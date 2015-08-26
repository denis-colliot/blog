package dco.app.blog.server.dao;

import com.google.common.io.Files;
import dco.app.blog.server.config.GuiceJUnitRunner;
import dco.app.blog.server.config.PersistenceTestModule;
import dco.app.blog.server.dao.base.EntityManagerProvider;
import org.apache.commons.lang3.StringUtils;
import org.h2.tools.RunScript;
import org.hibernate.internal.SessionImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

/**
 * Abstract DAO test class initializing {@code Injector}.
 *
 * @author Denis
 */
@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({
        PersistenceTestModule.class
})
public abstract class AbstractDAOTest extends EntityManagerProvider {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDAOTest.class);

    @Before
    public final void createDatabase() {
        executeScript("db/create-test-db.sql");
    }

    @After
    public final void shutdownDatabase() {
        em().clear(); // Important.
        executeScript("db/shutdown-test-db.sql");
    }

    private void executeScript(final String scriptName) {

        final URL script = AbstractDAOTest.class.getClassLoader().getResource(scriptName);

        if (StringUtils.isBlank(script.getPath())) {
            throw new UnsupportedOperationException("SQL script name '" + scriptName + "' not found");
        }

        LOGGER.info("Executing SQL script '{}'.", script.getPath());

        try {

            RunScript.execute(getConnection(), Files.newReader(new File(script.getPath()), StandardCharsets.UTF_8));

        } catch (Exception e) {
            throw new RuntimeException("Error during SQL script '" + scriptName + "' execution", e);
        }
    }

    /**
     * Returns the current {@code java.sql.Connection} instance.
     *
     * @return The current {@code java.sql.Connection} instance.
     */
    private Connection getConnection() {
        return em().unwrap(SessionImpl.class).connection();
    }

}