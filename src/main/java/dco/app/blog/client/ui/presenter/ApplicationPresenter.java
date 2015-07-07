package dco.app.blog.client.ui.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dco.app.blog.client.config.Injector;
import dco.app.blog.client.navigation.Page;
import dco.app.blog.client.navigation.Zone;
import dco.app.blog.client.ui.presenter.base.AbstractPresenter;
import dco.app.blog.client.ui.presenter.base.Presenter;
import dco.app.blog.client.ui.view.ApplicationView;
import dco.app.blog.client.ui.view.base.HasPageMessage;
import dco.app.blog.client.ui.view.base.ViewInterface;
import dco.app.blog.client.ui.view.base.ViewPopupInterface;
import dco.app.blog.client.util.MessageType;
import dco.app.blog.shared.util.ClientUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Application presenter.<br/>
 * This is the main presenter in charge of displaying the page that the user is navigating to using
 * {@link #showPresenter(Presenter)} method.<br/>
 * <br/>
 * The application presenter is also in charge of displaying the application message (if one is defined).
 *
 * @author Denis
 */
@Singleton
public final class ApplicationPresenter extends AbstractPresenter<ApplicationPresenter.View> implements HasPageMessage {

    /**
     * Application view.
     */
    @ImplementedBy(ApplicationView.class)
    public static interface View extends ViewInterface, HasPageMessage {

        /**
         * Hides the lodaing panel.
         */
        void hideLoadingPanel();

        /**
         * Shows the given {@code presenterWidget} view into proper area.
         *
         * @param presenterWidget
         *         The presenter's view to show.
         * @param fullPage
         *         {@code true} if the view should be shown on <em>full page</em>, {@code false} if it should be shown
         *         within content area.
         */
        void showPresenter(IsWidget presenterWidget, boolean fullPage);

        /**
         * Initializes the given zones view areas.
         */
        void initZones(Map<Zone, ViewInterface> zoneViews);

    }

    /**
     * Presenter initialization.
     *
     * @param view
     *         Presenter view interface.
     * @param injector
     *         Injected client injector.
     */
    @Inject
    public ApplicationPresenter(final View view, final Injector injector) {

        super(view, injector); // Executes 'bind()' method.

        final Map<Zone, ViewInterface> zoneViews = new HashMap<>();

        // TODO Zone views initialization.
//        zoneViews.put(Zone.APP_LOADER, injector.getAppLoaderPresenter().getView());
//        zoneViews.put(Zone.AUTH_BANNER, injector.getAuthenticationBannerPresenter().getView());
//        zoneViews.put(Zone.MENU_BANNER, injector.getMenuBannerPresenter().getView());
//        zoneViews.put(Zone.MESSAGE_BANNER, injector.getMessageBannerPresenter().getView());

        view.initZones(zoneViews);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bind() {
        // Add global application bindings here.
    }

    /**
     * Displays the given {@code view} on the application main content area.
     *
     * @param presenter
     *         The current presenter to display into application main panel.
     */
    public void showPresenter(final Presenter<?> presenter) {

        // Retrieving accessed page.
        final Page page;
        if (presenter instanceof PagePresenter) {
            page = ((PagePresenter<?>) presenter).getPage();
        } else {
            page = null;
        }

        // Page title.
        final String pageTitle = ClientUtils.isBlank(Page.getTitle(page)) ? "" : Page.getTitle(page);

        if (presenter.getView() instanceof ViewPopupInterface) {

            // Popup presenter's view case.
            final ViewPopupInterface viewPopup = (ViewPopupInterface) presenter.getView();
            viewPopup.setPopupTitle(pageTitle);
            viewPopup.center();

        } else {

            // Basic presenter's view case.

            // Displaying page view.
            view.showPresenter(presenter.getView().asWidget(), presenter.getView().isFullPage());

            // Sets page title widget visibility and content.
            setPageTitle(pageTitle);
        }

        // Hides the loading panel.
        view.hideLoadingPanel();
    }

    /**
     * Updates page title. Can be used to update title after asynchronous data loading for example.<br/>
     * The title area is automatically hidden if the given {@code pageTitle} is invalid.
     *
     * @param pageTitle
     *         The new page title.
     */
    public void setPageTitle(String pageTitle) {
        // No page title area defined yet.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPageMessageVisible(boolean visible) {
        view.setPageMessageVisible(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPageMessage(String html, MessageType type) {
        view.setPageMessage(html, type);
    }

}