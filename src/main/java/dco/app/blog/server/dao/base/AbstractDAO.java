package dco.app.blog.server.dao.base;

import com.google.inject.persist.Transactional;
import dco.app.blog.server.model.User;
import dco.app.blog.server.model.base.Entity;
import dco.app.blog.server.util.Injectors;
import dco.app.blog.server.util.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Abstract DAO implementation.
 * </p>
 * <p>
 * Parent class of all DAO implementations.
 * </p>
 *
 * @param <E>
 *         Entity type.
 * @param <K>
 *         Entity id type (primary key).
 * @author Denis
 */
public abstract class AbstractDAO<E extends Entity<K>, K extends Serializable> extends EntityManagerProvider implements DAO<E, K> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDAO.class);

    /**
     * The entity class managed by DAO implementation.
     */
    protected final Class<E> entityClass;

    /**
     * Initializes a new AbstractDAO.<br/>
     * Populates the {@link #entityClass} attribute.
     */
    @SuppressWarnings("unchecked")
    protected AbstractDAO() {
        this.entityClass = (Class<E>) Injectors.findGenericSuperClass(getClass()).getActualTypeArguments()[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final CriteriaBuilder getCriteriaBuilder() {
        return super.builder();
    }

    // --------------------------------------------------------------------------------
    //
    // COUNT METHODS.
    //
    // --------------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public int countAll() {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Counting total number of '{}' items.", entityClass.getName());
        }

        final TypedQuery<Number> query = em().createQuery("SELECT COUNT(e) FROM " + entityClass.getName() + " e", Number.class);
        return query.getSingleResult().intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int count(final CriteriaQuery<Number> criteriaQuery) {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Counting total number of '{}' items for criteria '{}'.", entityClass.getName(), criteriaQuery);
        }

        final Number total = em().createQuery(criteriaQuery).getSingleResult();

        return total != null ? total.intValue() : 0;
    }

    // --------------------------------------------------------------------------------
    //
    // FIND METHODS.
    //
    // --------------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public E findById(final K primaryKey) {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Retrieving '{}' item with primary key '{}'.", entityClass.getName(), primaryKey);
        }

        return em().find(entityClass, primaryKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> find(final CriteriaQuery<E> criteriaQuery) {
        return find(criteriaQuery, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> find(final CriteriaQuery<E> criteriaQuery, final Pagination pagination) {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Retrieving all '{}' items corresponding to criteriaQuery '{}' and pagination '{}'.", entityClass.getName(),
                    criteriaQuery, pagination);
        }

        final TypedQuery<E> query;

        if (criteriaQuery != null) {
            query = em().createQuery(criteriaQuery);

        } else {
            query = em().createQuery("SELECT e FROM " + entityClass.getName() + " e", entityClass);
        }

        if (pagination != null) {
            query.setFirstResult(pagination.getStartOffset());
            query.setMaxResults(pagination.getResultsNumber());
        }

        final List<E> items = query.getResultList();

        LOGGER.trace("Returning items list: {}", items);

        return items;
    }

    // --------------------------------------------------------------------------------
    //
    // PERSIST METHODS.
    //
    // --------------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public E persist(final E entity, final User user) {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Persisting entity '{}' processed by user '{}'.", entity, logUser(user));
        }

        if (entity == null) {
            return null;
        }

        final E merged = em().merge(setEntityProperties(entity, user));
        entity.setId(merged.getId());
        return merged;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public int update(final CriteriaUpdate<E> criteriaUpdate) {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Updating entities corresponding to criteria '{}'.", criteriaUpdate);
        }

        return em().createQuery(criteriaUpdate).executeUpdate();
    }

    // --------------------------------------------------------------------------------
    //
    // REMOVE METHODS.
    //
    // --------------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void remove(final K primaryKey) {

        if (primaryKey == null) {
            return;
        }

        final E entity = findById(primaryKey); // Avoids 'detached' entity issue.

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Removing primary key '{}' corresponding entity '{}'.", primaryKey, entity);
        }

        em().remove(entity); // Physical removal.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public int remove(final CriteriaDelete<E> criteriaDelete) {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Removing entities corresponding to criteria '{}'.", criteriaDelete);
        }

        return em().createQuery(criteriaDelete).executeUpdate();
    }

    // ------------------------------------------------------------------------------------------
    //
    // UTILITY METHODS.
    //
    // ------------------------------------------------------------------------------------------

    /**
     * Returns the given {@code user} corresponding <em>loggable</em> name.
     *
     * @param user
     *         The user instancce, may be {@code null}.
     * @return The given {@code user} corresponding <em>loggable</em> name.
     */
    protected static String logUser(final User user) {
        return user != null ? user.getFirstName() + ' ' + user.getName() : "anonymous";
    }

    /**
     * Sets {@code creation} or {@code update} properties on the given {@code entity}.
     *
     * @param entity
     *         The entity to update.
     * @param user
     *         The user processing the action, may be {@code null}.
     * @return The udpated {@code entity}.
     */
    protected static <E extends Entity<?>> E setEntityProperties(final E entity, final User user) {

        if (entity == null) {
            return null;
        }

        if (entity.getId() == null) {
            // Creation.
            entity.setCreationDate(new Date());
            entity.setCreationUser(logUser(user));

        } else {
            // Update.
            entity.setUpdateDate(new Date());
            entity.setUpdateUser(logUser(user));
        }

        return entity;
    }

    /**
     * Executes the given JPQL or native SQL {@code updateQuery}.<br/>
     * Checks the transaction before execution (see {@link #checkTransaction(EntityManager)}).
     *
     * @param updateQuery
     *         The update JPQL or native SQL query.
     * @return the number of elements updated/deleted.
     */
    @Transactional
    protected final int update(final Query updateQuery) {

        if (updateQuery == null) {
            throw new IllegalArgumentException("Update query is required.");
        }

        checkTransaction(em());

        return updateQuery.executeUpdate();
    }

    /**
     * Checks if active transaction is running. If so, given {@code em} is flushed in order to synchronize persistence
     * context.
     *
     * @param em
     *         The entity manager.
     */
    private static void checkTransaction(final EntityManager em) {

        if (em == null) {
            return;
        }

        if (em.getTransaction().isActive()) {
            // Active transaction running, flushing em to synchronize context.
            em.flush();
        }
    }

}