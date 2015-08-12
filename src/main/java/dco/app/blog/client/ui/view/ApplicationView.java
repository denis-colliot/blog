package dco.app.blog.client.ui.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Singleton;
import dco.app.blog.client.navigation.Zone;
import dco.app.blog.client.ui.presenter.ApplicationPresenter;
import dco.app.blog.client.ui.view.base.AbstractView;
import dco.app.blog.client.ui.view.base.ViewInterface;
import dco.app.blog.client.ui.widget.Loadable;
import dco.app.blog.client.util.MessageType;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBar;

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
public final class ApplicationView extends AbstractView implements ApplicationPresenter.View {

    /**
     * {@link com.google.gwt.uibinder.client.UiBinder} interface adapted to {@link ApplicationView}.
     */
    @UiTemplate("ApplicationView.ui.xml")
    static interface ViewUiBinder extends UiBinder<Widget, ApplicationView> {
    }

    @UiField
    HTML message;

    @UiField
    HasOneWidget container;

    @UiField
    MaterialNavBar navBar;

    @UiField
    MaterialLink navLinkTrips;

    @UiField
    MaterialLink navLinkTvShows;

    @UiField
    MaterialLink navLinkMe;

    @UiField
    MaterialLink menuLinkTrips;

    @UiField
    MaterialLink menuLinkTvShows;

    @UiField
    MaterialLink menuLinkMe;

    /**
     * Instantiates the application frame.
     */
    public ApplicationView() {
        final ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
        initWidget(uiBinder.createAndBindUi(this));

        RootPanel.get().add(this);
    }

    @Override
    public Widget asWidget() {
        return RootPanel.get();
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
        container.setWidget(presenterWidget.asWidget());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPageMessageVisible(boolean visible) {
        message.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPageMessage(String html, MessageType type) {
        message.setHTML(html);
        MessageType.applyStyleName(message, type);
    }

    @Override
    public MaterialNavBar getNavBar() {
        return navBar;
    }

    @Override
    public HasClickHandlers getNavLinkTrips() {
        return navLinkTrips;
    }

    @Override
    public HasClickHandlers getNavLinkTvShows() {
        return navLinkTvShows;
    }

    @Override
    public HasClickHandlers getNavLinkMe() {
        return navLinkMe;
    }

    @Override
    public HasClickHandlers getMenuLinkTrips() {
        return menuLinkTrips;
    }

    @Override
    public HasClickHandlers getMenuLinkTvShows() {
        return menuLinkTvShows;
    }

    @Override
    public HasClickHandlers getMenuLinkMe() {
        return menuLinkMe;
    }
}