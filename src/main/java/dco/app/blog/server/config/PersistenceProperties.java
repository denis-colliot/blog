package dco.app.blog.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Denis on 26/04/15.
 */
final class PersistenceProperties {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceProperties.class);

    /**
     * Persistence local properties file name.
     */
    private static final String FILE = "persistence.properties";

    /**
     * File corresponding {@link java.util.Properties}.
     */
    private static final Properties PROPERTIES = new Properties();

    /**
     * Initializes the persistence properties.
     *
     * @return The persistence properties.
     */
    public static Properties init() {

        LOGGER.info("Initializing persistence properties.");

        final Properties properties = new Properties();

        // Connection pool configuration.
        // https://community.jboss.org/wiki/HowToConfigureTheC3P0ConnectionPool
        // http://www.mchange.com/projects/c3p0/
        properties.setProperty("hibernate.connection.provider_class", "org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider");

        if (System.getenv().containsKey("CU_DATABASE_DNS_1")) {

            // --
            // Production environment (hardcoded).
            // --

            LOGGER.info("Production environment ; environment: {}", System.getenv());

            final String host = System.getenv().get("CU_DATABASE_DNS_1");
            final String username = System.getenv().get("CU_DATABASE_USER_1");
            final String password = System.getenv().get("CU_DATABASE_PASSWORD_1");
            final String dbName = System.getenv().get("CU_DATABASE_NAME");

            LOGGER.info("Host: '{}' ; Username: '{}' ; Password: '{}' ; DbName: '{}'", host, username, password, dbName);

            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            properties.setProperty("hibernate.connection.url", "jdbc:mysql://" + host + ":3306/" + dbName);
            properties.setProperty("hibernate.username", username);
            properties.setProperty("hibernate.password", password);

            properties.setProperty("hibernate.hbm2ddl.auto", "");
            properties.setProperty("hibernate.show_sql", "false");
            properties.setProperty("hibernate.format_sql", "false");

            properties.setProperty("hibernate.c3p0.min_size", "1");
            properties.setProperty("hibernate.c3p0.max_size", "15");
            properties.setProperty("hibernate.c3p0.max_statements", "100");
            properties.setProperty("hibernate.c3p0.timeout", "0");
            properties.setProperty("hibernate.c3p0.acquire_increment", "1");
            properties.setProperty("hibernate.c3p0.numHelperThreads", "6");

        } else {

            // --
            // Debug environment (read from 'persistence.properties' configuration file).
            // --

            LOGGER.info("Debug environment ; loading persistence properties file.");

            loadLocalConfiguration();

            // Mandatory properties.
            setMandatory(properties, "hibernate.dialect");
            setMandatory(properties, "hibernate.connection.driver_class");
            setMandatory(properties, "hibernate.connection.url");
            setMandatory(properties, "hibernate.username");
            setMandatory(properties, "hibernate.password");

            // Optional properties that can be overridden.
            setOptional(properties, "hibernate.archive.autodetection", "class");
            setOptional(properties, "hibernate.hbm2ddl.auto", "");
            setOptional(properties, "hibernate.show_sql", "true");
            setOptional(properties, "hibernate.format_sql", "true");
            setOptional(properties, "hibernate.c3p0.min_size", "1");
            setOptional(properties, "hibernate.c3p0.max_size", "15");
            setOptional(properties, "hibernate.c3p0.max_statements", "100");
            setOptional(properties, "hibernate.c3p0.timeout", "0");
            setOptional(properties, "hibernate.c3p0.acquire_increment", "1");
            setOptional(properties, "hibernate.c3p0.numHelperThreads", "6");
        }

        LOGGER.info("Persistence properties: {}", properties);

        return properties;
    }

    private static void loadLocalConfiguration() {
        try (final InputStream is = PersistenceModule.class.getClassLoader().getResourceAsStream(FILE)) {

            LOGGER.info("Loading properties from file '{}'.", FILE);

            PROPERTIES.load(is);

            LOGGER.trace("Loaded properties: {}", PROPERTIES);

        } catch (final Exception e) {
            LOGGER.error("Properties loading failure with file '" + FILE + "'.", e);
            throw new UnsupportedOperationException("Persistence configuration file '" + FILE + "' " +
                    "is missing or an error occured while reading it.");
        }
    }

    private static void setMandatory(Properties properties, String key) {
        set(properties, key, null, true);
    }

    private static void setOptional(Properties properties, String key, String defaultValue) {
        set(properties, key, defaultValue, false);
    }

    private static void set(Properties properties, String key, String defaultValue, boolean mandatory) {
        if (mandatory && !PROPERTIES.containsKey(key)) {
            throw new IllegalArgumentException("Missing required persistence property '" + key + "'.");
        }
        properties.setProperty(key, PROPERTIES.getProperty(key, defaultValue));
    }
}
