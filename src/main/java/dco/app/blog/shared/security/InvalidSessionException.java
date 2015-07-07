package dco.app.blog.shared.security;

import dco.app.blog.shared.dispatch.CommandException;

/**
 * <p>
 * Invalid session exception.
 * </p>
 * <p>
 * Thrown when user session token is invalid (expired, corrupted, etc.).
 * </p>
 *
 * @author Denis
 */
public class InvalidSessionException extends CommandException {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 7539134845675500300L;

    public InvalidSessionException() {
        // Serialization.
    }

    public InvalidSessionException(String message) {
        super(message);
    }
}