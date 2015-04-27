package dco.app.blog.server.model.base;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Abstract entity, parent class of all domain entities possessing a primary key.
 *
 * @param <K> Entity primary key type.
 * @author Denis
 */
@MappedSuperclass
public abstract class AbstractEntity<K extends Serializable> implements Entity<K> {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -7822034341683441998L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private K id;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "creation_user", nullable = true)
    private String creationUser;

    @Column(name = "update_date", nullable = true)
    private Date updateDate;

    @Column(name = "update_user", nullable = true)
    private String updateUser;

    /**
     * <p>
     * <b><em>Default {@code toString} method only displays centralized properties.</em><br/>
     * See {@link #appendToString(ToStringBuilder)} to append other properties to {@code toString} builder.</b>
     * </p>
     * {@inheritDoc}
     *
     * @see #appendToString(ToStringBuilder)
     */
    @Override
    public final String toString() {

        final ToStringBuilder builder = new ToStringBuilder(this);

        // Entity id.
        builder.append("id", getId());

        // Appends child entity specific properties.
        appendToString(builder);

        builder.append("creationDate", getCreationDate());
        builder.append("creationUser", getCreationUser());
        builder.append("updateDate", getUpdateDate());
        builder.append("updateUser", getUpdateUser());

        return builder.toString();
    }

    /**
     * <p>
     * Allows sub entities to append other properties to the given {@code builder}.
     * </p>
     * <p>
     * <em>Entity's {@code id}, {@code creation[date/user]} and {@code update[date/user]} properties have already been appended to the {@code builder}.</em>
     * </p>
     * <p>
     * Use given builder this way:
     * <p>
     * <pre>
     * builder.append(&quot;Property name&quot;, property);
     * </pre>
     * <p>
     * </p>
     *
     * @param builder The {@code toString} builder (never {@code null}).
     */
    protected abstract void appendToString(final ToStringBuilder builder);

    /**
     * <p>
     * <b><em>Default {@code hashCode} method only relies on {@code id} property.</em></b>
     * </p>
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    /**
     * <p>
     * <b><em>Default {@code equals} method only relies on {@code id} property.</em></b>
     * </p>
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        AbstractEntity<?> other = (AbstractEntity<?>) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public K getId() {
        return id;
    }

    @Override
    public void setId(K id) {
        this.id = id;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String getCreationUser() {
        return creationUser;
    }

    @Override
    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    @Override
    public Date getUpdateDate() {
        return updateDate;
    }

    @Override
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String getUpdateUser() {
        return updateUser;
    }

    @Override
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

}