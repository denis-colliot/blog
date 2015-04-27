package dco.app.blog.server.dao.base;

import dco.app.blog.server.model.User;
import dco.app.blog.server.model.base.Entity;
import dco.app.blog.server.util.Pagination;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * DAO interface.
 * </p>
 * <p>
 * Should be implemented by all DAO implementations.
 * </p>
 *
 * @param <E> Entity type.
 * @param <K> Entity id type (primary key).
 * @author Denis
 */
public interface DAO<E extends Entity<K>, K extends Serializable> {

    /**
     * Returns the {@link CriteriaBuilder} instance.
     *
     * @return The {@link CriteriaBuilder} instance.
     */
    CriteriaBuilder getCriteriaBuilder();

    // --------------------------------------------------------------------------------
    //
    // COUNT METHODS.
    //
    // --------------------------------------------------------------------------------

    /**
     * Counts the total number of entities.
     *
     * @return The total number of entities.
     */
    int countAll();

    /**
     * Counts the total number of entities corresponding to the given {@code criteriaQuery}.
     *
     * @return The total number of entities corresponding to the given {@code criteriaQuery}.
     */
    int count(CriteriaQuery<Number> criteriaQuery);

    // --------------------------------------------------------------------------------
    //
    // FIND METHODS.
    //
    // --------------------------------------------------------------------------------

    /**
     * Finds the given {@code primaryKey} corresponding entity.
     *
     * @param primaryKey The entity primary key.
     * @return The given {@code primaryKey} corresponding entity.
     */
    E findById(K primaryKey);

    /**
     * <p>
     * Finds the entities corresponding to the given {@code criteriaQuery}.
     * </p>
     * <p>
     * If the given {@code criteriaQuery} is {@code null}, the method finds <em>all</em> entities.
     * </p>
     *
     * @param criteriaQuery The criteria query. Set to {@code null} to retrieve all entities.
     * @return The entities corresponding to the given {@code criteriaQuery}.
     */
    List<E> find(CriteriaQuery<E> criteriaQuery);

    /**
     * <p>
     * Finds the entities corresponding to the given {@code criteriaQuery} and given {@code pagination}.
     * </p>
     * <p>
     * If the given {@code criteriaQuery} is {@code null}, the method finds <em>all</em> entities.<br/>
     * If the given {@code pagination} is {@code null}, no pagination is set.
     * </p>
     *
     * @param criteriaQuery The criteria query. Set to {@code null} to retrieve all entities.
     * @param pagination    The {@link Pagination} data. Set to {@code null} to ignore.
     * @return The entities corresponding to the given {@code criteriaQuery}.
     */
    List<E> find(CriteriaQuery<E> criteriaQuery, Pagination pagination);

    // --------------------------------------------------------------------------------
    //
    // PERSIST METHODS.
    //
    // --------------------------------------------------------------------------------

    /**
     * Persists the given {@code entity} with the given {@code user}.
     *
     * @param entity The entity to persist. Does nothing if {@code null}.
     * @param user   The user executing the persist action, may be {@code null}.
     * @return The persisted (updated) entity.
     */
    E persist(E entity, User user);

    /**
     * Updates the entities corresponding to the given {@code criteriaUpdate}.
     *
     * @param criteriaUpdate The criteria update.
     * @return The number of updated entities.
     */
    int update(CriteriaUpdate<E> criteriaUpdate);

    // --------------------------------------------------------------------------------
    //
    // REMOVE METHODS.
    //
    // --------------------------------------------------------------------------------

    /**
     * Removes the given {@code primaryKey} corresponding entity.
     *
     * @param primaryKey The primary key referencing the entity to remove. Does nothing if {@code null}.
     */
    void remove(K primaryKey);

    /**
     * Removes the entities corresponding to the given {@code criteriaDelete}.
     *
     * @param criteriaDelete The criteria delete.
     * @return The number of deleted entities.
     */
    int remove(CriteriaDelete<E> criteriaDelete);

}