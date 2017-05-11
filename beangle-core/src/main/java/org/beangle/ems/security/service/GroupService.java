package org.beangle.ems.security.service;

import java.util.List;

import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.GroupMember.Ship;
import org.beangle.ems.security.User;

public interface GroupService {

	public GroupMember getGroupMember(String groupName, User user, Ship ship);

	/**
	 * 查询所有用户组
	 * 
	 * @return
	 */
	public List<Group> findAll();

	/**
	 * 查询用户组里的用户
	 * @param group
	 * @return
	 */
	public List<User> findUser(Long groupId);
}
