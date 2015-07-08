package dco.app.blog.server.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

/**
 * {@link User} meta-model.
 *
 * @author Denis
 */
@StaticMetamodel(User.class)
public final class User_ {

    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> login;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, Boolean> active;

    public static volatile SingularAttribute<User, Date> creationDate;
    public static volatile SingularAttribute<User, String> creationUser;
    public static volatile SingularAttribute<User, Date> updateDate;
    public static volatile SingularAttribute<User, String> updateUser;

    private User_() {
        // Only provides static constants.
    }

}