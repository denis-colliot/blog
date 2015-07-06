package dco.app.blog.client.navigation;

import dco.app.blog.shared.util.ClientUtils;

/**
 * Parameters for {@link PageRequest}.
 *
 * @author Denis
 */
public enum RequestParameter {

    ID(true),
    REQUEST,
    DTO;

    // If the parameter is part of the tab uniqueness logic.
    private final boolean unique;

    private RequestParameter() {
        this(false);
    }

    private RequestParameter(boolean unique) {
        this.unique = unique;
    }

    public boolean isUnique() {
        return unique;
    }

    public String getRequestName() {
        return getRequestName(this);
    }

    public static String getRequestName(RequestParameter requestParameter) {
        return ClientUtils.toLowerCase(requestParameter.name());
    }

    public static RequestParameter fromRequestName(String requestName) {
        if (ClientUtils.isNotBlank(requestName)) {
            for (final RequestParameter e : values()) {
                if (e.getRequestName().equals(requestName)) {
                    return e;
                }
            }
        }

        return null;
    }

}