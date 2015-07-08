package dco.app.blog.client.ui.presenter;

import com.google.inject.ImplementedBy;
import dco.app.blog.client.config.Injector;
import dco.app.blog.client.navigation.Page;
import dco.app.blog.client.navigation.PageRequest;
import dco.app.blog.client.ui.presenter.base.AbstractPagePresenter;
import dco.app.blog.client.ui.view.LoginView;
import dco.app.blog.client.ui.view.base.ViewInterface;

/**
 * Login presenter.
 *
 * @author Denis
 */
public class LoginPresenter extends AbstractPagePresenter<LoginPresenter.View> {

    /**
     * Home view.
     */
    @ImplementedBy(LoginView.class)
    public static interface View extends ViewInterface {

    }

    protected LoginPresenter(final View view, final Injector injector) {
        super(view, injector);
    }

    @Override
    public Page getPage() {
        return Page.LOGIN;
    }

    @Override
    public void onPageRequest(final PageRequest request) {
        // TODO
    }
}
