package dco.app.blog.shared.command.base;

import com.google.gwt.user.client.rpc.IsSerializable;
import dco.app.blog.shared.command.result.base.Result;

/**
 * <p>
 * Command interface.
 * </p>
 * <p>
 * All command implementations should implements this interface and declare an empty constructor.
 * </p>
 *
 * @param <R>
 *         The command result type.
 * @author Denis
 */
public interface Command<R extends Result> extends IsSerializable {

}