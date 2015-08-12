package dco.app.blog.client.ui.presenter;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import dco.app.blog.client.config.Injector;
import dco.app.blog.client.navigation.Page;
import dco.app.blog.client.navigation.PageRequest;
import dco.app.blog.client.ui.presenter.base.AbstractPagePresenter;
import dco.app.blog.client.ui.view.TripsView;
import dco.app.blog.client.ui.view.base.ViewInterface;

/**
 * Trips presenter.
 *
 * @author Denis
 */
public class TripsPresenter extends AbstractPagePresenter<TripsPresenter.View> {

    /**
     * View interface.
     */
    @ImplementedBy(TripsView.class)
    public static interface View extends ViewInterface {

    }

    @Inject
    protected TripsPresenter(final View view, final Injector injector) {
        super(view, injector);
    }

    @Override
    public Page getPage() {
        return Page.TRIPS;
    }

    @Override
    public void onPageRequest(final PageRequest request) {
        // TODO
    }
}
