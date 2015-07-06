package dco.app.blog.client.event.page;

import com.google.gwt.event.shared.GwtEvent;
import dco.app.blog.client.navigation.PageRequest;

/**
 * Page changed event.
 *
 * @author Denis
 */
public class PageChangedEvent extends GwtEvent<PageChangedHandler> {

    private static Type<PageChangedHandler> TYPE;

    public static Type<PageChangedHandler> getType() {
        if (TYPE == null) {
            TYPE = new Type<PageChangedHandler>();
        }
        return TYPE;
    }

    private final PageRequest request;

    public PageChangedEvent(PageRequest request) {
        this.request = request;
    }

    public PageRequest getRequest() {
        return request;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void dispatch(PageChangedHandler handler) {
        handler.onPageChange(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type<PageChangedHandler> getAssociatedType() {
        return getType();
    }

}