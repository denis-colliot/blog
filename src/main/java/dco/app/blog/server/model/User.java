package dco.app.blog.server.model;

import dco.app.blog.server.model.base.AbstractEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Denis on 24/04/15.
 */
@Entity
@Table(name = "t_user_us")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "us_id", nullable = false))
})
public class User extends AbstractEntity<Long> {

    @Column(name = "us_name", nullable = false)
    private String name;

    @Column(name = "us_first_name", nullable = false)
    private String firstName;

    @Column(name = "us_login", nullable = false)
    private String login;

    @Column(name = "us_password", nullable = false)
    private String password;

    @Column(name = "us_email", nullable = true)
    private String email;

    @Column(name = "us_active", nullable = false)
    private boolean active;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendToString(final ToStringBuilder builder) {
        builder.append("name", name);
        builder.append("firstName", firstName);
        builder.append("login", login);
        builder.append("password", "[PROTECTED]");
        builder.append("email", email);
        builder.append("active", active);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
