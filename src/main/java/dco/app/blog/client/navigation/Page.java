package dco.app.blog.client.navigation;

import com.google.gwt.user.client.rpc.IsSerializable;
import dco.app.blog.shared.property.PropertyName;
import dco.app.blog.shared.util.ClientUtils;

/**
 * Page enumeration which values represent URL tokens.
 *
 * @author Denis
 */
public enum Page implements IsSerializable {

    HOME("home"),
    LOGIN("login");

    private final String parentKey;

    private final String token;

    private final boolean skipHistory;

    private String pageTitle;

    /**
     * Instantiates a new {@code Page} object.
     *
     * @param token
     *         The page token (must be unique).
     * @throws IllegalArgumentException
     *         If the page token is invalid or non-unique.
     */
    private Page(final String token) {
        this(null, token, false);
    }

    /**
     * Instantiates a new {@code Page} object with its title.
     *
     * @param parentKey
     *         The parent key.
     * @param token
     *         The page token (must be unique).
     * @throws IllegalArgumentException
     *         If the page token is invalid or non-unique.
     */
    private Page(final String parentKey, final String token) {
        this(parentKey, token, false);
    }

    /**
     * Instantiates a new {@code Page} object with its history configuration.
     *
     * @param token
     *         The page token (must be unique).
     * @param skipHistory
     *         Defines if the page must be considered in the history.
     * @throws IllegalArgumentException
     *         If the page token is invalid or non-unique.
     */
    private Page(final String token, final boolean skipHistory) {
        this(null, token, skipHistory);
    }

    /**
     * Instantiates a new {@code Page} object with its title and history configuration.
     *
     * @param parentKey
     *         The parent key.
     * @param token
     *         The page token (must be unique).
     * @param skipHistory
     *         Defines if the page must be considered in the history.
     * @throws IllegalArgumentException
     *         If the page token is invalid or non-unique.
     */
    private Page(final String parentKey, final String token, final boolean skipHistory) {
        this.parentKey = parentKey;
        this.token = (parentKey != null ? parentKey + Pages.KEY_SUFFIX : "") + token;
        this.skipHistory = skipHistory;

        if (ClientUtils.isBlank(getToken())) {
            throw new IllegalArgumentException("Invalid page token: '" + getToken() + "'.");
        }
        if (Pages.PAGES.containsKey(getToken())) {
            throw new IllegalArgumentException("Non-unique page token: '" + getToken() + "'.");
        }
        Pages.PAGES.put(getToken(), this);
    }

    /**
     * Indicates if the page needs to be considered in the history.
     *
     * @return {@code true} if the page needs to be considered in the history, {@code false} otherwise.
     */
    public boolean skipHistory() {
        return skipHistory;
    }

    /**
     * Returns the parent key.
     *
     * @return The parent key.
     */
    public String getParentKey() {
        return parentKey;
    }

    /**
     * Returns the page token.
     *
     * @return The page token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Returns a new request for this page.
     *
     * @return A new {@link PageRequest} instance for the current page.
     */
    public PageRequest request() {
        return new PageRequest(this);
    }

    /**
     * A convenience method for calling {@code request().addParameter(name, value)}.
     *
     * @param name
     *         The URL parameter name.
     * @param value
     *         The URL parameter value.
     * @return A {@code PageRequest} instance with the given parameter.
     * @see PageRequest#addParameter(RequestParameter, Object)
     */
    public PageRequest requestWith(final RequestParameter name, final Object value) {
        return new PageRequest(this).addParameter(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return token;
    }

    /**
     * Sets the page title.<br/>
     * If not {@code null}, this value will be returned by {@link #getTitle(Page)} method.
     *
     * @param pageTitle
     *         The new page title carried by this instance.
     */
    public void setTitle(final String pageTitle) {
        this.pageTitle = pageTitle;
    }

    /**
     * <p>
     * Returns the given {@code page} corresponding title.
     * </p>
     * <p>
     * The method follows this pattern:
     * <ol>
     * <li>The page is {@code null}: returns {@code null}.</li>
     * <li>The page carries a dynamic title value: returns the dynamic page title (see {@link #setTitle(String)}
     * method).</li>
     * <li>[<em>client-side only</em>] Returns a static i18n constant associated to the page.</li>
     * <li>Returns an error value containing the page token.</li>
     * </ol>
     * </p>
     *
     * @param page
     *         The {@link Page} instance.
     * @return The given {@code page} corresponding title, or {@code null}.
     */
    // Static title getter allows Page instance to be used on server-side.
    public static String getTitle(final Page page) {

        if (page == null) {
            return null;
        }

        if (page.pageTitle != null) {
            return page.pageTitle;
        }

        if (!ClientUtils.isClient()) {
            return PropertyName.error(page.token);
        }

        switch (page) {
            default:
                return PropertyName.error(page.token);
        }
    }

}