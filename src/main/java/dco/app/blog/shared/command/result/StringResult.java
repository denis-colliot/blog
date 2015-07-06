package dco.app.blog.shared.command.result;

import dco.app.blog.shared.command.result.base.Result;

/**
 * Result which contains an String.
 *
 * @author Denis
 */
public class StringResult implements Result {

    /**
     * The string value.
     */
    private String value;

    public StringResult() {
        // Serialization.
    }

    public StringResult(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

}