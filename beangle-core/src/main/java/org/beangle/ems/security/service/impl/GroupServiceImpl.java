package org.beangle.ems.security.service.impl;

import java.util.List;

import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.GroupMember.Ship;
import org.beangle.ems.security.User;
import org.beangle.ems.security.model.GroupMemberBean;
import org.beangle.ems.security.service.GroupService;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;

public class GroupServiceImpl implements GroupService {
	
	private EntityDao entityDao;
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public GroupMember getGroupMember(String groupName, User user, Ship ship) {
		List<Group> groups = entityDao.get(Group.class, "name", groupName);
		if(groups.isEmpty()){
			return null;
		}
		GroupMember gm = new GroupMemberBean(groups.get(0), user, ship);
		return gm;
	}

	public List<Group> findAll() {
		OqlBuilder<Group> query = OqlBuilder.from(Group.class, "group");
		query.where("enabled=true");
		return entityDao.search(query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUser(Long groupId) {
		OqlBuilder<?> query = OqlBuilder.from(Group.class, "g");
		query.join("g.members", "m");
		query.select("m.user");
		query.where("m.member = true");
		query.where("g.id = :groupid", groupId);
		query.cacheable();
		return (List<User>) entityDao.search(query);
	}

}
