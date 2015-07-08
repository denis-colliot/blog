package dco.app.blog.server.handler;

import dco.app.blog.server.dispatch.impl.UserDispatch;
import dco.app.blog.server.handler.base.AbstractCommandHandler;
import dco.app.blog.server.model.User;
import dco.app.blog.shared.command.SecureNavigationCommand;
import dco.app.blog.shared.command.result.Authentication;
import dco.app.blog.shared.command.result.SecureNavigationResult;
import dco.app.blog.shared.dispatch.CommandException;

/**
 * Created on 08/07/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public class SecureNavigationHandler extends AbstractCommandHandler<SecureNavigationCommand, SecureNavigationResult> {

    @Override
    protected SecureNavigationResult execute(final SecureNavigationCommand command, final UserDispatch.UserExecutionContext context) throws CommandException {

        final User user = context.getUser();

        return new SecureNavigationResult(
                new Authentication(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getFirstName(),
                        context.getLanguage()
                ), true);
    }

}
