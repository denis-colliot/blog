package dco.app.blog.client.ui.view.base;

import com.google.gwt.user.client.ui.IsWidget;
import dco.app.blog.client.ui.widget.Loadable;

/**
 * Each presenter's view implementation should implement this interface.
 *
 * @author Denis
 */
public interface ViewInterface extends IsWidget {

    /**
     * Initializes the view components.<br/>
     * <b>Executed only one time on first load.</b>
     */
    void initialize();

    /**
     * View revealed callback.<br/>
     * <b>Executed each time the view is revealed.</b>
     */
    void onViewRevealed();

    /**
     * Gets the {@link Loadable} elements list that will be locked when action is executed.
     *
     * @return the {@link Loadable} elements list that will be locked when action is executed.
     */
    Loadable[] getLoadables();

    /**
     * <p>
     * Returns if the current view should be shown on <em>full page</em> and not within the common content area.
     * </p>
     * <p>
     * <em>Default implementation returns {@code false}.</em>
     * </p>
     *
     * @return {@code true} if the current view should be shown on <em>full page</em> and not within common content area,
     * {@code false} if the view should be shown within common content area.
     */
    boolean isFullPage();

}