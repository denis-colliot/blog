package dco.app.blog.shared.command.result.base;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

/**
 * Interface implemented by all dispatch commands results.<br/>
 * Ensures that command results implement {@code IsSerializable}.
 *
 * @author Denis
 */
public interface Result extends IsSerializable, Serializable {

}