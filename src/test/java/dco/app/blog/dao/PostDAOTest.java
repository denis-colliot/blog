package dco.app.blog.dao;

import com.google.inject.Inject;
import dco.app.blog.server.dao.PostDAO;
import dco.app.blog.server.model.Post;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaDelete;
import java.util.List;

/**
 * Created by Denis on 25/04/15.
 */
public class PostDAOTest extends AbstractDAOTest {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PostDAOTest.class);

    @Inject
    private PostDAO postDAO;

    @Before
    public void before() {
        reset();

        for (int i = 1; i <= 5; i++) {
            final Post post = new Post();
            post.setSubject("Test post #" + i + " subject");
            post.setContent("Test post #" + i + " dumb content.");
            postDAO.persist(post, null);
        }
    }

    @Test
    public void testFindAll() {
        final List<Post> posts = postDAO.find(null);

        LOGGER.debug("Posts: {}", posts);

        Assert.assertNotNull(posts);
        Assert.assertEquals(5, posts.size());
    }

    @After
    public void reset() {
        final CriteriaDelete<Post> delete = builder().createCriteriaDelete(Post.class);
        delete.from(Post.class);
        postDAO.remove(delete);
    }

}
