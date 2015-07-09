package dco.app.blog.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import dco.app.blog.client.i18n.I18N;
import dco.app.blog.shared.util.ClientUtils;

/**
 * <p>
 * Application available languages.
 * </p>
 * <p>
 * I18N resources (constants, mails, etc.) are generated for these available languages.<br>
 * If a new language should be supported by the application, make sure to update this enum.
 * </p>
 * <p>
 * See server-side {@link dco.app.blog.server.util.Languages} utility class.
 * </p>
 *
 * @author Denis
 */
public enum Language implements IsSerializable {

    // Enum's 'getLocale()' MUST return a valid "java.util.Locale" language parameter.

    // Enum values order defines the LoginView form field order.

    /**
     * French language.
     */
    FR,

    /**
     * English language.
     */
    EN,

    /**
     * Spanish language.
     */
    ES;

    /**
     * Returns the current {@link Language} corresponding <em>locale</em> name.<br>
     * The returned value is compatible with {@link java.util.Locale#Locale(String)} constructor.
     *
     * @return The current {@link Language} corresponding <em>locale</em> name.
     */
    public String getLocale() {
        return name().toLowerCase();
    }

    /**
     * Returns the given locale {@code value} corresponding {@link Language} instance.
     *
     * @param value
     *         The locale value (trimmed during process).<br/>
     *         Case insensitive.
     * @return The given locale {@code value} corresponding {@link Language} instance, or {@code null}.
     */
    public static Language fromString(final String value) {
        try {

            return Language.valueOf(ClientUtils.trimToEmpty(value).toUpperCase());

        } catch (final Exception e) {
            return null;
        }
    }

    /**
     * Returns the given {@code language} corresponding i18n name.<br>
     * This method should be executed from client-side. If executed from server-side, it returns the enum constant
     * name.
     *
     * @param language
     *         The language.
     * @return The given {@code language} corresponding i18n name, or {@code null}.
     */
    public static final String i18n(final Language language) {

        if (language == null) {
            return null;
        }

        if (!ClientUtils.isClient()) {
            return language.name();
        }

        switch (language) {
            case FR:
                return I18N.CONSTANTS.language_french();
            case EN:
                return I18N.CONSTANTS.language_english();
            case ES:
                return I18N.CONSTANTS.language_spanish();
            default:
                return language.name();
        }
    }

    /**
     * Returns the given {@code i18n} value corresponding language instance.<br>
     * This method should be executed from client-side. If executed from server-side, it returns {@code null}.
     *
     * @param i18n
     *         The {@code i18n} language value.
     * @return The given {@code i18n} value corresponding language instance, or {@code null}.
     */
    public static final Language fromI18n(final String i18n) {

        if (!ClientUtils.isClient()) {
            return null;
        }

        if (I18N.CONSTANTS.language_french().equals(i18n)) {
            return FR;

        } else if (I18N.CONSTANTS.language_english().equals(i18n)) {
            return EN;

        } else if (I18N.CONSTANTS.language_spanish().equals(i18n)) {
            return ES;

        } else {
            return null;
        }
    }

}