package dco.app.blog.server.security;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dco.app.blog.server.dao.PostDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Denis on 25/04/15.
 */
@Singleton
public class AuthenticationFilter implements Filter {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Inject
    private PostDAO postDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nothing to do here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        // Nothing to do here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain)
            throws IOException, ServletException {

        LOGGER.trace("(TODO) Authentication security.");

        // TODO Authentication security implementation.

        LOGGER.trace("[_TEST ONLY: TO REMOVE_] Posts: {}", postDAO.find(null));

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
