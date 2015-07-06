package dco.app.blog.client.ui.view.base;

import com.google.gwt.user.client.ui.HasWidgets;

/**
 * Each <b>parent</b> presenter's view implementation should implement this interface.
 *
 * @author Denis
 */
public interface HasSubView extends ViewInterface {

    /**
     * Returns the sub-presenters placeholder, where sub-views will be shown.
     *
     * @return The sub-presenters placeholder.
     */
    HasWidgets.ForIsWidget getPlaceHolder();

}