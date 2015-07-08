package dco.app.blog.server.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Server entry-point.
 */
public class ServletContextListener extends GuiceServletContextListener {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletContextListener.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected Injector getInjector() {

        LOGGER.info("Creating Guice injector.");

        return Guice.createInjector(
                // Persistence module.
                new PersistenceModule(),
                // Command-Handler module.
                new CommandHandlerModule(),
                // Servlet module.
                new ServletModule());
    }

}