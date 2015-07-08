package dco.app.blog.server.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

/**
 * {@link dco.app.blog.server.model.Authentication} meta-model.
 *
 * @author Denis
 */
@StaticMetamodel(Authentication.class)
public final class Authentication_ {

    public static volatile SingularAttribute<Authentication, Long> id;
    public static volatile SingularAttribute<Authentication, String> dateCreated;
    public static volatile SingularAttribute<Authentication, String> dateLastActive;

    public static volatile SingularAttribute<Authentication, Date> creationDate;
    public static volatile SingularAttribute<Authentication, String> creationUser;
    public static volatile SingularAttribute<Authentication, Date> updateDate;
    public static volatile SingularAttribute<Authentication, String> updateUser;

    private Authentication_() {
        // Only provides static constants.
    }

}