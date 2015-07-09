package dco.app.blog.server.config.dispatch;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import dco.app.blog.server.dispatch.CommandHandlerRegistry;
import dco.app.blog.server.dispatch.Dispatch;
import dco.app.blog.server.dispatch.impl.LazyCommandHandlerRegistry;
import dco.app.blog.server.dispatch.impl.UserDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This module will configure the implementation for the {@link Dispatch} and {@link CommandHandlerRegistry}
 * interfaces.<br>
 * If you want to override the defaults ({@code GuiceDispatch} and {@code DefaultCommandHandlerRegistry},
 * respectively), pass the override values into the constructor for this module and ensure it is installed
 * <b>before</b>  any {@link AbstractCommandHandlerModule} instances.
 *
 * @author Denis
 */
final class DispatchModule extends AbstractModule {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DispatchModule.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {

        if (LOG.isInfoEnabled()) {
            LOG.info("Installing dispatch module.");
        }

        bind(CommandHandlerRegistry.class).to(LazyCommandHandlerRegistry.class).in(Singleton.class);
        bind(Dispatch.class).to(UserDispatch.class).in(Singleton.class);

        // This will bind registered handlers to the registry.
        requestStaticInjection(CommandHandlerLinker.class);
    }

    /**
     * Override so that only one instance of this class will ever be installed in an {@link Injector}.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof DispatchModule;
    }

    /**
     * Override so that only one instance of this class will ever be installed in an {@link Injector}.
     */
    @Override
    public int hashCode() {
        return DispatchModule.class.hashCode();
    }

}