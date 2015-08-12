package dco.app.blog.client.ui.presenter;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import dco.app.blog.client.config.Injector;
import dco.app.blog.client.navigation.Page;
import dco.app.blog.client.navigation.PageRequest;
import dco.app.blog.client.ui.presenter.base.AbstractPagePresenter;
import dco.app.blog.client.ui.view.TvShowsView;
import dco.app.blog.client.ui.view.base.ViewInterface;

/**
 * TV Shows presenter.
 *
 * @author Denis
 */
public class TvShowsPresenter extends AbstractPagePresenter<TvShowsPresenter.View> {

    /**
     * View interface.
     */
    @ImplementedBy(TvShowsView.class)
    public static interface View extends ViewInterface {

    }

    @Inject
    protected TvShowsPresenter(final View view, final Injector injector) {
        super(view, injector);
    }

    @Override
    public Page getPage() {
        return Page.TV_SHOWS;
    }

    @Override
    public void onPageRequest(final PageRequest request) {
        // TODO
    }
}
