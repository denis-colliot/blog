package dco.app.blog.client.navigation;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import dco.app.blog.client.event.bus.EventBus;
import dco.app.blog.client.event.page.*;
import dco.app.blog.client.ui.presenter.base.Presenter;
import dco.app.blog.client.ui.view.base.AbstractPopupView;
import dco.app.blog.shared.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Page manager handling History and pages loading.
 *
 * @author Denis
 */
public class PageManager implements ValueChangeHandler<String>, PageChangedHandler, PageRequestHandler {

    /**
     * Application event bus.
     */
    private final EventBus eventBus;

    /**
     * Record of all the application presenter's pages.
     */
    private final Map<String, Pair<Page, Boolean>> pages;

    /**
     * Record of all the application {@link Page}s with their corresponding {@link Presenter.PagePresenter} instance.
     */
    private final Map<Page, Presenter.PagePresenter<?>> presenters;

    /**
     * The current page request.
     */
    private PageRequest currentPageRequest;

    /**
     * The current popup page request.
     */
    private PageRequest currentPopupPageRequest;

    /**
     * Page manager constructor.
     *
     * @param eventBus
     *         application event bus.
     */
    @Inject
    public PageManager(final EventBus eventBus) {
        this.eventBus = eventBus;
        pages = new HashMap<String, Pair<Page, Boolean>>();
        presenters = new HashMap<Page, Presenter.PagePresenter<?>>();

        // Register ourselves with the History API.
        History.addValueChangeHandler(this);

        // Listen for manual place change events.
        eventBus.addHandler(PageChangedEvent.getType(), this);
    }

    /**
     * Registers a {@link Presenter.PagePresenter} instance.
     *
     * @param pagePresenter
     *         The {@link Presenter.PagePresenter} instance associated to a {@link Page} token.
     * @param popupView
     *         {@code true} if the given {@code page} view is displayed as a popup view.
     */
    public void registerPage(final Presenter.PagePresenter<?> pagePresenter, final boolean popupView) {

        if (pagePresenter == null) {
            throw new IllegalArgumentException("Invalid page presenter instance.");
        }

        final Page page = pagePresenter.getPage();

        if (page != null) {
            presenters.put(page, pagePresenter);
            pages.put(page.getToken(), new Pair<Page, Boolean>(page, popupView));
        }
    }

    /**
     * Returns the {@link Page} instance associated to the given page id.
     *
     * @param pageId
     *         page id value
     * @return the {@link Page} instance associated to the given page id, or {@code null} if no page instance exists for
     * the given id.
     */
    public Page getPage(final String pageId) {
        return pages.get(pageId) != null ? pages.get(pageId).left : null;
    }

