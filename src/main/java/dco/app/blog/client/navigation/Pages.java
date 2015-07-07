package dco.app.blog.client.navigation;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for {@link Page} class.
 *
 * @author Denis
 */
final class Pages {

    private Pages() {
        // Only provides static constants.
    }

    static final String KEY_SUFFIX = "-";

    /**
     * Stores the {@link Page} tokens with their corresponding instance.
     */
    static final Map<String, Page> PAGES = new HashMap<String, Page>();

}