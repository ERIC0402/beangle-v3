/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.notifiers.console;

import org.beangle.notification.Message;
import org.beangle.notification.NotificationException;
import org.beangle.notification.Notifier;

/**
 * @author beangle
 */
public class ConsoleNotifier implements Notifier<Message> {

	public String getType() {
		return "console";
	}

	public void deliver(Message context) throws NotificationException {
		System.out.println(context.getText());
	}

}
