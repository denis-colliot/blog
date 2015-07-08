package dco.app.blog.client.ui.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Singleton;
import dco.app.blog.client.navigation.Zone;
import dco.app.blog.client.ui.presenter.ApplicationPresenter;
import dco.app.blog.client.ui.view.base.ViewInterface;
import dco.app.blog.client.ui.widget.Loadable;
import dco.app.blog.client.util.MessageType;

import java.util.Map;

/**
 * <p>
 * Application frame view.
 * </p>
 * <p>
 * This is the only <em>view</em> that does not inherit {@link dco.app.blog.client.ui.view.base.AbstractView}.
 * </p>
 *
 * @author Denis
 */
@Singleton
public class ApplicationView implements ApplicationPresenter.View {

    /**
     * {@link com.google.gwt.uibinder.client.UiBinder} interface adapted to {@link ApplicationView}.
     */
    @UiTemplate("ApplicationView.ui.xml")
    static interface ApplicationViewUiBinder extends UiBinder<Widget, ApplicationView> {
    }

    @UiField
    Panel wrapperPanel;

    @UiField
    HTML messageLabel;

    @UiField
    Panel contentPanel;

    /**
     * Instantiates the application frame.
     */
    public ApplicationView() {

        final ApplicationViewUiBinder uiBinder = GWT.create(ApplicationViewUiBinder.class);
        uiBinder.createAndBindUi(this);

        // Root panel initialization.
        RootPanel.get().add(wrapperPanel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Widget asWidget() {
        return wrapperPanel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        // Not used (everything is initialized in the constructor).
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewRevealed() {
        // Not used.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Loadable[] getLoadables() {
        // Not used.
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFullPage() {
        // Not used.
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideLoadingPanel() {

        final RootPanel panel = RootPanel.get("loading");

        if (panel != null) {
            panel.setVisible(false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initZones(final Map<Zone, ViewInterface> zoneViews) {

        // TODO init zones.

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPresenter(final IsWidget presenterWidget, boolean fullPage) {
        contentPanel.clear();
        contentPanel.add(presenterWidget.asWidget());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPageMessageVisible(boolean visible) {
        messageLabel.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPageMessage(String html, MessageType type) {
        messageLabel.setHTML(html);
        MessageType.applyStyleName(messageLabel, type);
    }

}