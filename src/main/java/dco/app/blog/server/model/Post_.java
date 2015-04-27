package dco.app.blog.server.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

/**
 * {@link Post} meta-model.
 *
 * @author Denis
 */
@StaticMetamodel(Post.class)
public final class Post_ {

    public static volatile SingularAttribute<Post, Long> id;
    public static volatile SingularAttribute<Post, String> subject;
    public static volatile SingularAttribute<Post, String> content;

    public static volatile SingularAttribute<Post, Date> creationDate;
    public static volatile SingularAttribute<Post, String> creationUser;
    public static volatile SingularAttribute<Post, Date> updateDate;
    public static volatile SingularAttribute<Post, String> updateUser;

    private Post_() {
        // Only provides static constants.
    }

}