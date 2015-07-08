package dco.app.blog.client.ui.view;

import com.google.gwt.user.client.ui.HTML;
import dco.app.blog.client.ui.presenter.HomePresenter;
import dco.app.blog.client.ui.view.base.AbstractView;

/**
 * Home view.
 *
 * @author Denis
 */
public class HomeView extends AbstractView implements HomePresenter.View {

    @Override
    public void initialize() {
        initWidget(new HTML("Home view"));
    }
}
