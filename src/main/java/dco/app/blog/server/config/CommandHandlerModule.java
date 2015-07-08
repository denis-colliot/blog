package dco.app.blog.server.config;

import dco.app.blog.server.config.dispatch.AbstractCommandHandlerModule;
import dco.app.blog.server.handler.SecureNavigationHandler;
import dco.app.blog.shared.command.SecureNavigationCommand;

/**
 * <p>
 * Command-Handler module. Installs automatically dispatch module.
 * </p>
 * <p>
 * Simply bind command classes to their corresponding handler class.
 * </p>
 *
 * @author Denis Colliot (dcolliot@ideia.fr)
 * @author Maxime Lombard (mlombard@ideia.fr)
 */
public class CommandHandlerModule extends AbstractCommandHandlerModule {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configureHandlers() {
        // Thank you for maintaining alphabetical order.
        bindHandler(SecureNavigationCommand.class, SecureNavigationHandler.class);
    }

}