package dco.app.blog.client.i18n;

/**
 * Interface to represent the constants contained in resource bundle:
 * 	'/Users/Denis/Documents/PROJECTS/blog/src/main/resources/dco/app/blog/client/i18n/Constants.properties'.
 */
public interface Constants extends com.google.gwt.i18n.client.Constants {
  
  /**
   * Translated "Blog".
   * 
   * @return translated "Blog"
   */
  @DefaultStringValue("Blog")
  @Key("app.title")
  String app_title();

  /**
   * Translated ":".
   * 
   * @return translated ":"
   */
  @DefaultStringValue(":")
  @Key("form.label.separator")
  String form_label_separator();

  /**
   * Translated "Erreur".
   * 
   * @return translated "Erreur"
   */
  @DefaultStringValue("Erreur")
  @Key("message.error.defaultTitle")
  String message_error_defaultTitle();

  /**
   * Translated "Information".
   * 
   * @return translated "Information"
   */
  @DefaultStringValue("Information")
  @Key("message.info.defaultTitle")
  String message_info_defaultTitle();

  /**
   * Translated "Question".
   * 
   * @return translated "Question"
   */
  @DefaultStringValue("Question")
  @Key("message.question.defaultTitle")
  String message_question_defaultTitle();

  /**
   * Translated "Validation".
   * 
   * @return translated "Validation"
   */
  @DefaultStringValue("Validation")
  @Key("message.valid.defaultTitle")
  String message_valid_defaultTitle();

  /**
   * Translated "Alerte".
   * 
   * @return translated "Alerte"
   */
  @DefaultStringValue("Alerte")
  @Key("message.warning.defaultTitle")
  String message_warning_defaultTitle();
}
