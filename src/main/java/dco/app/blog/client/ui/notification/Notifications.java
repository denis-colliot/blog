package dco.app.blog.client.ui.notification;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import dco.app.blog.client.ui.widget.panel.PopupPanel;
import dco.app.blog.client.ui.widget.popup.AbstractPopupWidget;
import dco.app.blog.client.ui.widget.popup.NotificationWidget;
import dco.app.blog.client.util.MessageType;
import dco.app.blog.shared.util.ClientUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays messages into a notification tray.
 *
 * @author Denis
 */
final class Notifications {

    /**
     * Delay (in ms) between notifications.
     */
    private static final int DEFAULT_DELAY = 1000;

    private static final List<NotificationWidget> POPUPS = new ArrayList<>();

    private Notifications() {
        // Provides only static methods.
    }

    /**
     * Shows the given message into a new notification widget. The notifications widget are graphically stacked.
     *
     * @param title
     *         The title.
     * @param html
     *         The message.
     * @param type
     *         The message's type.
     */
    public static void show(final String title, final String html, MessageType type) {

        final String nonNullTitle = ClientUtils.isNotBlank(title) ? title : MessageType.getTitle(type);

        final NotificationWidget notificationWidget = new NotificationWidget(nonNullTitle, html);

        // Shows it.
        notificationWidget.getPopup().setPopupPositionAndShow(new PopupPanel.PositionCallback() {

            @Override
            public void setPosition(int offsetWidth, int offsetHeight) {

                final int marginRight = notificationWidget.style().marginRight();
                final int marginBottom = notificationWidget.style().marginBottom();
                final int popupHeight = notificationWidget.style().popupHeight();
                final int popupPadding = notificationWidget.style().popupPadding();

                final int left = (Window.getClientWidth() + Window.getScrollLeft()) - offsetWidth - marginRight;
                final int top = (Window.getClientHeight() + Window.getScrollTop()) - offsetHeight - marginBottom;

                notificationWidget.getPopup().setZIndex(AbstractPopupWidget.getCurrentZIndex() + 1);
                notificationWidget.getPopup().setPopupPosition(left, top);

                for (final NotificationWidget nw : POPUPS) {
                    nw.getPopup().setPopupPosition(nw.getPopup().getPopupLeft(),
                            nw.getPopup().getPopupTop() - (popupHeight + marginBottom + popupPadding + popupPadding));
                }

                POPUPS.add(notificationWidget);

                // Starts the timer.
                new Timer() {

                    @Override
                    public void run() {
                        notificationWidget.getPopup().hide();
                        POPUPS.remove(notificationWidget);
                    }

                }.schedule(DEFAULT_DELAY);
            }
        });
    }

}