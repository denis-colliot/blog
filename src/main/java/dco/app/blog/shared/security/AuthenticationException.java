package dco.app.blog.shared.security;

import dco.app.blog.shared.dispatch.FunctionalException;

/**
 * <p>
 * Authentication exception.
 * </p>
 * <p>
 * Thrown when user authentication fails (invalid login and/or password).
 * </p>
 *
 * @author Denis
 */
public class AuthenticationException extends FunctionalException {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 5293424643003033715L;

    public AuthenticationException() {
        super(FunctionalException.ErrorCode.AUTHENTICATION_FAILURE);
    }

}