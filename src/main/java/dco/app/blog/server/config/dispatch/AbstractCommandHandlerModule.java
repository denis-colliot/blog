package dco.app.blog.server.config.dispatch;

import com.google.inject.AbstractModule;
import com.google.inject.internal.UniqueAnnotations;
import dco.app.blog.server.dispatch.CommandHandler;
import dco.app.blog.shared.command.base.Command;
import dco.app.blog.shared.command.result.base.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This is an abstract base class that configures Guice to inject {@link dco.app.blog.server.dispatch.Dispatch} and
 * {@link CommandHandler} instances.<br>
 * If no other prior instance of {@link DispatchModule} has been installed, the standard {@link
 * dco.app.blog.server.dispatch.Dispatch} and {@link dco.app.blog.server.dispatch.CommandHandlerRegistry} classes will
 * be configured.
 * </p>
 * <p>
 * Implement the {@link #configureHandlers()} method and call {@link #bindHandler(Class, Class)} to register handler
 * implementations. For example:
 * <pre>
 * public class CommandHandlerModule extends AbstractCommandHandlerModule {
 *      \@Override
 *      protected void configureHandlers() {
 *          bindHandler( MyCommand.class, MyCommandHandler.class );
 *      }
 * }
 * </pre>
 * </p>
 *
 * @author Denis
 */
public abstract class AbstractCommandHandlerModule extends AbstractModule {

    /**
     * {@link CommandHandlerMap} implementation.
     *
     * @param <C>
     *         The command type.
     * @param <R>
     *         The command result type.
     * @author Denis Colliot (dcolliot@ideia.fr)
     */
    private static class CommandHandlerMapImpl<C extends Command<R>, R extends Result> implements CommandHandlerMap<C, R> {

        private final Class<C> commandClass;

        private final Class<? extends CommandHandler<C, R>> handlerClass;

        public CommandHandlerMapImpl(final Class<C> commandClass, final Class<? extends CommandHandler<C, R>> handlerClass) {
            this.commandClass = commandClass;
            this.handlerClass = handlerClass;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Class<C> getCommandClass() {
            return commandClass;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Class<? extends CommandHandler<C, R>> getCommandHandlerClass() {
            return handlerClass;
        }

    }

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractCommandHandlerModule.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected final void configure() {

        if (LOG.isInfoEnabled()) {
            LOG.info("Installing command-handler module.");
        }

        // This will only get installed once due to equals/hashCode override.
        install(new DispatchModule());

        configureHandlers();
    }

    /**
     * Override this method to configure handlers.
     */
    protected abstract void configureHandlers();

    /**
     * Binds the specified {@link CommandHandler} instance class.
     *
     * @param commandClass
     *         The command class.
     * @param handlerClass
     *         The command handler class.
     */
    protected final <C extends Command<R>, R extends Result, H extends CommandHandler<C, R>> void bindHandler(final Class<C> commandClass,
                                                                                                              final Class<H> handlerClass) {
        bind(CommandHandlerMap.class).annotatedWith(UniqueAnnotations.create()).toInstance(new CommandHandlerMapImpl<C, R>(commandClass, handlerClass));
    }

}