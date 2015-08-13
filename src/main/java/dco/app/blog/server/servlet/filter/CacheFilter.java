package dco.app.blog.server.servlet.filter;

import com.google.inject.Singleton;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * <p>
 * Instructs browsers to cache application ressources "until the sun explodes" (or actually a year). Exceptions are
 * made for files containing the token ".nocache."
 * </p>
 * <p>
 * See http://www.infoq.com/articles/gwt-high-ajax<br/>
 * See http://www.gwtproject.org/doc/latest/DevGuideCompilingAndDebugging.html#perfect_caching
 * </p>
 * <p>
 * Apache configuration example:
 * <p/>
 * <pre>
 * &lt;Files *.nocache.*&gt;
 *   ExpiresActive on
 *   ExpiresDefault "now"
 *   Header merge Cache-Control "public, max-age=0, must-revalidate"
 * &lt;/Files&gt;
 *
 * &lt;Files *.cache.*&gt;
 *   ExpiresActive on
 *   ExpiresDefault "now plus 1 year"
 *   Header set dumbHeader "dumb header flag to control filter mechanism."
 * &lt;/Files&gt;
 * </pre>
 * <p/>
 * </p>
 *
 * @author Denis
 */
@Singleton
public class CacheFilter implements Filter {

    /**
     * Resources containing one of these value(s) will <b>never</b> be cached.
     *
     * @see #containsAny(String, String...)
     */
    private static final String[] NO_CACHE_FILTERS = {".nocache."};

    /**
     * Resources containing one of these value(s) will be cached.
     *
     * @see #containsAny(String, String...)
     */
    private static final String[] CACHE_FILTERS = {".cache."};

    /**
     * Year period in milliseconds.
     */
    private static final long YEAR = 365 * 24 * 60 * 60 * 1000L;

    /**
     * Application custom header.<br/>
     * Used to verify that responses headers are correctly set.
     */
    private static final String CUSTOM_HEADER_NAME = "appHeader";

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain) throws IOException, ServletException {

        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            // Just in case.
            filterChain.doFilter(request, response);
            return;
        }

        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        final String requestURI = httpRequest.getRequestURI();

        if (containsAny(requestURI, CACHE_FILTERS)) {
            // Cached resource.
            httpResponse.setDateHeader("Expires", new Date().getTime() + YEAR);

            // Control flag.
            httpResponse.setHeader(CUSTOM_HEADER_NAME, "Resource cached by application filter");

        } else if (containsAny(requestURI, NO_CACHE_FILTERS)) {
            // Never cached resource.
            httpResponse.setDateHeader("Expires", 0);
            httpResponse.setHeader("Cache-Control", "no-cache, public, max-age=0, must-revalidate");

            // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
            httpResponse.addHeader("Cache-Control", "post-check=0, pre-check=0");

            // Set standard HTTP/1.0 no-cache header.
            httpResponse.setHeader("Pragma", "no-cache");

            // Control flag.
            httpResponse.setHeader(CUSTOM_HEADER_NAME, "Resource NOT cached by application filter");
        }

        // Define content-type for JSON files.
        if (requestURI.contains(".json")) {
            httpResponse.setContentType("application/json");
        }

        filterChain.doFilter(request, response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        // Nothing to initialize.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        // Nothing to destroy.
    }

    /**
     * Returns if the given {@code seq} contains any of the given {@code searchSeqs}.<br/>
     * The search is case insensitive.
     * <p/>
     * <pre>
     * containsAny(null, null) -> false
     * containsAny(*, null) -> false
     * containsAny(null, *) -> false
     * containsAny("abc", "a") -> true
     * containsAny("abc", "abcd", "efg") -> false
     * containsAny("abc", "aB") -> true
     * containsAny("abc", "aBk") -> false
     * containsAny("abc", "aBk", "abC") -> true
     * </pre>
     *
     * @param seq
     *         The sequence.
     * @param searchSeqs
     *         The search sequence(s).
     * @return {@code true} if the given {@code seq} contains any of the given {@code searchSeqs}, {@code false}
     * otherwise.
     */
    private static boolean containsAny(final String seq, final String... searchSeqs) {

        if (seq == null || searchSeqs == null) {
            return false;
        }

        for (final String searchSeq : searchSeqs) {
            if (StringUtils.containsIgnoreCase(seq, searchSeq)) {
                return true;
            }
        }

        return false;
    }

}