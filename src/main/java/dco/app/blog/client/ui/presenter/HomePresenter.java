package dco.app.blog.client.ui.presenter;

import com.google.inject.ImplementedBy;
import dco.app.blog.client.config.Injector;
import dco.app.blog.client.navigation.Page;
import dco.app.blog.client.navigation.PageRequest;
import dco.app.blog.client.ui.presenter.base.AbstractPagePresenter;
import dco.app.blog.client.ui.view.HomeView;
import dco.app.blog.client.ui.view.base.ViewInterface;

/**
 * Home presenter.
 *
 * @author Denis
 */
public class HomePresenter extends AbstractPagePresenter<HomePresenter.View> {

    /**
     * Home view.
     */
    @ImplementedBy(HomeView.class)
    public static interface View extends ViewInterface {

    }

    protected HomePresenter(final View view, final Injector injector) {
        super(view, injector);
    }

    @Override
    public Page getPage() {
        return Page.HOME;
    }

    @Override
    public void onPageRequest(final PageRequest request) {
        // TODO
    }
}
