package dco.app.blog.server.util;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Pagination POJO used to specify a pagination on a select query.
 *
 * @author Denis
 */
public final class Pagination {

    /**
     * Offset position in the {@code ResultSet} to start pagination.<br/>
     * Starts at {@code 0}.
     */
    private final int startOffset;

    /**
     * Number of results that should be returned.
     */
    private final int resultsNumber;

    /**
     * Initializes a new {@code Pagination} instance.
     *
     * @param startOffset   See {@link #startOffset} description.
     * @param resultsNumber See {@link #resultsNumber} description.
     */
    public Pagination(final int startOffset, final int resultsNumber) {
        this.startOffset = startOffset;
        this.resultsNumber = resultsNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * See {@link #startOffset} description.
     */
    public int getStartOffset() {
        return startOffset;
    }

    /**
     * See {@link #resultsNumber} description.
     */
    public int getResultsNumber() {
        return resultsNumber;
    }

}