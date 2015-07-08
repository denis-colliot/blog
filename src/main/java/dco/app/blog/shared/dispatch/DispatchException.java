package dco.app.blog.shared.dispatch;

import dco.app.blog.client.util.ToStringBuilder;

import java.io.Serializable;

/**
 * An abstract superclass for exceptions that can be thrown by the Dispatch system.
 *
 * @author Denis
 */
public abstract class DispatchException extends Exception implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -6473670236082515600L;

    private String causeClassname;

    protected DispatchException() {
        // Serialization.
    }

    public DispatchException(final String message) {
        super(message);
    }

    public DispatchException(final Throwable cause) {
        super(cause);
        if (cause != null) {
            this.causeClassname = cause.getClass().getName();
        }
    }

    public DispatchException(final String message, final Throwable cause) {
        super(message + " (" + cause.getMessage() + ")");
        this.causeClassname = cause.getClass().getName();
    }

    public String getCauseClassname() {
        return causeClassname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("Exception", super.toString());
        builder.append("Cause", causeClassname);
        return builder.toString();
    }

}