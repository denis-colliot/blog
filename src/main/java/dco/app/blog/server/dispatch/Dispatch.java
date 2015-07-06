package dco.app.blog.server.dispatch;

import dco.app.blog.client.security.SecureDispatchAsync;
import dco.app.blog.server.model.User;
import dco.app.blog.shared.command.base.Command;
import dco.app.blog.shared.command.result.base.Result;
import dco.app.blog.shared.dispatch.DispatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * Executes commands and returns the results.
 *
 * @author Denis
 */
public interface Dispatch {

    /**
     * Executes the specified {@code command} and returns the appropriate result.
     *
     * @param <C>
     *         The command type.
     * @param <R>
     *         The {@link Result} type returned by {@code command} execution.
     * @param commandExecution
     *         The command execution (containing {@link Command} to execute).
     * @param user
     *         The user executing the command.
     * @param request
     *         The servlet HTTP request.
     * @return The command's result.
     * @throws DispatchException
     *         If the command execution failed.
     */
    <C extends Command<R>, R extends Result> R execute(final SecureDispatchAsync.CommandExecution<C, R> commandExecution, final User user,
                                                       final HttpServletRequest request) throws DispatchException;

}