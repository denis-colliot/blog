package dco.app.blog.server.model;

import dco.app.blog.server.model.base.AbstractEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Denis on 24/04/15.
 */
@Entity
@Table(name = "t_post_po")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "po_id", nullable = false))
})
public class Post extends AbstractEntity<Long> {

    // --
    //
    // Attributes.
    //
    // --

    @Column(name = "po_subject")
    private String subject;

    @Column(name = "po_content")
    private String content;

    // --
    //
    // Methods.
    //
    // --

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendToString(final ToStringBuilder builder) {
        builder.append("subject", subject);
        builder.append("content", content);
    }

    // --
    //
    // Getters & Setters.
    //
    // --

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
