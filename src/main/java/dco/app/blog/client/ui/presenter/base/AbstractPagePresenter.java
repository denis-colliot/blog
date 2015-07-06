package dco.app.blog.client.ui.presenter.base;

import com.allen_sauer.gwt.log.client.Log;
import com.google.inject.Inject;
import com.google.inject.Injector;
import dco.app.blog.client.event.bus.EventBus;
import dco.app.blog.client.event.page.PageChangedEvent;
import dco.app.blog.client.event.page.PageRequestEvent;
import dco.app.blog.client.event.page.PageRequestHandler;
import dco.app.blog.client.i18n.I18N;
import dco.app.blog.client.navigation.Page;
import dco.app.blog.client.navigation.PageManager;
import dco.app.blog.client.navigation.PageRequest;
import dco.app.blog.client.ui.notification.ConfirmCallback;
import dco.app.blog.client.ui.notification.N10N;
import dco.app.blog.client.ui.view.base.AbstractView;
import dco.app.blog.client.ui.view.base.ViewInterface;
import dco.app.blog.client.ui.view.base.ViewPopupInterface;
import dco.app.blog.client.util.MessageType;
import dco.app.blog.shared.util.ClientUtils;

/**
 * <p>
 * Abstract <b>page</b> presenter associated to a page token.
 * </p>
 * <p>
 * <b>Rules to add a new <u>page</u> presenter:</b>
 * <ol>
 * <li>Define a new page token in {@link Page} enum.
 * <li>Create a new class inheriting {@link AbstractPagePresenter} with {@link com.google.inject.Singleton} annotation
 * (<u>crucial</u>).<br>
 * The presenter's {@link PagePresenter#getPage()} method should return new page token.</li>
 * <li>Define an inner <em>static</em> interface representing the presenter's view. This interface must have the
 * {@link com.google.inject.ImplementedBy} annotation referencing the view implementation (<u>crucial</u>).<br/>
 * See {@link AbstractView} javadoc to initialize the view implementation.</li>
 * <li>Add an accessor to the presenter into client-side {@link Injector} and call it into {@link dco.app.blog.client.Blog#onModuleLoad()}
 * entry point in order to register presenter.</li>
 * </ol>
 * </p>
 * <p>
 * <b>Utility methods provided to presenter implementations:</b>
 * <ul>
 * <li>{@link #auth()} to access the current authentication.</li>
 * <li>{@link #isAnonymous()} to check if no user is currently authenticated.</li>
 * </ul>
 * </p>
 *
 * @param <V>
 *         View interface extending the {@link ViewInterface} interface
 * @author Denis
 */
public abstract class AbstractPagePresenter<V extends ViewInterface> extends AbstractPresenter<V> implements Presenter.PagePresenter<V> {

