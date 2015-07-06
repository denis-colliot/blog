package dco.app.blog.client.security;

import com.google.gwt.user.client.rpc.AsyncCallback;
import dco.app.blog.shared.command.base.Command;
import dco.app.blog.shared.command.result.base.Result;

/**
 * Secure dispatch asynchronous service.
 *
 * @author Denis
 */
public interface SecureDispatchServiceAsync {

    /**
     * Executes the given {@code commandExecution} corresponding {@link Command} and executes the given {@code callback}
     * once command has been processed.
     *
     * @param <C>
     *         Command type.
     * @param <R>
     *         Result type.
     * @param commandExecution
     *         The {@link SecureDispatchAsync.CommandExecution} containing {@link Command} to execute.
     * @param callback
     *         The callback executed once command has been processed.
     */
    <C extends Command<R>, R extends Result> void execute(final SecureDispatchAsync.CommandExecution<C, R> commandExecution,
                                                          final AsyncCallback<Result> callback);

}