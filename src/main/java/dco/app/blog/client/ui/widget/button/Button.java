package dco.app.blog.client.ui.widget.button;

import com.google.gwt.event.dom.client.ClickHandler;
import dco.app.blog.client.ui.widget.Loadable;

/**
 * {@link Loadable} button implementation.
 *
 * @author Denis
 */
public class Button extends com.google.gwt.user.client.ui.Button implements Loadable {

    /**
     * Loading state of the button.
     */
    private boolean loading;

    /**
     * Initial enabled state of the button.
     */
    private boolean initialEnabledState = isEnabled();

    /**
     * Creates a new button.
     */
    public Button() {
        super();
    }

    /**
     * Creates a new button with the given HTML.
     *
     * @param html
     *         the button text as HTML.
     */
    public Button(final String html) {
        super(html);
    }

    /**
     * Creates a new button with the given HTML and specified selection listener.
     *
     * @param html
     *         the button's text as HTML.
     * @param handler
     *         the button click handler.
     */
    public Button(final String html, final ClickHandler handler) {
        super(html, handler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLoading(final boolean loading) {

        if (!this.loading && loading) {
            super.setEnabled(false);
            // TODO replaceIcon(IconImageBundle.ICONS.loading());

        } else if (this.loading && !loading) {
            super.setEnabled(initialEnabledState);
            // TODO setIcon(getIcon());
        }

        this.loading = loading;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoading() {
        return loading;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        initialEnabledState = enabled;
    }

}