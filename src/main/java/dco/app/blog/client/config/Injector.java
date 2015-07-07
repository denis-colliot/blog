package dco.app.blog.client.config;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import dco.app.blog.client.dispatch.DispatchAsync;
import dco.app.blog.client.event.bus.EventBus;
import dco.app.blog.client.navigation.PageManager;
import dco.app.blog.client.security.AuthenticationProvider;
import dco.app.blog.client.ui.presenter.ApplicationPresenter;

/**
 * GIN injector.
 *
 * @author Denis
 */
@GinModules(value = {
        ClientModule.class
})
public interface Injector extends Ginjector {

    EventBus getEventBus();

    DispatchAsync getDispatch();

    PageManager getPageManager();

    ApplicationPresenter getApplicationPresenter();

    AuthenticationProvider getAuthenticationProvider();

}