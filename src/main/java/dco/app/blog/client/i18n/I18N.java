package dco.app.blog.client.i18n;

import com.google.gwt.core.shared.GWT;

/**
 * Contains global instances of i18n Constants and Messages.
 *
 * @author Denis
 */
public final class I18N {

    private I18N() {
    }

    public static final Constants CONSTANTS = (Constants) GWT.create(Constants.class);
    public static final Messages MESSAGES = (Messages) GWT.create(Messages.class);

}