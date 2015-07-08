package dco.app.blog.client.security;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.inject.Inject;
import dco.app.blog.client.dispatch.ExceptionHandler;
import dco.app.blog.client.event.bus.EventBus;
import dco.app.blog.client.i18n.I18N;
import dco.app.blog.client.ui.notification.N10N;
import dco.app.blog.shared.dispatch.CommandException;
import dco.app.blog.shared.dispatch.FunctionalException;
import dco.app.blog.shared.security.InvalidSessionException;
import dco.app.blog.shared.security.UnauthorizedAccessException;

/**
 * Handles exception thrown by the command pattern.
 *
 * @author Denis
 */
public class SecureExceptionHandler implements ExceptionHandler {

    /**
     * The injected event bus.
     */
    private final EventBus eventBus;

    @Inject
    public SecureExceptionHandler(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Status onFailure(final Throwable e) {

        if (e instanceof IncompatibleRemoteServiceException) {
            // The correct response to receiving an instance of this exception in the AsyncCallback.onFailure(Throwable)
            // method is to get the application into a state where a browser refresh can be done.
            // TODO This needs to be handled by the user interface somewhere.
            if (Log.isErrorEnabled()) {
                Log.error("Exception handler intercepts an IncompatibleRemoteServiceException ; application refresh should fix the problem.", e);
            }

            N10N.error(I18N.CONSTANTS.navigation_incompatibleRemoteServiceException());

            return Status.STOP;

        } else if (e instanceof InvocationException) {
            // Network connection problem.
            if (Log.isErrorEnabled()) {
                Log.error("Exception handler intercepts an InvocationException probably due to network connection problem.", e);
            }

            N10N.errorNotif(I18N.CONSTANTS.navigation_networkProblem());

            return Status.STOP;

        } else if (e instanceof InvalidSessionException) {

            // Intercepts an invalid session exception.

            if (Log.isDebugEnabled()) {
                Log.debug("Exception handler intercepts an invalid session.");
            }

            // Logout: clears the session and navigates to login page.
            eventBus.logout();

            return Status.STOP;

        } else if (e instanceof UnauthorizedAccessException) {

            // Intercepts a unauthorized access.

            if (Log.isDebugEnabled()) {
                Log.debug("Exception handler intercepts an unauthorized access.", e);
            }

            // The user executed an unauthorized action.

            // Inform the user.
            N10N.info(I18N.CONSTANTS.navigation_unauthorized_action());

            return Status.STOP;

        } else if (e instanceof FunctionalException) {

            // Intercepts a functional exception.

            if (Log.isWarnEnabled()) {
                Log.warn("Exception handler intercepts a functional exception.", e);
            }

            // Lets the dispatch manages the error.
            return Status.CONTINUE;

        } else if (e instanceof CommandException) {

            // Intercepts a service exception.

            if (Log.isErrorEnabled()) {
                Log.error("Exception handler intercepts a command exception.", e);
            }

            // Lets the dispatch manages the error.
            return Status.CONTINUE;

        } else {

            if (Log.isFatalEnabled()) {
                Log.fatal("Exception handler intercepts an unknown exception.", e);
            }

            // Lets the dispatch manages the error.
            return Status.CONTINUE;
        }
    }
}