package dco.app.blog.shared.command.result;

import dco.app.blog.shared.command.result.base.Result;

/**
 * Result which contains an Integer.
 *
 * @author Denis
 */
public class IntegerResult implements Result {

    private Integer value;

    public IntegerResult() {
        // Serialization.
    }

    public IntegerResult(final Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(final Integer value) {
        this.value = value;
    }

}