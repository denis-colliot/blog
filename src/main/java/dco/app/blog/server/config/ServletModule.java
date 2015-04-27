package dco.app.blog.server.config;

import com.google.inject.persist.PersistFilter;
import dco.app.blog.server.security.AuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Module to serves the servlets.
 *
 * @author Denis
 */
public class ServletModule extends com.google.inject.servlet.ServletModule {

    /**
     * Log.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ServletModule.class);

    /**
     * Servlet remote service endpoint.
     */
    public static final String ENDPOINT = "/blog-api/";

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configureServlets() {

        LOG.info("Installing servlets module.");

        // Filters.
        filter("/*").through(PersistFilter.class);
        filter("/*").through(AuthenticationFilter.class);
//		security("/*").through(CacheFilter.class);
//
//		// Servlets.
//		serve(ENDPOINT + SecureDispatchService.REMOTE_SERVICE_RELATIVE_PATH).with(SecureDispatchServlet.class);
//		serve("/").with(SigmahHostController.class);
//		serve("/healthcheck").with(HealthCheckServlet.class);
//		serve(ENDPOINT + Servlet.FILE.getPathName()).with(FileServlet.class);
//		serve(ENDPOINT + Servlet.MANIFEST.getPathName()).with(ManifestServlet.class);
//		serve(ENDPOINT + Servlet.EXPORT.getPathName()).with(ExportServlet.class);
//		serve(ENDPOINT + Servlet.IMPORT.getPathName()).with(ImportServlet.class);
    }
}