package dco.app.blog.server.model.base;

import java.io.Serializable;
import java.util.Date;

/**
 * Domain entities interface.
 *
 * @param <K> Entity primary key type.
 * @author Denis
 */
public interface Entity<K extends Serializable> extends Serializable {

    /**
     * Returns the entity id value.
     *
     * @return the entity id value, or {@code null}.
     */
    K getId();

    /**
     * Sets the entity id value.
     *
     * @param id The new entity id value.
     */
    void setId(final K id);

    /**
     * Returns the creation date.
     *
     * @return The creation date, never {@code null}.
     */
    Date getCreationDate();

    /**
     * Sets the creation date.
     *
     * @param creationDate The creation date, should not be {@code null}.
     */
    void setCreationDate(final Date creationDate);

    /**
     * Returns the creation user name.
     *
     * @return The creation user name, never {@code null}.
     */
    String getCreationUser();

    /**
     * Sets the creation user name.
     *
     * @param creationUser The creation user name, should not be {@code null}.
     */
    void setCreationUser(final String creationUser);

    /**
     * Returns the last update date.
     *
     * @return The last update date, may be {@code null}.
     */
    Date getUpdateDate();

    /**
     * Sets the update date.
     *
     * @param updateDate The update date, should not be {@code null}.
     */
    void setUpdateDate(final Date updateDate);

    /**
     * Returns the last update user name.
     *
     * @return The last update user name, may be {@code null}.
     */
    String getUpdateUser();

    /**
     * Sets the update user name.
     *
     * @param updateUser The update user name, should not be {@code null}.
     */
    void setUpdateUser(final String updateUser);

}