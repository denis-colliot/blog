package dco.app.blog.dao;

import dco.app.blog.config.GuiceJUnitRunner;
import dco.app.blog.server.config.PersistenceModule;
import dco.app.blog.server.dao.base.EntityManagerProvider;
import org.junit.runner.RunWith;

/**
 * Abstract DAO test class initializing {@code Injector}.
 *
 * @author Denis
 */
@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({
        PersistenceModule.class
})
public abstract class AbstractDAOTest extends EntityManagerProvider {

}