package dco.app.blog.shared.command.result;

import dco.app.blog.client.util.ToStringBuilder;
import dco.app.blog.shared.command.result.base.Result;

/**
 * <p>
 * Encapsulates authenticated user identity. If no user is authenticated, this object encapsulates <em>anonymous</em>
 * user identity, see {@link org.sigmah.server.servlet.base.ServletExecutionContext#ANONYMOUS_USER ANONYMOUS_USER}.
 * </p>
 * <p>
 * This object is managed by {@link dco.app.blog.client.security.AuthenticationProvider}.
 * </p>
 *
 * @author Alex Bertram
 * @author Denis Colliot (dcolliot@ideia.fr)
 * @see dco.app.blog.client.security.AuthenticationProvider
 * @see ServletExecutionContext#ANONYMOUS_USER
 */
public class Authentication implements Result {

    /**
     * The authentication token required for calls to the command dispatch service.
     */
    private String authenticationToken;

    /**
     * The currently authenticated user's unique id.
     */
    private Integer userId;

    /**
     * The currently authenticated user's email.
     */
    private String userEmail;

    /**
     * The currently authenticated user's last name.
     */
    private String userName;

    /**
     * The currently authenticated user's first name.
     */
    private String userFirstName;

    /**
     * Necessary constructor for serialization.
     */
    public Authentication() {
        // Serialization.
    }

    /**
     * Initializes a new {@code Authentication} instance.
     *
     * @param userId
     *         The user's id (from the server's database).
     * @param userEmail
     *         The user's email.
     * @param userName
     *         The user's last name.
     * @param userFirstName
     *         The user's first name.
     */
    public Authentication(Integer userId, String userEmail, String userName, String userFirstName) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userFirstName = userFirstName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        final ToStringBuilder builder = new ToStringBuilder(this);

        builder.append("userId", userId);
        builder.append("userEmail", userEmail);
        builder.append("userName", userName);
        builder.append("userFirstName", userFirstName);

        return builder.toString();
    }

    // ------------------------------------------------------------
    //
    // GETTERS & SETTERS.
    //
    // ------------------------------------------------------------

    /**
     * Returns the server authentication token.
     *
     * @return The server authentication token.
     */
    public String getAuthenticationToken() {
        return authenticationToken;
    }

    /**
     * Sets the authentication token.
     * </p>
     * <p>
     * <em>Should <b>only</b> be called by {@link org.sigmah.server.handler.LoginCommandHandler} or {@link
     * dco.app.blog.client.security.AuthenticationProvider}.</em>
     * </p>
     *
     * @param authenticationToken
     *         The authentication token.
     */
    // Only required setter.
    public void setAuthenticationToken(final String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    /**
     * Returns the authenticated user id or {@code null} if anonymous.
     *
     * @return The authenticated user id or {@code null} if anonymous.
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Returns the authenticated user email or {@code null} if anonymous.
     *
     * @return The authenticated user email or {@code null} if anonymous.
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Returns the authenticated user last name or {@code null} if anonymous.
     *
     * @return The authenticated user last name or {@code null} if anonymous.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the authenticated user first name or {@code null} if anonymous.
     *
     * @return The authenticated user first name or {@code null} if anonymous.
     */
    public String getUserFirstName() {
        return userFirstName;
    }

}