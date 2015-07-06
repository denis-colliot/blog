package dco.app.blog.shared.command.result;

import dco.app.blog.shared.command.result.base.Result;

/**
 * Result which contains a boolean.
 *
 * @author Denis
 */
public class BooleanResult implements Result {

    private boolean value;

    public BooleanResult() {
        // Serialization.
    }

    public BooleanResult(final boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(final boolean value) {
        this.value = value;
    }

}