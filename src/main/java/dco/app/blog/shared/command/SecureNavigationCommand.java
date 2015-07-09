package dco.app.blog.shared.command;

import dco.app.blog.client.navigation.Page;
import dco.app.blog.shared.command.base.AbstractCommand;
import dco.app.blog.shared.command.result.SecureNavigationResult;

/**
 * Secure navigation command.
 *
 * @author Denis
 */
public class SecureNavigationCommand extends AbstractCommand<SecureNavigationResult> {

    private Page page;

    public SecureNavigationCommand() {
        // Serialization.
    }

    public SecureNavigationCommand(final Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

}