    /**
     * Executes {@link #bind()} method and registers page with {@link PageManager}.
     *
     * @param view
     *         Page presenter's view interface.
     * @param injector
     *         Injected application client-side injector.
     */
    @Inject
    protected AbstractPagePresenter(final V view, final Injector injector) {

        super(view, injector); // Executes 'bind()' method.

        // Registers page object.
        injector.getPageManager().registerPage(this, isPopupView());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void bind() {

        final Page page = getPage();

        if (page == null) {
            return;
        }

        registerHandler(eventBus.addHandler(PageRequestEvent.getType(), new PageRequestHandler() {

            @Override
            public void onPageRequest(final PageRequestEvent event) {

                if (!event.concern(page)) {
                    return;
                }

                loadPagePresenter(event, page);
            }
        }));
    }

    /**
     * <p>
     * Loads the current presenter by executing the following steps :
     * <ul>
     * <li>Initializes the view components by calling {@link #initialize()} method (only for the first access).</li>
     * <li>Sends an {@link PageChangedEvent} to indicate that the page has changed.</li>
     * <li>Executes the current presenter's method {@link #onPageRequest}.</li>
     * <li>Executes the current presenter's method {@link #revealView}.</li>
     * </ul>
     * </p>
     *
     * @param event
     *         Page request event.
     * @param page
     *         New page loaded.
     */
    private final void loadPagePresenter(final PageRequestEvent event, final Page page) {

        // Initializes presenter's view components.
        initialize();

        // Necessary when we've fired PRE (PageRequestEvent) from code vs Hyperlink.
        eventBus.fireEvent(new PageChangedEvent(event.getRequest()));

        // Resets page message.
        displayPageMessage(null, null);

        executeOnPageRequest(event, page);
    }

    /**
     * <p>
     * Can be overridden by child implementation.
     * </p>
     * <p>
     * <b>Important:</b> don't forget to execute {@code super.afterOnPageRequest(PageRequestEvent, Page)} if the method is
     * overridden.
     * </p>
     *
     * @param event
     *         Page request event.
     * @param page
     *         New page loaded.
     */
    protected void executeOnPageRequest(final PageRequestEvent event, final Page page) {
        afterOnPageRequest(event, page);
    }

    /**
     * Must be called after {@link #executeOnPageRequest(PageRequestEvent, Page)}.
     *
     * @param event
     *         Page request event.
     * @param page
     *         New page loaded.
     */
    protected final void afterOnPageRequest(final PageRequestEvent event, final Page page) {

        if (Log.isTraceEnabled()) {
            Log.trace("Executing '" + page + "' onPageRequest() method.");
        }

        final PageRequest pageRequest = event.getRequest();

        if (isSubPresenter()) {
            ((HasSubPresenter.SubPresenter<?>) this).getParentPresenter().onSubPresenterRequest(pageRequest);
        }

        // Processing presenter 'onPageRequest()' implementation.
        onPageRequest(pageRequest);

        revealView();

        if (!isPopupView()) {
            // Update application message and control.
            displayApplicationMessage(page);
        }

    }

    /**
     * Gets the current presenter page title.
     *
     * @return the current presenter page title.
     */
    protected final String getPageTitle() {
        return Page.getTitle(getPage());
    }

    /**
     * Sets the current presenter page title.<br/>
     * If the presenter's view is a {@link ViewPopupInterface} implementation, the popup title is dynamically updated.
     *
     * @param title
     *         The new page title.
     */
    protected final void setPageTitle(String title) {
        if (getPage() == null) {
            return;
        }
        getPage().setTitle(title);

        if (isPopupView()) {
            // Popup presenter's view case.
            ((ViewPopupInterface) view).setPopupTitle(title);

        } else {
            // Classic page.
            injector.getApplicationPresenter().setPageTitle(title);
        }
    }

    /**
     * Checks if a message needs to be displayed in the application header. If it does, the message is sent to the
     * application presenter's view.<br/>
     * <br/>
     * Also check if the {@code page} is still in progress from retrieved server properties. If this is the case, a
     * warning message is displayed.
     *
     * @param page
     *         The accessed page.
     */
    private void displayApplicationMessage(final Page page) {
        // TODO retrieve application message from server-side (DB most-likely).
        // eventBus.updateZone(Zone.MESSAGE_BANNER, "Welcome in Sigmah !", MessageType.WARNING);
    }

    /**
     * Displays the given message at the top of the current page. If the message is {@code null} or {@code empty}, the
     * message will be hidden.<br/>
     * <br/>
     * <strong>The page message must be initialized in the {@link #onPageRequest} method.</strong>
     *
     * @param message
     *         The message as HTML.
     */
    protected final void displayPageMessage(final String message, MessageType type) {

        if (isPopupView()) {
            ((ViewPopupInterface) view).setPageMessage(message, type);
        } else {
            injector.getApplicationPresenter().setPageMessage(message, type);
        }
    }

    /**
     * Hides the presenter's popup view.<br/>
     * If the presenter's view is not a {@link ViewPopupInterface} implementation, the method does nothing.
     */
    protected final void hideView() {
        if (isPopupView()) {
            ((ViewPopupInterface) view).hide();
        }
    }

    /**
     * Returns if the current presenter's {@code view} is an instance of a popup view ({@link ViewPopupInterface}
     * implementation).
     *
     * @return {@code true} if the current presenter's {@code view} is an instance of a popup view, {@code false}
     * otherwise.
     */
    protected final boolean isPopupView() {
        return view instanceof ViewPopupInterface;
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * <p>
     * If a value has changed, user is invited to confirm its leaving process.
     * </p>
     * <p>
     * Value change detection relies on following mechanisms:
     * <ul>
     * <li>{@link #hasValueChanged()} method result.</li>
     * <li>And/Or {@link HasForm} presenters: provided form(s) are scanned to detect value change.</li>
     * </ul>
     * </p>
     */
    @Override
    public void beforeLeaving(final EventBus.LeavingCallback callback) {

        if (!(this instanceof HasForm) && !hasValueChanged()) {
            super.beforeLeaving(callback);
            return;
        }

        boolean valueHasChanged = hasValueChanged();

        if (!valueHasChanged && this instanceof HasForm) {
            final FormPanel[] forms = ((HasForm) this).getForms();

            if (ClientUtils.isEmpty(forms)) {
                super.beforeLeaving(callback);
                return;
            }

            for (final FormPanel form : forms) {
                if (form != null && (valueHasChanged |= form.isValueHasChanged())) {
                    break;
                }
            }
        }

        if (valueHasChanged) {
            N10N.confirmation(I18N.CONSTANTS.unsavedDataMessage(), new ConfirmCallback() {

                @Override
                public void onAction() {
                    // YES action.
                    onLeavingOk();
                    callback.leavingOk();
                }
            }, new ConfirmCallback() {

                @Override
                public void onAction() {
                    // NO action.
                    onLeavingKo();
                    callback.leavingKo();
                }
            });

        } else {
            onLeavingOk();
            callback.leavingOk();
        }
    }

    /**
     * Callback executed when the user agrees to leave the current page presenter.<br>
     * <em>Default implementation does nothing.</em>
     */
    protected void onLeavingOk() {
        // Default implementation does nothing.
    }

    /**
     * Callback executed when the user refuses to leave the current page presenter.<br>
     * <em>Default implementation does nothing.</em>
     */
    protected void onLeavingKo() {
        // Default implementation does nothing.
    }

    /**
     * Returns if a value of the current page has changed.<br>
     * <em>Default implementation returns {@code false}.</em>
     *
     * @return {@code true} if a value of the current page has changed, {@code false} otherwise.
     */
    protected boolean hasValueChanged() {
        return false;
    }

}