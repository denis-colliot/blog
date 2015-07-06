package dco.app.blog.client.event.page;

/**
 * Thrown of a page URL cannot be parsed.
 *
 * @author Denis
 */
public class PageParsingException extends Exception {

    /**
     * Serial id.
     */
    private static final long serialVersionUID = -7562901661618711604L;

    public PageParsingException() {
    }

    public PageParsingException(String message) {
        super(message);
    }

    public PageParsingException(Throwable cause) {
        super(cause);
    }

    public PageParsingException(String message, Throwable cause) {
        super(message, cause);
    }

}