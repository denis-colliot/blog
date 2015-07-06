package dco.app.blog.client.dispatch;

/**
 * Implementations of this interface can be added to a {@link DispatchAsync} implementation to intercept exceptions
 * which return from further up the chain.
 *
 * @author Denis
 */
public interface ExceptionHandler {

    /**
     * Exception handler status enumeration.
     *
     * @author Denis
     */
    public static enum Status {

        /**
         * Stops the result handling (does not execute command callback implementation).
         */
        STOP,

        /**
         * Continues the result handling process and transmits it to command callback implementation.
         */
        CONTINUE;

    }

    /**
     * This method is called when an exception occurs. Return {@link Status#STOP} to indicate that the exception has been
     * handled and further processing should not occur. Return {@link Status#CONTINUE} to indicate that further processing
     * should occur.
     *
     * @param e
     *         The exception.
     * @return The status after execution.
     */
    Status onFailure(final Throwable e);

}