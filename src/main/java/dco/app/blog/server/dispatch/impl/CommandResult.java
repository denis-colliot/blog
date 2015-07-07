package dco.app.blog.server.dispatch.impl;

import dco.app.blog.shared.command.base.Command;
import dco.app.blog.shared.command.result.base.Result;

/**
 * This contains both the original {@link Command} and the {@link Result} of that command.
 *
 * @param <C>
 *         The command type.
 * @param <R>
 *         The result type.
 * @author Denis
 */
final class CommandResult<C extends Command<R>, R extends Result> {

    /**
     * The command.
     */
    private final C command;

    /**
     * The command result.
     */
    private final R result;

    public CommandResult(final C command, final R result) {
        this.command = command;
        this.result = result;
    }

    public C getCommand() {
        return command;
    }

    public R getResult() {
        return result;
    }

}