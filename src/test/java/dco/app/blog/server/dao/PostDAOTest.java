package dco.app.blog.server.dao;

import com.google.inject.Inject;
import dco.app.blog.server.model.Post;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Test
    public void insert() {
        final Post post = new Post();
        post.setSubject("Test post subject");
        post.setContent("Test post dumb content.");
        postDAO.persist(post, null);
    }

    @Test
    public void testFindAll() {

        // Inserts 5 posts.
        for (int i = 1; i <= 5; i++) {
            insert();
        }

        // Finds posts.
        final List<Post> posts = postDAO.find(null);

        LOGGER.debug("Posts: {}", posts);

        Assert.assertNotNull(posts);
        Assert.assertEquals(5, posts.size());
    }

}
