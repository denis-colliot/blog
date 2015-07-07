package dco.app.blog.client.ui.widget.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import dco.app.blog.client.ui.widget.panel.PopupPanel;

/**
 * Notification widget. Used to display a simple notification pop-up on the screen.
 *
 * @author Denis
 */
public class NotificationWidget extends AbstractPopupWidget<PopupPanel> {

    /**
     * {@link UiBinder} interface adapted to {@link NotificationWidget}.
     */
    @UiTemplate("NotificationWidget.ui.xml")
    interface NotificationsViewUiBinder extends UiBinder<Widget, NotificationWidget> {
    }

    /**
     * Notification widget style.
     *
     * @author Denis
     */
    public static interface NotificationStyle extends CssResource {

        int marginRight();

        int marginBottom();

        int popupWidth();

        int popupHeight();

        int popupPadding();

    }

    @UiField
    NotificationStyle style;

    @UiField(provided = true)
    PopupPanel popup;

    @UiField
    FlowPanel panel;

    @UiField
    Label headerLabel;

    @UiField
    HTML contentLabel;

    @UiConstructor
    public NotificationWidget() {
        this(null, null);
    }

    public NotificationWidget(String header, String content) {
        super(new PopupPanel(), false);
        this.popup = super.popup; // Internal popup instance stored into parent class.

        final NotificationsViewUiBinder uiBinder = GWT.create(NotificationsViewUiBinder.class);
        initPopupWidget(uiBinder.createAndBindUi(this));

        // Important: dimensions must be set in java code !
        panel.getElement().getStyle().setPadding(style.popupPadding(), Unit.PX);
        popup.setWidth(style.popupWidth() + "px");
        popup.setHeight(style.popupHeight() + "px");

        if (header != null) {
            setHeaderLabel(header);
        }

        if (content != null) {
            setContentLabel(content);
        }
    }

    /**
     * Gets the internal popup panel.
     *
     * @return the internal popup panel.
     */
    public PopupPanel getPopup() {
        return popup;
    }

    /**
     * Gets the notification {@link NotificationStyle} instance.
     *
     * @return the notification {@link NotificationStyle} instance.
     */
    public NotificationStyle style() {
        return style;
    }

    /**
     * Sets the notification header label.
     *
     * @param header
     *         The notification header label.
     */
    public void setHeaderLabel(String header) {
        this.headerLabel.setText(header);
    }

    /**
     * Sets the notification content label.
     *
     * @param content
     *         The notification content label.
     */
    public void setContentLabel(String content) {
        this.contentLabel.setHTML(content);
    }

    @Override
    public void setContent(Widget widget) {
        throw new UnsupportedOperationException("Only 'text' content is allowed for notification widget");
    }

}
