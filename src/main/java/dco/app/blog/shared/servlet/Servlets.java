package dco.app.blog.shared.servlet;

import com.google.gwt.http.client.Response;
import dco.app.blog.shared.util.ClientUtils;
import dco.app.blog.shared.util.URLs;

/**
 * <p>
 * Additional servlet shared parameters.
 * </p>
 * <p>
 * <b>How to add a new Servlet Method to an <u>existing</u> Servlet?</b>
 * <ol>
 * <li>Declare the new method in {@link ServletMethod} with its unique java name.</li>
 * <li>Implement the new method in corresponding server {@code HttpServlet}.</li>
 * </ol>
 * </p>
 *
 * @author Denis
 * @see com.google.gwt.http.client.Response
 */
public final class Servlets {

    /**
     * <p>
     * Additional servlet(s) enumeration.
     * </p>
     * <p>
     * If a new servlet is served by the application, it must be added here in order to be accessed by client-side.
     * </p>
     *
     * @author Denis
     */
    public static enum Servlet {

        /**
         * Upload/Download servlet.
         */
        FILE,

        // Add other servlets here.
        ;

        /**
         * Gets the servlet path name.
         *
         * @return the servlet path name.
         */
        public String getPathName() {
            return name().toLowerCase();
        }

        /**
         * Gets the full servlet URL.<br>
         * This method must be executed after application is initialized (use of {@link com.google.gwt.core.client.GWT}
         * class).
         *
         * @return The full servlet URL (with host and context).
         */
        public String getUrl() {
            return URLs.buildApplicationURL(getPathName());
        }

        /**
         * Returns the {@code Servlet} value corresponding to the given {@code pathName}.
         *
         * @param pathName
         *         The request URI (composed with {@code /module_name/path_name}).
         * @return the {@code Servlet} value corresponding to the given {@code pathName}, or {@code null}.
         */
        public static Servlet fromPathName(final String pathName) {

            if (pathName == null) {
                return null;
            }

            for (final Servlet s : Servlet.values()) {
                if (ClientUtils.equalsIgnoreCase(pathName, s.getPathName())) {
                    return s;
                }
            }

            return null;
        }
    }

    /**
     * <p>
     * Additional servlets methods enumeration.
     * </p>
     * <p>
     * If a new servlet is served by the application, its methods must be added here in order to be accessed by
     * client-side.<br/>
     * Methods names must be unique.
     * </p>
     *
     * @author Denis
     */
    public static enum ServletMethod {

        // --
        // File Servlet.
        // --

        /**
         * <p>
         * Uploads a file.
         * </p>
         * <p>
         * Expected request parameter(s):
         * <ul>
         * <li>{@link dco.app.blog.client.navigation.RequestParameter#ID} : File name.</li>
         * </ul>
         * </p>
         */
        UPLOAD("upload"),

        /**
         * <p>
         * Downloads a file version.
         * </p>
         * <p>
         * Expected request parameter(s):
         * <ul>
         * <li>{@link dco.app.blog.client.navigation.RequestParameter#ID} : Id of the file <b>version</b> to
         * download.</li>
         * </ul>
         * </p>
         */
        DOWNLOAD("download"),

        // Add other servlets methods here.

        ;

        /**
         * The servlet {@code java} method name.
         */
        private final String name;

        /**
         * Whether the servlet method UI destination is a new {@code popup windows}.
         */
        private final boolean popup;

        private ServletMethod(final String name) {
            this(name, false);
        }

        private ServletMethod(final String name, final boolean popup) {
            assert ClientUtils.isNotBlank(name) : "The servlet method name is required.";
            this.name = name;
            this.popup = popup;
        }

        /**
         * Gets the servlet method name.
         *
         * @return the servlet method name.
         */
        public String getName() {
            return name;
        }

        /**
         * Returns if the servlet method UI destination is a new {@code popup windows}.
         *
         * @return {@code true} if the servlet method UI destination is a new {@code popup windows}, {@code false} if
         * the UI
         * destination is the main screen.
         */
        public boolean isPopup() {
            return popup;
        }

        /**
         * Returns the {@code ServletMethod} value corresponding to the given {@code methodName}.
         *
         * @param methodName
         *         The method name.
         * @return the {@code ServletMethod} value corresponding to the given {@code methodName}, or {@code null}.
         */
        public static ServletMethod fromMethodName(final String methodName) {

            if (methodName == null) {
                return null;
            }

            for (final ServletMethod servletMethod : ServletMethod.values()) {
                if (ClientUtils.equals(methodName, servletMethod.getName())) {
                    return servletMethod;
                }
            }

            return null;
        }
    }

    /**
     * Builds the given {@code errorCode} corresponding error message.
     *
     * @param errorCode
     *         The HTTP error code.
     * @return The given {@code errorCode} corresponding error message.
     */
    public static String buildErrorResponse(final int errorCode) {
        return String.valueOf(ERROR_RESPONSE_PREFIX) + errorCode;
    }

    /**
     * Check if the given message indicates a server error.
     *
     * @param message
     *         Message returned by a servlet.
     * @return <code>true</code> if an error happened, <code>false</code> otherwise.
     */
    public static boolean isErrorResponse(String message) {
        return message != null && !message.isEmpty() && message.charAt(0) == ERROR_RESPONSE_PREFIX;
    }

    /**
     * Parse the error message and return the embedded error code as an integer. If the message does not denote an
     * error,
     * 200 is returned.
     *
     * @param message
     *         Message returned by a servlet.
     * @return The error code if available, 200 otherwise.
     */
    public static int getErrorCode(String message) {
        if (!isErrorResponse(message)) {
            return Response.SC_OK;
        } else {
            return Integer.parseInt(message.substring(1));
        }
    }

    /**
     * Prefix of every error message.
     */
    private static final char ERROR_RESPONSE_PREFIX = '#';

    /**
     * Servlet parameter key referencing authentication token used to secure servlet calls.
     */
    public static final String AUTHENTICATION_TOKEN = "_s_at";

    /**
     * Servlet parameter key referencing method to execute.
     *
     * @see ServletMethod
     */
    public static final String SERVLET_METHOD = "_s_me";

    /**
     * Servlet parameter key referencing origin page token.
     */
    public static final String ORIGIN_PAGE_TOKEN = "_s_op";

    /**
     * Servlet parameter key referencing random value.<br/>
     * Avoids cache related issues.
     */
    static final String RANDOM = "_s_ra";

    /**
     * Servlet parameter key referencing ajax request flag.<br/>
     * Allows server-side servlet to detect ajax call from regular access.
     */
    public static final String AJAX = "_s_aj";

    /**
     * Utility class <em>private</em> constructor.
     */
    private Servlets() {
        // Provides only static methods.
    }

}