package dco.app.blog.client.dispatch;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import dco.app.blog.client.ui.widget.Loadable;
import dco.app.blog.shared.command.base.Command;
import dco.app.blog.shared.command.result.base.Result;

import java.util.Collection;

/**
 * <p>
 * An abstract base class that provides methods that can be called to handle success or failure results from the remote
 * service.
 * </p>
 * <p>
 * These should be called by the implementation of
 * {@link DispatchAsync#execute(Command, AsyncCallback, Loadable...)}.
 * </p>
 *
 * @author Denis
 */
public abstract class AbstractDispatchAsync implements DispatchAsync {

    /**
     * The dispatch exception handler.
     */
    @Inject
    private ExceptionHandler exceptionHandler;

    /**
     * Handles a failed command execution.
     *
     * @param command
     *         The failed command.
     * @param caught
     *         The exception.
     * @param callback
     *         The execution callback.
     */
    protected <C extends Command<R>, R extends Result> void onFailure(final C command, final Throwable caught,
                                                                      final AsyncCallback<R> callback,
                                                                      final Collection<Loadable> loadables) {

        final ExceptionHandler.Status status;

        if (exceptionHandler != null) {
            status = exceptionHandler.onFailure(caught);
        } else {
            status = ExceptionHandler.Status.CONTINUE;
        }

        switch (status) {

            case STOP:
                return;

            case CONTINUE:
                callback.onFailure(caught);
                break;
        }
    }

    /**
     * Handles a success command execution.
     *
     * @param command
     *         The succeed command.
     * @param result
     *         The command execution result.
     * @param callback
     *         The execution callback.
     */
    protected <C extends Command<R>, R extends Result> void onSuccess(final C command,
                                                                      final R result,
                                                                      final AsyncCallback<R> callback) {
        callback.onSuccess(result);
    }

}