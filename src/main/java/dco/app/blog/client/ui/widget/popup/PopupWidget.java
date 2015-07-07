package dco.app.blog.client.ui.widget.popup;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import dco.app.blog.client.ui.view.base.AbstractPopupView;
import dco.app.blog.client.ui.widget.panel.PopupPanel;

/**
 * Default popup widget implementation.
 *
 * @author Denis
 */
public class PopupWidget extends AbstractPopupWidget<PopupPanel> {

    /**
     * {@link UiBinder} interface adapted to {@link AbstractPopupView}.
     */
    @UiTemplate("PopupWidget.ui.xml")
    static interface PopupViewUiBinder extends UiBinder<Widget, PopupWidget> {
    }

    @UiField(provided = true)
    PopupPanel popup;

    public PopupWidget() {
        this(true, true);
    }

    public PopupWidget(final boolean modal, final boolean closeable) {
        super(new PopupPanel());
        this.popup = super.popup;
        this.popup.setModal(modal);

        final PopupViewUiBinder uiBinder = GWT.create(PopupViewUiBinder.class);
        initPopupWidget(uiBinder.createAndBindUi(this));
    }

    /**
     * Sets the popup widget content.
     *
     * @param widget
     *         The new popup widget content.
     */
    public void setContent(IsWidget widget) {
        if (widget != null) {
            setContent(widget.asWidget());
        }
    }

    /**
     * Sets the popup widget content.
     *
     * @param widget
     *         The new popup widget content.
     */
    @Override
    public void setContent(Widget widget) {
        popup.setWidget(widget);
    }

    /**
     * Sets the popup widget width.
     *
     * @param width
     *         The width.
     */
    public void setWidth(String width) {
        if (width != null) {
            popup.setWidth(width);
        }
    }

}