    /**
     * Returns if the given {@link Page} instance is associated to a popup view.<br/>
     * If the page is {@code null} or is not registered into page manager, the method returns {@code false}.
     *
     * @param page
     *         The page instance.
     * @return {@code true} if the given {@link Page} instance is associated to a popup view, {@code false} otherwise.
     */
    public boolean isPopupView(final Page page) {
        if (page == null || pages.get(page.getToken()) == null) {
            return false;
        }
        return pages.get(page.getToken()).right;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onValueChange(final ValueChangeEvent<String> event) {
        try {

            final PageRequest pageRequest = PageRequest.fromString(event.getValue(), pages);

            // A popup page cannot be accessed directly by URL modification (such as <a> elements).
            if (isPopupView(pageRequest.getPage())) {
                if (Log.isInfoEnabled()) {
                    Log.info("Popup page '" + pageRequest + "' cannot be accessed directly by URL.");
                }
                eventBus.navigate(null);
            } else {
                eventBus.fireEvent(new PageRequestEvent(pageRequest, true));
            }

        } catch (final PageParsingException e) {
            eventBus.navigate(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPageChange(final PageChangedEvent event) {

        final PageRequest pageRequest = event.getRequest();
        final Page page = pageRequest.getPage();

        // Tracks current page.
        trackPage(page);

        if (page != null && isPopupView(page)) {
            currentPopupPageRequest = pageRequest;
        } else {
            currentPageRequest = pageRequest;
        }

        newPlace(pageRequest);

        updateZones(pageRequest);
    }

    /**
     * Updates zones.<br/>
     * Does nothing if given {@code pageRequest} references a pop-up view.
     *
     * @param pageRequest
     *         The page request (containing access rights).
     */
    private void updateZones(final PageRequest pageRequest) {

        final Page page = pageRequest.getPage();

        if (page != null && isPopupView(page)) {
            return;
        }

        eventBus.updateZone(Zone.AUTH_BANNER);
        eventBus.updateZoneRequest(Zone.MENU_BANNER.requestWith(RequestParameter.REQUEST, pageRequest));
    }

    /**
     * Adds a new browser history entry only if the requested page exists among the {@code pages} map attribute.<br/>
     * Does nothing if given {@code pageRequest} references a pop-up view or a <em>skip history</em> page.
     *
     * @param request
     *         Page request.
     */
    private void newPlace(final PageRequest request) {

        final Page page = request.getPage();

        if (page != null && (page.skipHistory() || isPopupView(page))) {
            // "Pop-up views" and "Skip history pages" don't generate a new History item.
            return;
        }

        History.newItem(request.toString(), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPageRequest(final PageRequestEvent event) {
        if (!event.isFromHistory()) {
            currentPageRequest = event.getRequest();
            newPlace(event.getRequest());
        }
    }

    public void fireCurrentPlace() {
        if (History.getToken() != null) {
            History.fireCurrentHistoryState();
        }
    }

    /**
     * Returns the current page (even if the current page is a popup).
     *
     * @return the current page or {@code null}.
     */
    public Page getCurrentPage() {
        return getCurrentPage(true);
    }

    /**
     * Returns the current page.
     *
     * @param includeCurrentPopup
     *         Set to <code>true</code> to get the current popup's page if any popup is currently displayed, set to
     *         <code>false</code> to get the current non-popup page even if a popup is currently displayed.
     * @return the current page or {@code null}.
     */
    public Page getCurrentPage(boolean includeCurrentPopup) {

        final PageRequest pageRequest = getCurrentPageRequest(includeCurrentPopup);
        return pageRequest != null ? pageRequest.getPage() : null;
    }

    /**
     * Returns the current page token (even if the current page is a popup).
     *
     * @return the current page <b>token</b> or {@code null}.
     */
    public String getCurrentPageToken() {
        return getCurrentPageToken(true);
    }

    /**
     * Returns the current page <b>token</b>.
     *
     * @param includeCurrentPopup
     *         Set to <code>true</code> to get the current popup's page token if any popup is currently displayed, set to
     *         <code>false</code> to get the current non-popup page token even if a popup is currently displayed.
     * @return the current page token or {@code null}.
     */
    public String getCurrentPageToken(boolean includeCurrentPopup) {
        final Page currentPage = getCurrentPage(includeCurrentPopup);
        return currentPage != null ? currentPage.getToken() : null;
    }

    /**
     * Returns the current page request (even if the current page is a popup).
     *
     * @return the current page request or {@code null}.
     */
    public PageRequest getCurrentPageRequest() {
        return getCurrentPageRequest(true);
    }

    /**
     * Returns the current page request.
     *
     * @param includeCurrentPopup
     *         Set to <code>true</code> to get the current popup's page request if any popup is currently displayed, set
     *         to <code>false</code> to get the current non-popup page request even if a popup is currently displayed.
     * @return the current page request or {@code null}.
     */
    public PageRequest getCurrentPageRequest(boolean includeCurrentPopup) {

        if (includeCurrentPopup && AbstractPopupView.isPopupDisplayed()) {
            return new PageRequest(currentPopupPageRequest);
        } else {
            return new PageRequest(currentPageRequest);
        }
    }

    /**
     * Returns if the current page is a popup view.
     *
     * @return {@code true} if the current page is a popup view.
     */
    public boolean isCurrentPagePopup() {
        return isPopupView(getCurrentPage());
    }

    /**
     * Returns the current {@link Presenter.PagePresenter} (even if the current page is a popup).
     *
     * @return the current {@link Presenter.PagePresenter} instance or {@code null}.
     */
    public Presenter.PagePresenter<?> getCurrentPresenter() {
        return getCurrentPresenter(true);
    }

    /**
     * Returns the current {@link Presenter.PagePresenter} instance.
     *
     * @param includeCurrentPopup
     *         Set to <code>true</code> to get the current popup's {@link Presenter.PagePresenter} if any popup is currently
     *         displayed, set to <code>false</code> to get the current non-popup {@link Presenter.PagePresenter} even if a popup is
     *         currently displayed.
     * @return the current {@link Presenter.PagePresenter} instance or {@code null}.
     */
    public Presenter.PagePresenter<?> getCurrentPresenter(boolean includeCurrentPopup) {

        final Page currentPage = getCurrentPage(includeCurrentPopup);

        return currentPage != null ? presenters.get(currentPage) : null;
    }

    /**
     * Returns the given {@code url} corresponding {@code PageRequest}.
     *
     * @param url
     *         The URL string value to parse.
     * @return the given {@code url} corresponding {@code PageRequest}.
     * @throws IllegalArgumentException
     *         If the given {@code url} is invalid.
     */
    public PageRequest getPageRequest(final String url) {
        try {

            return PageRequest.fromString(url, pages);

        } catch (final Exception e) {
            throw new IllegalArgumentException("URL '" + url + "' is invalid.", e);
        }
    }

    /**
     * Tracks given page in Google Analytics.
     *
     * @param page
     *         The tracked page.
     */
    public static void trackPage(final Page page) {
        if (page != null) {
            // TODO : Enable Google Analytics ?
            // trackPage(page.getToken());
        }
    }

    /**
     * Tracks given page in Google Analytics.
     *
     * @param pageName
     *         The tracked page name.
     */
    private static native void trackPage(final String pageName) /*-{
                                                                                                                            try {
																															$wnd._gaq.push([ '_setAccount', 'UA-000000000-1' ]);
																															$wnd._gaq.push([ '_trackPageview', pageName ]);
																															$wnd._gaq.push([ '_trackPageLoadTime' ]);
																															} catch (err) {
																															// Custom exception handling.
																															}
																															}-*/;

}