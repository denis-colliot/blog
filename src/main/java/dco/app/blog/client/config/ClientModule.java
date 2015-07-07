package dco.app.blog.client.config;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import dco.app.blog.client.dispatch.DispatchAsync;
import dco.app.blog.client.dispatch.ExceptionHandler;
import dco.app.blog.client.event.bus.EventBus;
import dco.app.blog.client.event.bus.EventBusImpl;
import dco.app.blog.client.navigation.PageManager;
import dco.app.blog.client.security.SecureDispatchAsync;

/**
 * GIN module to bind presenters and views.
 *
 * @author Denis
 */
public class ClientModule extends AbstractGinModule {

    @Override
    protected void configure() {

        // Navigation.
        bind(EventBus.class).to(EventBusImpl.class).in(Singleton.class);
        bind(PageManager.class).in(Singleton.class);

        // Dispatch & security.
        bind(ExceptionHandler.class).to(SecureExceptionHandler.class).in(Singleton.class);
        bind(DispatchAsync.class).to(SecureDispatchAsync.class).in(Singleton.class);

        // Presenters rely on "@ImplementedBy" annotation on their view interface.
    }

}