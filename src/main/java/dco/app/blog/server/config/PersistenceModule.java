package dco.app.blog.server.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.persist.jpa.JpaPersistModule;
import dco.app.blog.server.dao.AuthenticationDAO;
import dco.app.blog.server.dao.PostDAO;
import dco.app.blog.server.dao.impl.AuthenticationDAOImpl;
import dco.app.blog.server.dao.impl.PostDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by Denis on 24/04/15.
 *
 * @author Denis
 */
public class PersistenceModule extends AbstractModule {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceModule.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {

        LOGGER.info("Initializing persistence module.");

        // Installs the JPA module.
        install(new JpaPersistModule("blog-datasource")
                .properties(PersistenceProperties.init(getPersistencePropertiesFile())));

        // JSR-303 : bean validation.
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        bind(Validator.class).toInstance(validator);

        bind(AuthenticationDAO.class).to(AuthenticationDAOImpl.class).in(Singleton.class);
        bind(PostDAO.class).to(PostDAOImpl.class).in(Singleton.class);
    }

    /**
     * Returns the local persistence properties file name.
     *
     * @return The local persistence properties file name.
     */
    protected String getPersistencePropertiesFile() {
        return "persistence.properties";
    }

}
