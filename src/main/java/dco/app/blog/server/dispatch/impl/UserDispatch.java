package dco.app.blog.server.dispatch.impl;

import com.google.inject.Inject;
import dco.app.blog.client.navigation.Page;
import dco.app.blog.client.navigation.PageRequest;
import dco.app.blog.client.navigation.RequestParameter;
import dco.app.blog.client.security.SecureDispatchAsync;
import dco.app.blog.server.dispatch.CommandHandler;
import dco.app.blog.server.dispatch.CommandHandlerRegistry;
import dco.app.blog.server.dispatch.Dispatch;
import dco.app.blog.server.dispatch.ExecutionContext;
import dco.app.blog.server.model.User;
import dco.app.blog.server.servlet.base.ServletExecutionContext;
import dco.app.blog.shared.command.base.Command;
import dco.app.blog.shared.command.result.base.Result;
import dco.app.blog.shared.dispatch.CommandException;
import dco.app.blog.shared.dispatch.DispatchException;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Dispatch custom implementation.
 *
 * @author Denis Colliot (dcolliot@ideia.fr)
 */
public class UserDispatch implements Dispatch {

    /**
     * Execution context provided to handlers in order to execute sub-commands.
     *
     * @author Denis Colliot (dcolliot@ideia.fr)
     */
    public static final class UserExecutionContext extends ServletExecutionContext implements ExecutionContext {

        /**
         * The dispatch instance.
         */
        private final UserDispatch dispatch;

        /**
         * Sub-commands.
         */
        private final List<CommandResult<?, ?>> commandResults;

        /**
         * The application URL.
         */
        private final String applicationUrl;

        /**
         * Initializes a new user execution context.
         *
         * @param dispatch
         *         See {@link #dispatch}.
         * @param user
         *         See {@link #user}.
         * @param request
         *         See {@link #request}.
         * @param originPageToken
         *         See {@link #originPageToken}.
         */
        private UserExecutionContext(final UserDispatch dispatch, final User user, final HttpServletRequest request, final String originPageToken) {

            super(user, request, originPageToken);

            this.dispatch = dispatch;
            this.commandResults = new java.util.ArrayList<CommandResult<?, ?>>();
            this.applicationUrl = request.getHeader("Referer").split(PageRequest.URL_TOKEN)[0];
        }

