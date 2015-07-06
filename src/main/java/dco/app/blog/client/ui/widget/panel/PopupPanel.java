package dco.app.blog.client.ui.widget.panel;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import dco.app.blog.client.event.ClosePopupHandler;
import dco.app.blog.client.ui.widget.popup.IsPopupWidget;
import dco.app.blog.client.util.MessageType;

/**
 * Custom {@code PopupPanel} implementation implementing {@code IsPopupWidget} interface.
 *
 * @author Denis
 * @see com.google.gwt.user.client.ui.PopupPanel
 */
public class PopupPanel extends com.google.gwt.user.client.ui.PopupPanel implements IsPopupWidget {

    /**
     * Creates an empty popup panel. A child widget must be added to it before it is shown.
     */
    public PopupPanel() {
        super();
    }

    /**
     * Creates an empty popup panel, specifying its "auto-hide" property.
     *
     * @param autoHide
     *         <code>true</code> if the popup should be automatically hidden when the user clicks outside of it or
     *         the history token changes.
     */
    public PopupPanel(boolean autoHide) {
        super(autoHide);
    }

    /**
     * Creates an empty popup panel, specifying its "auto-hide" and "modal" properties.
     *
     * @param autoHide
     *         <code>true</code> if the popup should be automatically hidden when the user clicks outside of it or
     *         the history token changes.
     * @param modal
     *         <code>true</code> if keyboard or mouse events that do not target the PopupPanel or its children should
     *         be ignored
     */
    public PopupPanel(boolean autoHide, boolean modal) {
        super(autoHide, modal);
    }

    // --
    //
    // IsPopupWidget implementation.
    //
    // --

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContent(Widget widget) {
        setWidget(widget);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setZIndex(int zIndex) {
        DOM.setIntStyleAttribute(getElement(), "zIndex", zIndex);
        if (getGlassElement() != null) {
            getGlassElement().getStyle().setProperty("zIndex", String.valueOf(zIndex - 1));
        }
    }

    @Override
    public void setClosePopupHandler(ClosePopupHandler handler) {
        // Does nothing.
    }

    @Override
    public void setPageMessageVisible(boolean visible) {
        // Does nothing.
    }

    @Override
    public void setPageMessage(String html, MessageType type) {
        // Does nothing.
    }

    @Override
    public void setLoading(boolean loading) {
        // Does nothing.
    }

    @Override
    public boolean isLoading() {
        return false;
    }
}
