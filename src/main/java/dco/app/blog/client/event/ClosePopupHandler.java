package dco.app.blog.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles the {@link ClosePopupEvent} events.
 *
 * @author Denis
 */
public interface ClosePopupHandler extends EventHandler {

    /**
     * Called when a popup is closed.
     *
     * @param event
     *         The event.
     */
    void onClosePopup(ClosePopupEvent event);

}