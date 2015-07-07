package dco.app.blog.client.ui.widget.popup;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import dco.app.blog.client.event.ClosePopupHandler;
import dco.app.blog.client.util.MessageType;

/**
 * Abstract popup widget.
 *
 * @author Denis
 */
public abstract class AbstractPopupWidget<P extends IsPopupWidget> implements IsPopupWidget {

    /**
     * z-index start value.
     */
    private static final int Z_INDEX_START_VALUE = 9999;

    /**
     * The application z-index value.
     */
    private static int zIndex = Z_INDEX_START_VALUE;

    protected final P popup;

    protected final boolean disableScrolling;

    private Widget widget;

    private Integer currentPopupZIndex;

    /**
     * Initializes the popup widget.<br/>
     * Window scrolling is automatically disabled while popup widget is displayed (to deactivate this behaviour, see
     * {@link #AbstractPopupWidget(IsPopupWidget, boolean)}).
     *
     * @param popupPanel
     *         The internal popup panel used to display the popup widget.
     */
    protected AbstractPopupWidget(P popupPanel) {
        this(popupPanel, true);
    }

    /**
     * Initializes the popup widget.
     *
     * @param popupPanel
     *         The internal popup panel used to display the popup widget.
     * @param disableScrolling
     *         {@code true} to disable window scrolling while popup widget is displayed, {@code false} to leave it.
     */
    protected AbstractPopupWidget(P popupPanel, boolean disableScrolling) {
        this.popup = popupPanel;
        this.disableScrolling = disableScrolling;
    }

    /**
     * Initializes the popup widget with the given {@code widget}.
     *
     * @param widget
     *         The popup widget.
     */
    protected final void initPopupWidget(Widget widget) {
        this.widget = widget;
    }

    @Override
    public void center() {
        if (popup != null) {
            if (disableScrolling) {
                setScrollingEnabled(false);
            }
            popup.center();
            setPopupZIndex();
        }
    }

    @Override
    public void show() {
        if (popup != null) {
            if (disableScrolling) {
                setScrollingEnabled(false);
            }
            popup.show();
            setPopupZIndex();
        }
    }

    @Override
    public void hide() {
        if (popup != null) {
            popup.hide();
            zIndex--;
            if (zIndex < Z_INDEX_START_VALUE) {
                // Security.
                zIndex = Z_INDEX_START_VALUE;
            }
            currentPopupZIndex = null;
            if (disableScrolling) {
                // Reactivates scrolling only if hiding last popup.
                setScrollingEnabled(zIndex == Z_INDEX_START_VALUE);
            }
        }
    }

    /**
     * Enables or disables window scrolling.
     *
     * @param scrolling
     *         {@code true} to enable window scrolling, {@code false} to disable it.
     */
    public static void setScrollingEnabled(boolean scrolling) {
        // Window.enableScrolling(scrolling);
        final Overflow o = scrolling ? Overflow.AUTO : Overflow.HIDDEN;
        RootPanel.get().getElement().getStyle().setOverflow(o);
    }

    /**
     * Sets the displayed {@code popup} z-index value.<br/>
     * Sets the {@code currentPopupZIndex} attribute value.
     */
    private void setPopupZIndex() {
        if (popup instanceof AbstractPopupWidget || currentPopupZIndex != null) {
            return;
        }
        currentPopupZIndex = zIndex++;
        popup.setZIndex(currentPopupZIndex);
    }

    /**
     * Gets the current z-index value.
     *
     * @return the current z-index value.
     */
    public static int getCurrentZIndex() {
        return zIndex;
    }

    /**
     * Returns if a popup is currently displayed on user page.
     *
     * @return {@code true} if a popup is currently displayed on user page.
     */
    public static boolean isPopupDisplayed() {
        return zIndex != Z_INDEX_START_VALUE;
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void setStyleName(String styleName) {
        popup.setStyleName(styleName);
    }

    @Override
    public void addStyleName(String styleName) {
        popup.addStyleName(styleName);
    }

    @Override
    public void removeStyleName(String styleName) {
        popup.removeStyleName(styleName);
    }

    @Override
    public void setTitle(String title) {
        popup.setTitle(title);
    }

    @Override
    public void setContent(Widget widget) {
        popup.setContent(widget);
    }

    @Override
    public void setWidth(String width) {
        popup.setWidth(width);
    }

    @Override
    public void setHeight(String height) {
        popup.setHeight(height);
    }

    @Override
    public void setZIndex(int zIndex) {
        popup.setZIndex(zIndex);
    }

    @Override
    public void setClosePopupHandler(ClosePopupHandler handler) {
        popup.setClosePopupHandler(handler);
    }

    @Override
    public void setPageMessageVisible(boolean visible) {
        popup.setPageMessageVisible(visible);
    }

    @Override
    public void setPageMessage(String html, MessageType type) {
        popup.setPageMessage(html, type);
    }

    @Override
    public void setLoading(boolean loading) {
        popup.setLoading(loading);
    }

    @Override
    public boolean isLoading() {
        return popup.isLoading();
    }
}
