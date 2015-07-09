package dco.app.blog.server.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import dco.app.blog.server.security.SecureSessionValidator;
import dco.app.blog.shared.security.impl.AuthenticationSecureSessionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Security module.
 *
 * @author Denis
 */
public class SecurityModule extends AbstractModule {

    /**
     * Log.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SecurityModule.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {

        if (LOG.isInfoEnabled()) {
            LOG.info("Installing security module.");
        }

        bind(SecureSessionValidator.class).to(AuthenticationSecureSessionValidator.class).in(Singleton.class);
        //bind(Authenticator.class).to(DatabaseAuthenticator.class).in(Singleton.class);
    }

}