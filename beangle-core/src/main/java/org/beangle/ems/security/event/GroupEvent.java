/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.event;

import org.beangle.ems.event.BusinessEvent;
import org.beangle.ems.security.Group;

/**
 * @author beangle
 * @version $Id: GroupEvent.java Jul 27, 2011 10:29:31 AM beangle $
 */
public class GroupEvent extends BusinessEvent {

	private static final long serialVersionUID = 2633756457351411934L;

	public GroupEvent(Group group) {
		super(group);
		this.resource = "用户组管理";
	}

	protected Group getGroup() {
		return (Group) source;
	}
}
