package dco.app.blog.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import dco.app.blog.client.config.Injector;
import dco.app.blog.client.ui.notification.N10N;

/**
 * Application module entry point.
 */
public class Blog implements EntryPoint {

    /**
     * GIN injector.
     */
    private Injector injector;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onModuleLoad() {

        if (Log.isDebugEnabled()) {
            Log.debug("Application > Client init start.");
        }

        // GIN injector instantiation.
        if (Log.isDebugEnabled()) {
            Log.debug("Application > Creates GIN injector.");
        }
        injector = GWT.create(Injector.class);

        // Set GXT theme.
        if (Log.isDebugEnabled()) {
            Log.debug("Application > Sets GWT default theme.");
        }

        // Uncaught exception handler.
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {

            @Override
            public void onUncaughtException(final Throwable e) {
                if (Log.isErrorEnabled()) {
                    Log.error("Uncaught exception on client-side.", e);
                }
                // TODO [i18n] Uncaught exception error message.
                N10N.error("An unexpected error has occured.");
            }
        });

        clientInitializing();

        if (Log.isDebugEnabled()) {
            Log.debug("Application > Client init end.");
        }

    }

    /**
     * Initializes client presenters and requests page access to the {@link dco.app.blog.client.navigation.PageManager}.
     */
    private void clientInitializing() {

        if (Log.isDebugEnabled()) {
            Log.debug("Application > Register presenters and navigation handlers.");
        }

        // Application presenters.
        injector.getApplicationPresenter();

        // Pages.
        injector.getLoginPresenter();
        injector.getHomePresenter();

        // Propagates the network state.
        injector.getPageManager().fireCurrentPlace();
    }

}
