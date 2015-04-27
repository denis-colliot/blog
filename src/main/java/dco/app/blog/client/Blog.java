package dco.app.blog.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Application module entry point.
 */
public class Blog implements EntryPoint {

    /**
     * {@inheritDoc}
     */
    public void onModuleLoad() {
        RootPanel.get().add(new HTML("GWT ready my friend."));
    }

}