        /**
         * <p>
         * Initializes a new user execution context from the given {@code servletContext}.
         * </p>
         * <p>
         * <b>Warning: this context cannot allow sub-command execution or provide application url.</b>
         * </p>
         *
         * @param servletContext
         *         The servlet execution context.
         */
        public UserExecutionContext(final ServletExecutionContext servletContext) {

            super(servletContext.getUser(), servletContext.getRequest(), servletContext.getOriginPageToken());

            this.dispatch = null;
            this.commandResults = null;
            this.applicationUrl = null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <C extends Command<R>, R extends Result> R execute(final C command) throws CommandException {
            return execute(command, true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <C extends Command<R>, R extends Result> R execute(final C command, final boolean allowRollback) throws CommandException {

            // Executes the sub-action.
            final R result = dispatch.doExecute(command, this);

            // Registers it and its result.
            if (allowRollback) {
                commandResults.add(new CommandResult<C, R>(command, result));
            }

            return result;
        }

        /**
         * Cancels the memorized commands or results.
         *
         * @throws DispatchException
         */
        private void rollback() throws DispatchException {
            for (int i = commandResults.size() - 1; i >= 0; i--) {
                final CommandResult<?, ?> actionResult = commandResults.get(i);
                rollback(actionResult);
            }
        }

        /**
         * Rollbacks a command execution.
         *
         * @param <C>
         *         The command type.
         * @param <R>
         *         The result type.
         * @param commandResult
         *         The command and the command result.
         * @throws DispatchException
         *         If the roll back failed.
         */
        private <C extends Command<R>, R extends Result> void rollback(final CommandResult<C, R> commandResult) throws DispatchException {
            dispatch.doRollback(commandResult.getCommand(), commandResult.getResult(), this);
        }

        /**
         * The application URL.
         *
         * @return The application URL.
         */
        public final String getApplicationUrl() {
            return getApplicationUrl(null, null);
        }

        /**
         * The application URL.
         *
         * @param page
         *         The specific page to include into URL.
         * @param parameters
         *         The page parameters to include into URL.
         * @return The application URL.
         */
        public final String getApplicationUrl(final Page page, final Map<RequestParameter, String> parameters) {
            if (page == null) {
                return applicationUrl;
            }
            return applicationUrl + PageRequest.toUrl(page.getToken(), parameters);
        }
    }

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(UserDispatch.class);

    /**
     * The command-handler registry instance used to find commands handler.
     */
    private final CommandHandlerRegistry handlerRegistry;

    @Inject
    public UserDispatch(final CommandHandlerRegistry handlerRegistry) {
        this.handlerRegistry = handlerRegistry;
    }

    /**
     * Find the given {@code command} corresponding handler.
     *
     * @param <C>
     *         The command type.
     * @param <R>
     *         The command result type.
     * @param command
     *         The command.
     * @return the given {@code command} corresponding handler.
     * @throws UnsupportedCommandException
     *         If no handler cannot be found for the command.
     */
    private <C extends Command<R>, R extends Result> CommandHandler<C, R> findHandler(final C command) throws UnsupportedCommandException {

        // Asks the handler to the registry.
        final CommandHandler<C, R> handler = handlerRegistry.findHandler(command);

        // If there is no handler, throws an exception.
        if (handler == null) {
            throw new UnsupportedCommandException(command);
        }

        return handler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <C extends Command<R>, R extends Result> R execute(final SecureDispatchAsync.CommandExecution<C, R> commandExecution, final User user, final HttpServletRequest request)
            throws DispatchException {

        // Builds a new user execution context.
        final UserExecutionContext context = new UserExecutionContext(this, user, request, commandExecution.getCurrentPageToken());

        try {

            // Tries to execute the action.
            return doExecute(commandExecution.getCommand(), context);

        } catch (final CommandException e) {
            // Rollback if necessary.
            context.rollback();
            throw e;
        }
    }

    /**
     * Executes the given command from server side.
     *
     * @param <C>
     *         Command type.
     * @param <R>
     *         Result type.
     * @param command
     *         Command to execute.
     * @param executionContext
     *         Execution context of the servlet.
     * @return Execution result.
     * @throws DispatchException
     *         If the command handler execution fails.
     */
    public <C extends Command<R>, R extends Result> R execute(final C command, final ServletExecutionContext executionContext)
            throws DispatchException {

        // Builds a new user execution context.
        final UserExecutionContext context = new UserExecutionContext(this, executionContext.getUser(), executionContext.getRequest(), null);

        try {
            // Tries to execute the action.
            return doExecute(command, context);

        } catch (final CommandException e) {
            // Rollback if necessary.
            context.rollback();
            throw e;
        }
    }

    /**
     * Executes a command.
     *
     * @param <C>
     *         The command type.
     * @param <R>
     *         The command result type.
     * @param context
     *         The execution context.
     * @return The command execution result.
     * @throws CommandException
     *         If the command handler execution fails.
     */
    private <C extends Command<R>, R extends Result> R doExecute(final C command, final UserExecutionContext context) throws CommandException {

        // Retrieves the handler.
        final CommandHandler<C, R> handler = findHandler(command);

        if (LOG.isDebugEnabled()) {
            LOG.debug("EXECUTING COMMAND - Command: '{}' ; Handler: '{}' ; User: '{}'.", command, handler, context.getUser());
        }

        final StopWatch chrono;
        if (LOG.isDebugEnabled()) {
            chrono = new StopWatch();
            chrono.start();

        } else {
            chrono = null;
        }

        // Asks for the action execution.
        final R executionResult = handler.execute(command, context);

        if (LOG.isDebugEnabled() && chrono != null) {
            chrono.stop();
            LOG.debug("COMMAND '{}' EXECUTED IN {} MS.", command, chrono.getTime());
        }

        return executionResult;
    }

    /**
     * Rollbacks a command.
     *
     * @param <C>
     *         The command type.
     * @param <R>
     *         The command result type.
     * @param command
     *         The command.
     * @param result
     *         The command result.
     * @param context
     *         The execution context.
     * @throws DispatchException
     *         If execution fails.
     */
    private <C extends Command<R>, R extends Result> void doRollback(final C command, final R result, final UserExecutionContext context)
            throws DispatchException {

        // Retrieves the handler.
        final CommandHandler<C, R> handler = findHandler(command);

        if (LOG.isInfoEnabled()) {
            LOG.info("ROLLBACKING COMMAND - Action: '{}' ; Handler: '{}' ; User: '{}'.", command, handler, context.getUser());
        }

        // Asks for the action rollback.
        handler.rollback(command, result, context);
    }

}