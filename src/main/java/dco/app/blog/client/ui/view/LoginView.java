package dco.app.blog.client.ui.view;

import com.google.gwt.user.client.ui.HTML;
import dco.app.blog.client.ui.presenter.LoginPresenter;
import dco.app.blog.client.ui.view.base.AbstractView;

/**
 * Login view.
 *
 * @author Denis
 */
public class LoginView extends AbstractView implements LoginPresenter.View {

    @Override
    public void initialize() {
        initWidget(new HTML("Login view"));
    }
}
