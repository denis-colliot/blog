package dco.app.blog.client.event.zone;

import com.google.gwt.event.shared.GwtEvent;
import dco.app.blog.client.navigation.Zone;
import dco.app.blog.client.navigation.ZoneRequest;

/**
 * Zone request event.
 *
 * @author Denis
 */
public class ZoneRequestEvent extends GwtEvent<ZoneRequestHandler> {

    private static Type<ZoneRequestHandler> TYPE;

    private ZoneRequest zoneRequest;

    public static Type<ZoneRequestHandler> getType() {
        if (TYPE == null) {
            TYPE = new Type<ZoneRequestHandler>();
        }
        return TYPE;
    }

    public ZoneRequestEvent(ZoneRequest zoneRequest) {
        this.zoneRequest = zoneRequest;
    }

    public ZoneRequest getZoneRequest() {
        return zoneRequest;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type<ZoneRequestHandler> getAssociatedType() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void dispatch(ZoneRequestHandler handler) {
        handler.onZoneRequest(this);
    }

    /**
     * Returns if the current event concerns the given {@code zone}.
     *
     * @param zone
     *         The zone.
     * @return {@code true} if the current event concerns the given {@code zone}, {@code false} otherwise.
     */
    public boolean concern(final Zone zone) {
        final Zone currentZone = getZoneRequest() != null ? getZoneRequest().getZone() : null;
        return currentZone != null && currentZone == zone;
    }

}