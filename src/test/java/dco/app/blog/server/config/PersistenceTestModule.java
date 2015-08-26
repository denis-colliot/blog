package dco.app.blog.server.config;

/**
 * Created on 26/08/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public class PersistenceTestModule extends PersistenceModule {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPersistencePropertiesFile() {
        return "persistence-test.properties";
    }

}
