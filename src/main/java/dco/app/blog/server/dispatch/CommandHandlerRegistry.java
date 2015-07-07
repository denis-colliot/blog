package dco.app.blog.server.dispatch;

import dco.app.blog.shared.command.base.Command;
import dco.app.blog.shared.command.result.base.Result;

/**
 * Command-Handler registry interface.
 *
 * @author Denis
 */
public interface CommandHandlerRegistry {

    /**
     * Searches the registry and returns the first handler which supports the specied command, or <code>null</code> if
     * none is available.
     *
     * @param command
     *         The command.
     * @return The command handler.
     */
    <C extends Command<R>, R extends Result> CommandHandler<C, R> findHandler(final C command);

    /**
     * Clears all registered handlers from the registry.
     */
    void clearHandlers();

    /**
     * Registers the specified {@link CommandHandler} class with the registry.
     *
     * @param commandClass
     *         The command class the handler handles.
     * @param handlerClass
     *         The handler class.
     */
    <C extends Command<R>, R extends Result> void addHandlerClass(final Class<C> commandClass, final Class<? extends CommandHandler<C, R>> handlerClass);

    /**
     * Removes any registration of the specified class, as well as any instances which have been created.
     *
     * @param commandClass
     *         The command class the handler handles.
     * @param handlerClass
     *         The handler class.
     */
    <C extends Command<R>, R extends Result> void removeHandlerClass(final Class<C> commandClass, final Class<? extends CommandHandler<C, R>> handlerClass);

}