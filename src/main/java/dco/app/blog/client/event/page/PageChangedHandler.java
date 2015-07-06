package dco.app.blog.client.event.page;

import com.google.gwt.event.shared.EventHandler;

/**
 * Page changed handler.
 *
 * @author Denis
 */
public interface PageChangedHandler extends EventHandler {

    /**
     * Called after the current page has already changed. Allows handlers to update any internal tracking, etc.
     *
     * @param event
     *         The event.
     */
    void onPageChange(PageChangedEvent event);

}