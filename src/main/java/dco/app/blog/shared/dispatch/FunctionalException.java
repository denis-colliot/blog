package dco.app.blog.shared.dispatch;

import com.google.gwt.user.client.rpc.IsSerializable;
import dco.app.blog.client.i18n.I18N;
import dco.app.blog.client.util.ToStringBuilder;
import dco.app.blog.shared.util.ClientUtils;

/**
 * <p>
 * Functional command exception.
 * </p>
 * <p>
 * Exception thrown by services when a <em>functional</em> issue is detected and an appropriate message should be
 * displayed.
 * </p>
 *
 * @author Denis
 */
public class FunctionalException extends DispatchException {

    /**
     * The functional error codes with corresponding {@code i18n} messages.
     *
     * @author Denis
     */
    public static enum ErrorCode implements IsSerializable {

        /**
         * Authentication failure.<br>
         * No parameters.
         */
        AUTHENTICATION_FAILURE;
    }

    /**
     * Returns the functional exception message.<br/>
     * Method {@link FunctionalException#getParameter(int)} can be used to populate message dynamic parameters.
     *
     * @param exception
     *         The functional exception that may embed message parameter(s).
     * @param errorCode
     *         Functional error code (cannot be {@code null}).
     * @return the functional exception message.
     */
    private static String getMessage(final FunctionalException exception, final ErrorCode errorCode) {
        switch (errorCode) {

            case AUTHENTICATION_FAILURE:
                return I18N.CONSTANTS.login_bad_credentials();

            default:
                return errorCode.toString();
        }
    }

    /**
     * Returns the functional exception title.<br/>
     * Method {@link FunctionalException#getParameter(int)} can be used to populate title dynamic parameters.
     *
     * @param exception
     *         The functional exception that may embed title parameter(s)..
     * @param errorCode
     *         Functional error code (cannot be {@code null}).
     * @return the functional exception title, or {@code null} to use the default title.
     */
    private static String getTitle(final FunctionalException exception, final ErrorCode errorCode) {
        switch (errorCode) {

            default:
                return null; // Default title.
        }
    }

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -324930495914540987L;

    /**
     * This exception {@code errorType}. Cannot be {@code null} (assertion).
     *
     * @see ErrorCode
     */
    private ErrorCode errorCode;

    /**
     * The error message relative parameters. Can be {@code null}.<br/>
     * They are used to populate dynamic message parameters (be careful with order).
     */
    protected String[] parameters;

    protected FunctionalException() {
        // Serialization.
    }

    /**
     * Builds a {@link FunctionalException} according to the given message-relative {@link ErrorCode}.
     *
     * @param errorCode
     *         See {@link #errorCode}.
     * @param parameters
     *         See {@link #parameters}.
     */
    public FunctionalException(ErrorCode errorCode, String... parameters) {
        this(null, errorCode, parameters);
    }

    /**
     * Builds a {@link FunctionalException} according to the given message-relative {@link ErrorCode}.
     *
     * @param cause
     *         Exception that triggered this error.
     * @param errorCode
     *         See {@link #errorCode}.
     * @param parameters
     *         See {@link #parameters}.
     */
    public FunctionalException(Throwable cause, ErrorCode errorCode, String... parameters) {
        super(cause);
        assert errorCode != null : "Error code is required.";
        this.errorCode = errorCode;
        this.parameters = parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("Exception", "FunctionalException");
        builder.append("Error code", errorCode);
        return builder.toString();
    }

    /**
     * Returns the error message parameter specified at given {@code index}.
     *
     * @param index
     *         The parameter index.
     * @return the error message parameter specified at given {@code index}. Returns {@code null} if no parameter has
     * been
     * provided or {@code index} is out of bounds.
     * @see #parameters
     */
    public final String getParameter(int index) {
        if (parameters == null || index >= parameters.length || index < 0) {
            return null;
        }
        return parameters[index];
    }

    /**
     * Returns the number of parameters set into the current exception.
     *
     * @return the number of parameters set into the current exception.
     */
    public final int getParameterCount() {
        return parameters != null ? parameters.length : 0;
    }

    /**
     * Returns the current functional exception corresponding {@link ErrorCode}.
     *
     * @return The {@link ErrorCode} (never {@code null}).
     */
    public final ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Returns the functional error corresponding title (if any).
     *
     * @return The functional error corresponding title (if any), or {@code null}.
     */
    public final String getTitle() {
        if (!ClientUtils.isClient()) {
            return null;
        }
        return getTitle(this, errorCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getMessage() {
        if (!ClientUtils.isClient()) {
            return toString(); // Displays exception class name and error code.
        }
        return getMessage(this, errorCode);
    }

    /**
     * Verify if the actual error is of the given type
     *
     * @param errorCode
     *         Functional error code.
     * @return result of the comparison
     */
    public final boolean is(ErrorCode errorCode) {
        return this.errorCode == errorCode;
    }

}