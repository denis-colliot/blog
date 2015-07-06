package dco.app.blog.shared.command.result;

import dco.app.blog.shared.command.result.base.Result;

/**
 * Result which contains a long.
 *
 * @author Denis
 */
public class LongResult implements Result {

    private Long value;

    public LongResult() {
        // Serialization.
    }

    public LongResult(final Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(final Long value) {
        this.value = value;
    }

}