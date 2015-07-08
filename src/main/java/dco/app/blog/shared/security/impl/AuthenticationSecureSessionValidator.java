package dco.app.blog.shared.security.impl;

import com.google.inject.Inject;
import dco.app.blog.client.navigation.Page;
import dco.app.blog.client.security.SecureDispatchAsync;
import dco.app.blog.server.dao.AuthenticationDAO;
import dco.app.blog.server.model.Authentication;
import dco.app.blog.server.model.User;
import dco.app.blog.server.security.SecureSessionValidator;
import dco.app.blog.shared.command.base.Command;
import dco.app.blog.shared.command.result.base.Result;
import dco.app.blog.shared.servlet.Servlets;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * The {@link SecureSessionValidator} implementation.
 * </p>
 * <p>
 * This service is used to validate dispatch servlet commands <em>and</em> additional servlet(s) processes.
 * </p>
 *
 * @author Denis
 * @see dco.app.blog.server.dispatch.SecureDispatchServlet
 * @see org.sigmah.server.servlet.base.AbstractServlet
 */
public class AuthenticationSecureSessionValidator implements SecureSessionValidator {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationSecureSessionValidator.class);

    /**
     * The injected {@code AuthenticationDAO}.
     */
    private final AuthenticationDAO authenticationDAO;

    /**
     * AuthenticationSecureSessionValidator initialization.
     *
     * @param authenticationDAO
     *         Injected DAO.
     */
    @Inject
    public AuthenticationSecureSessionValidator(final AuthenticationDAO authenticationDAO) {
        this.authenticationDAO = authenticationDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Access validate(final String authenticationToken, final Servlets.Servlet servlet, final Servlets.ServletMethod method, final String originPageToken) {
        return validate(authenticationToken, AccessRights.servletToken(servlet, method), originPageToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Access validate(final String authenticationToken, final SecureDispatchAsync.CommandExecution<? extends Command<?>, ? extends Result> commandExecution) {
        return validate(authenticationToken, AccessRights.commandToken(commandExecution.getCommand().getClass()), commandExecution.getCurrentPageToken());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUserGranted(final User user, final Page page) {
        return isUserGranted(user, AccessRights.pageToken(page), null);
    }

    /**
     * Validates the access to the given {@code resourceToken} resource for the {@code authenticationToken}.
     *
     * @param authenticationToken
     *         The authentication token.
     * @param resourceToken
     *         The resource token.
     * @param originPageToken
     *         The origin page token.
     * @return The validation access result.
     */
    private Access validate(final String authenticationToken, final String resourceToken, final String originPageToken) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Starting validation of authentication token '{}' for resource '{}'.", authenticationToken, resourceToken);
        }

        try {

            if (StringUtils.isBlank(authenticationToken) || "null".equalsIgnoreCase(authenticationToken)) {

                if (LOG.isTraceEnabled()) {
                    LOG.trace("No authentication token (anonymous user): '{}'.", authenticationToken);
                }

                final boolean commandAuthorizedForAnonymous = isUserGranted(null, resourceToken, originPageToken);

                if (commandAuthorizedForAnonymous) {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("ACCESS GRANTED for authentication token '{}'.", authenticationToken);
                    }
                    return new Access(AccessType.ACCESS_GRANTED, null);

                } else {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("ACCESS UNAUTHORIZED for authentication token '{}'.", authenticationToken);
                    }
                    return new Access(AccessType.UNAUTHORIZED_ACCESS, null);
                }
            }

            // Retrieves the authentication token corresponding user.
            final Authentication authentication = authenticationDAO.findById(authenticationToken);

            // Invalid token ?
            if (authentication == null) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("ACCESS UNAUTHORIZED - Invalid session, no Authentication found in database for token '{}'.", authenticationToken);
                }
                return new Access(AccessType.INVALID_SESSION, null);
            }

            if (authentication.getUser() == null) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("ACCESS UNAUTHORIZED - Invalid session, no User for Authentification token '{}'.", authenticationToken);
                }
                return new Access(AccessType.INVALID_SESSION, null);
            }

            final User user = authentication.getUser(); // Cannot be null at this point.

            final boolean processAuthorizedForUser = isUserGranted(user, resourceToken, originPageToken);

            if (processAuthorizedForUser) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("ACCESS GRANTED - User '{}' is granted to execute process.", user);
                }

                return new Access(AccessType.ACCESS_GRANTED, user);
            }

            if (LOG.isTraceEnabled()) {
                LOG.trace("ACCESS UNAUTHORIZED - User '{}' does not have required permission to execute process.", user);
            }

            return new Access(AccessType.UNAUTHORIZED_ACCESS, user);

        } catch (final Throwable e) {

            if (LOG.isErrorEnabled()) {
                LOG.error("Error while validating the authentication token '" + authenticationToken + "'.", e);
            }

            return new Access(AccessType.INVALID_SESSION, null);
        }
    }

    /**
     * Returns the grant access to the given {@code resourceToken} for the {@code user}.
     *
     * @param user
     *         The user.
     * @param resourceToken
     *         The resource token to secure.
     * @param originPageToken
     *         The origin page token, may be {@code null}.
     * @return {@code true} if the {@code user} is granted to access {@code resourceToken}, {@code false} otherwise.
     */
    private boolean isUserGranted(final User user, final String resourceToken, final String originPageToken) {

        if (user != null && BooleanUtils.isFalse(user.getActive())) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("User '{}' cannot access resource '{}' because it is no longer active.", user, resourceToken);
            }
            return false;
        }

        return AccessRights.isGranted(user, resourceToken, originPageToken);
    }

}