package dco.app.blog.client.event.page;

import com.google.gwt.event.shared.EventHandler;

/**
 * Page request handler.
 *
 * @author Denis
 */
public interface PageRequestHandler extends EventHandler {

    /**
     * Called when something has requested a new page. Should be implemented by instances which can show the page.
     *
     * @param event
     *         The event.
     */
    void onPageRequest(PageRequestEvent event);

}