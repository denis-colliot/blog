package dco.app.blog.shared.command.base;

import dco.app.blog.shared.command.result.base.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p>
 * Abstract dispatch command.
 * </p>
 * <p>
 * All command implementations should extend this abstract layer and declare an empty constructor.
 * </p>
 *
 * @param <R>
 *         The command result type.
 * @author Denis
 */
public abstract class AbstractCommand<R extends Result> implements Command<R> {

    /**
     * Empty constructor necessary for RPC serialization.
     */
    protected AbstractCommand() {
        // Serialization.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        appendToString(builder);
        return builder.toString();
    }

    /**
     * <p>
     * Allows child commands to append other properties to the given {@code builder}.
     * </p>
     * <p>
     * Use given builder this way:
     * <p/>
     * <pre>
     * builder.append(&quot;Property name&quot;, property);
     * </pre>
     * <p/>
     * </p>
     *
     * @param builder
     *         The {@code toString} builder (never {@code null}).
     */
    protected void appendToString(final ToStringBuilder builder) {
        // Child implementation should override this method.
    }

}