package dco.app.blog.shared.dispatch;

import dco.app.blog.shared.command.base.Command;
import dco.app.blog.shared.command.result.base.Result;

/**
 * Exception thrown if no handler can be found for a command.
 *
 * @author Denis
 */
public class UnsupportedCommandException extends CommandException {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -7759072862530169401L;

    protected UnsupportedCommandException() {
        // Serialization.
    }

    @SuppressWarnings({"unchecked"
    })
    public UnsupportedCommandException(final Command<? extends Result> command) {
        this((Class<? extends Command<? extends Result>>) command.getClass());
    }

    public UnsupportedCommandException(final Class<? extends Command<? extends Result>> commandClass) {
        super("No handler is registered for " + commandClass.getName());
    }

}