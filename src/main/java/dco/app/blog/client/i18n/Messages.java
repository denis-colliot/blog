package dco.app.blog.client.i18n;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/Users/Denis/Documents/PROJECTS/blog/src/main/resources/dco/app/blog/client/i18n/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Hello {0}!".
   * 
   * @return translated "Hello {0}!"
   */
  @DefaultMessage("Hello {0}!")
  @Key("test.message")
  String test_message(String arg0);
}
