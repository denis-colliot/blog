package dco.app.blog.server.handler.base;

import dco.app.blog.server.dao.base.EntityManagerProvider;
import dco.app.blog.server.dispatch.CommandHandler;
import dco.app.blog.server.dispatch.ExecutionContext;
import dco.app.blog.server.dispatch.impl.UserDispatch;
import dco.app.blog.server.util.Injectors;
import dco.app.blog.shared.command.base.Command;
import dco.app.blog.shared.command.result.base.Result;
import dco.app.blog.shared.dispatch.CommandException;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * A super class for handlers which manages the specific execution context.
 *
 * @param <C>
 *         The command type.
 * @param <R>
 *         The result type.
 * @author Denis
 */
public abstract class AbstractCommandHandler<C extends Command<R>, R extends Result> extends EntityManagerProvider implements CommandHandler<C, R> {

    /**
     * Ensures that the execution context extends the {@link UserDispatch.UserExecutionContext} class.
     *
     * @param context
     *         The execution context.
     * @throws CommandException
     *         If the context doesn't extends the {@link UserDispatch.UserExecutionContext} class.
     */
    private static void ensureGuiceExecutionContext(final ExecutionContext context) throws CommandException {
        if (!(context instanceof UserDispatch.UserExecutionContext)) {
            throw new CommandException("The execution context doesn't extends '"
                    + UserDispatch.UserExecutionContext.class.getCanonicalName()
                    + "'. The handler cannot be executed.");
        }
    }

    /**
     * The command type.
     */
    private Class<C> clazz;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public final Class<C> getCommandType() {

        if (clazz == null) {
            final Class<?> clazz = Injectors.getClass(this);
            final Type type = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];

            if (type instanceof Class) {
                this.clazz = (Class<C>) type;
            } else if (type instanceof ParameterizedType) {
                // If the command handler is parametrized, retrieves the raw class.
                this.clazz = (Class<C>) ((ParameterizedType) type).getRawType();
            } else {
                throw new UnsupportedOperationException("Type is not supported: " + type);
            }
        }

        return clazz;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final R execute(final C command, final ExecutionContext context) throws CommandException {

        if (context != null) {

            ensureGuiceExecutionContext(context);

            final UserDispatch.UserExecutionContext uContext = (UserDispatch.UserExecutionContext) context;

            return execute(command, uContext);

        } else {

            return execute(command, null);

        }

    }

    /**
     * Executes the given {@code command} within given {@code context}.
     *
     * @param command
     *         The command
     * @param context
     *         The execution context.
     * @return The command execution result.
     * @throws CommandException
     *         If the command execution fails.
     */
    protected abstract R execute(final C command, final UserDispatch.UserExecutionContext context) throws CommandException;

    /**
     * {@inheritDoc}
     */
    @Override
    public final void rollback(final C command, R result, final ExecutionContext context) throws CommandException {

        if (context != null) {

            ensureGuiceExecutionContext(context);

            final UserDispatch.UserExecutionContext uContext = (UserDispatch.UserExecutionContext) context;
            rollback(command, result, uContext);

        } else {

            rollback(command, result, null);

        }

    }

    /**
     * Rollbacks the given {@code command} execution.<br/>
     * The default implementation does nothing.
     *
     * @param command
     *         The command.
     * @param result
     *         The command result.
     * @param context
     *         The context.
     * @throws CommandException
     *         If the rollback failed.
     */
    public void rollback(final C command, R result, final UserDispatch.UserExecutionContext context) throws CommandException {
        // Default implementation does nothing.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return new ToStringBuilder(this).toString();
    }

}