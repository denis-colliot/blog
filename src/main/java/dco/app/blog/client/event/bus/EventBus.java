package dco.app.blog.client.event.bus;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import dco.app.blog.client.navigation.Page;
import dco.app.blog.client.navigation.PageRequest;
import dco.app.blog.client.navigation.Zone;
import dco.app.blog.client.navigation.ZoneRequest;
import dco.app.blog.client.ui.widget.Loadable;

/**
 * <p>
 * Application event bus interface.
 * </p>
 * <p>
 * Handles pages navigation, zones update, messages and notifications display.
 * </p>
 *
 * @author Denis
 */
public interface EventBus extends HasHandlers {

    /**
     * Callback used to execute process on presenter leaving.
     *
     * @author Denis
     */
    static interface LeavingCallback {

        /**
         * Executed if the current presenter should be left.
         */
        void leavingOk();

        /**
         * Executed if the current presenter should <b>not</b> be left.
         */
        void leavingKo();

    }

    /**
     * Registers the given {@code handler} for the {@code type}.
     *
     * @param <H>
     *         The event handler type.
     * @param type
     *         The type.
     * @param handler
     *         The handler.
     * @return The {@code HandlerRegistration}.
     */
    <H extends EventHandler> HandlerRegistration addHandler(Type<H> type, H handler);

    /**
     * Gets the handler at the given {@code code}.
     *
     * @param <H>
     *         The event handler type.
     * @param index
     *         The index.
     * @param type
     *         The handler's event type.
     * @return The given handler.
     */
    <H extends EventHandler> H getHandler(Type<H> type, int index);

    /**
     * Gets the number of handlers listening to the event {@code type}.
     *
     * @param type
     *         The event type.
     * @return The number of registered handlers.
     */
    int getHandlerCount(Type<?> type);

    /**
     * Does this handler manager handle the given event type?
     *
     * @param e
     *         The event type.
     * @return Whether the given event type is handled.
     */
    boolean isEventHandled(Type<?> e);

    /**
     * Logs out the current authenticated user (if any).
     */
    void logout();

    /**
     * Navigates to the given {@code page}.<br>
     * To access the page with <em>URL</em> and/or <em>Object</em> parameters, use
     * {@link #navigateRequest(PageRequest, Loadable...)}.
     *
     * @param page
     *         The {@link Page} to access (without URL parameters).
     * @param loadables
     *         (optional) The {@link Loadable} elements to set in {@code loading} mode during page access rights
     *         retrieval action.
     */
    void navigate(Page page, Loadable... loadables);

    /**
     * Navigates to the given page {@code request}.
     *
     * @param request
     *         The {@link Page} request to access.
     * @param loadables
     *         (optional) The {@link Loadable} elements to set in {@code loading} mode during page access rights
     *         retrieval action.
     */
    void navigateRequest(PageRequest request, Loadable... loadables);

    /**
     * Updates the given {@code zone}.<br>
     * To update a zone with <em>Object</em> parameters, use {@link #updateZoneRequest(ZoneRequest)}.
     *
     * @param zone
     *         The {@link Zone} to update.
     */
    void updateZone(Zone zone);

    /**
     * Updates the given {@code zoneRequest} corresponding zone.
     *
     * @param zoneRequest
     *         The {@link ZoneRequest} containing the zone to update and some optional data parameters.
     */
    void updateZoneRequest(ZoneRequest zoneRequest);

}