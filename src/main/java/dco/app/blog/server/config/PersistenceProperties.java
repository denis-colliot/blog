package dco.app.blog.server.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Map;
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
     * PAAS system environment properties prefix.
     */
    private static final String PAAS_ENV_PREFIX = "CU_";

    /**
     * File corresponding {@link java.util.Properties}.
     */
    private static final Properties PROPERTIES = new Properties();

    /**
     * <p>
     * Initializes the persistence properties.
     * </p>
     * <p>
     * Detects automatically local environment from production environment.
     * </p>
     *
     * @return The persistence properties.
     */
    public static Properties init() {

        LOGGER.info("Initializing persistence properties ; System environment: {}", System.getenv());

        final Properties properties = new Properties();

        // Connection pool configuration.
        // https://community.jboss.org/wiki/HowToConfigureTheC3P0ConnectionPool
        // http://www.mchange.com/projects/c3p0/
        properties.setProperty("hibernate.connection.provider_class", "org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider");

        if (isProductionEnvironment()) {

            // --
            // Production environment (hardcoded).
            // --

            LOGGER.info("Production environment ; using system environment properties.");

            loadProperties("cloudunit.properties");

            final String host = System.getenv().get(PROPERTIES.getProperty("env.database.host"));
            final String username = System.getenv().get(PROPERTIES.getProperty("env.database.user"));
            final String password = System.getenv().get(PROPERTIES.getProperty("env.database.password"));
            final String dbName = System.getenv().get(PROPERTIES.getProperty("env.database.name"));

            final String dialect = PROPERTIES.getProperty("hibernate.dialect");
            final String driver = PROPERTIES.getProperty("jdbc.driver");
            final String url = PROPERTIES.getProperty("jdbc.url");

            LOGGER.info("Host: '{}' ; Username: '{}' ; Password: '{}' ; DbName: '{}'", host, username, password, dbName);
            LOGGER.info("Dialect: '{}' ; Driver: '{}' ; Url: '{}'", dialect, driver, url);

            properties.setProperty("hibernate.dialect", dialect);
            properties.setProperty("hibernate.connection.driver_class", driver);
            properties.setProperty("hibernate.connection.url", url.replace("{host}", host).replace("{dbName}", dbName));
            properties.setProperty("hibernate.connection.username", username);
            properties.setProperty("hibernate.connection.password", password);

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

            loadProperties("persistence.properties");

            // Mandatory properties.
            setMandatory(properties, "hibernate.dialect");
            setMandatory(properties, "hibernate.connection.driver_class");
            setMandatory(properties, "hibernate.connection.url");
            setMandatory(properties, "hibernate.connection.username");
            setMandatory(properties, "hibernate.connection.password");

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

    /**
     * <p>Returns if the current environment is the production environment.</p>
     * <p>The method simply look for {@code CU_*} environment properties among {@code System.getenv()}.</p>
     */
    private static boolean isProductionEnvironment() {

        for (final Map.Entry<String, String> env : System.getenv().entrySet()) {
            if (StringUtils.startsWithIgnoreCase(env.getKey(), PAAS_ENV_PREFIX)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Loads the given {@code file} and populates {@link #PROPERTIES}.
     *
     * @param file
     *         The file to read.
     */
    private static void loadProperties(final String file) {
        try (final InputStream is = PersistenceModule.class.getClassLoader().getResourceAsStream(file)) {

            LOGGER.info("Loading properties from file '{}'.", file);

            PROPERTIES.load(is);

            LOGGER.trace("Loaded properties: {}", PROPERTIES);

        } catch (final Exception e) {
            LOGGER.error("Properties loading failure with file '" + file + "'.", e);
            throw new UnsupportedOperationException("Persistence configuration file '" + file + "' " +
                    "is missing or an error occurred while reading it.");
        }
    }

    /**
     * Sets the <b>mandatory</b> {@code key} property.
     *
     * @param properties
     *         The persistence properties that <b>should</b> contain the corresponding value.
     * @param key
     *         The property key.
     * @throws IllegalArgumentException
     *         If the given {@code properties} do not contain {@code key} value.
     */
    private static void setMandatory(Properties properties, String key) {
        set(properties, key, null, true);
    }

    /**
     * Sets the <b>optional</b> {@code key} property.<br>
     * If the {@code properties} do not contain {@code key} value, its value is set to {@code null}.
     *
     * @param properties
     *         The persistence properties that <b>may</b> contain the corresponding value.
     * @param key
     *         The property key.
     */
    private static void setOptional(Properties properties, String key, String defaultValue) {
        set(properties, key, defaultValue, false);
    }

    /**
     * Sets the {@code key} property.
     *
     * @param properties
     *         The persistence properties that may/should contain the corresponding value.
     * @param key
     *         The property key.
     * @param defaultValue
     *         The default value used if the optional {@code key} property is not found among {@code properties}.
     * @param mandatory
     *         Is it a mandatory property?
     * @throws IllegalArgumentException
     *         If the given {@code properties} do not contain the <b>mandatory</b> {@code key} value.
     */
    private static void set(Properties properties, String key, String defaultValue, boolean mandatory) {
        if (mandatory && !PROPERTIES.containsKey(key)) {
            throw new IllegalArgumentException("Missing required persistence property '" + key + "'.");
        }
        properties.setProperty(key, PROPERTIES.getProperty(key, defaultValue));
    }
}
