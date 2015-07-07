package dco.app.blog.client.ui.notification;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import dco.app.blog.client.i18n.I18N;
import dco.app.blog.client.ui.widget.button.Button;
import dco.app.blog.client.ui.widget.popup.IsPopupWidget;
import dco.app.blog.client.ui.widget.popup.PopupWidget;
import dco.app.blog.client.util.MessageType;
import dco.app.blog.shared.util.ClientUtils;

/**
 * Displays confirmation message (with yes/no buttons) in a modal popup.
 *
 * @author Denis
 */
final class Confirmations {

    private Confirmations() {
        // Provides only static methods.
    }

    // CSS.
    private static final String CSS_POPUP = "notification";

    private static final String CSS_POPUP2 = "confirmation";

    private static final String CSS_FORM = "form-panel";

    // Initialize the popup widget.
    private static final IsPopupWidget popup;

    private static boolean visible;

    private static ConfirmCallback yesCallback;

    private static ConfirmCallback noCallback;

    static {

        // Buttons.
        final Button yes = new Button(I18N.CONSTANTS.yes(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (yesCallback != null) {
                    yesCallback.onAction();
                }
                popup.hide();
                visible = false;
            }
        });
        final Button no = new Button(I18N.CONSTANTS.no(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (noCallback != null) {
                    noCallback.onAction();
                }
                popup.hide();
                visible = false;
            }
        });

        // TODO Form panel.
//        final FormPanel form = Forms.panel(CSS_FORM);
//        form.setPadding(0);
//        form.addButton(yes);
//        form.addButton(no);

        // Popup.
        popup = new PopupWidget();
        popup.setContent(new HTML("<b>TODO: Confirmation popup content.</b>"));
        popup.addStyleName(CSS_POPUP);
        popup.addStyleName(CSS_POPUP2);

        visible = false;

    }

    /**
     * Clears the current message.
     */
    private static void clear() {
        popup.setPageMessage(null, null);
    }

    /**
     * Shows the given message into the popup.<br/>
     * <br/>
     * There is only one instance of the popup, the previous message may be erased.
     *
     * @param title
     *         The title.
     * @param html
     *         The message.
     * @param yesCallback
     *         The callback for the yes action.
     * @param noCallback
     *         The callback for the no action.
     */
    static void show(final String title, final String html, ConfirmCallback yesCallback, ConfirmCallback noCallback) {

        clear();

        Confirmations.yesCallback = yesCallback;
        Confirmations.noCallback = noCallback;

        popup.setTitle(ClientUtils.isNotBlank(title) ? title : MessageType.getTitle(MessageType.QUESTION));
        popup.setPageMessage(html, MessageType.QUESTION);

        if (!visible) {
            popup.center();
            visible = true;
        }
    }

}