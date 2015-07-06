package dco.app.blog.client.event.zone;

import com.google.gwt.event.shared.EventHandler;

/**
 * Zone request handler.
 *
 * @author Denis
 */
public interface ZoneRequestHandler extends EventHandler {

    /**
     * Called when something has requested a zone update. Should be implemented by instances which can update the zone.
     *
     * @param event
     *         The event.
     */
    void onZoneRequest(ZoneRequestEvent event);

}