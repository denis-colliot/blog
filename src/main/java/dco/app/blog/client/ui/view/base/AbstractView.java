package dco.app.blog.client.ui.view.base;

import com.google.gwt.user.client.ui.Composite;
import dco.app.blog.client.ui.widget.Loadable;

/**
 * <p>
 * Default abstract view.<br/>
 * The abstract view hides a default {@link Composite} container to layout its component(s).
 * </p>
 * <p/>
 * <b>Rules to add a new view:</b>
 * <ol>
 * <li>Define a new presenter managing the view ; see {@link dco.app.blog.client.ui.presenter.base.AbstractPresenter}
 * javadoc.</li>
 * <li>Create a new class inheriting {@link AbstractView} with {@link com.google.inject.Singleton} annotation
 * (<u>crucial</u>).</li>
 * <li>View implementation should use {@code Composite.initWidget(IsWidget)} in the {@link #initialize()} method.</li>
 * </ol>
 *
 * @author Denis
 */
public abstract class AbstractView extends Composite implements ViewInterface {

    /**
     * {@inheritDoc}
     */
    @Override
    public Loadable[] getLoadables() {
        // Can be overridden by sub views.
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewRevealed() {
        // Default implementation does nothing.
        // Can be overridden by sub views implementations.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFullPage() {
        // Default implementation returns false.
        return false;
    }

}