/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.sql.Date;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;
import org.beangle.ems.security.event.GroupCreationEvent;
import org.beangle.ems.security.event.UserAlterationEvent;
import org.beangle.ems.security.event.UserCreationEvent;
import org.beangle.ems.security.event.UserRemoveEvent;
import org.beangle.ems.security.event.UserStatusEvent;
import org.beangle.ems.security.model.GroupMemberBean;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.springframework.util.Assert;

/**
 * 用户信息服务的实现类
 * 
 * @author dell,beangle 2005-9-26
 */
public class UserServiceImpl extends BaseServiceImpl implements UserService {
	
	public boolean isAdmin(User user) {
		return User.ROOT.equals(user.getId());
	}

	public boolean isAdmin(Long userId) {
		return User.ROOT.equals(userId);
	}

	public User get(String name, String password) {
		Map<String, Object> params = CollectUtils.newHashMap();
		params.put("name", name);
		params.put("password", password);
		List<?> userList = entityDao.searchHQLQuery(
				"from User user where  user.name = :name and user.password = :password", params);
		if (userList.size() > 0) return (User) userList.get(0);
		else return null;
	}

	public User get(Long id) {
		return entityDao.get(User.class, id);
	}

	public User get(String loginName) {
		if (StringUtils.isEmpty(loginName)) return null;
		OqlBuilder<User> entityQuery = OqlBuilder.from(User.class, "user");
		entityQuery.where("user.name=:name", loginName);
		List<User> users = entityDao.search(entityQuery);
		return (users.isEmpty()) ? null : users.get(0);
	}

	public List<User> getUsers(Long[] userIds) {
		return entityDao.get(User.class, userIds);
	}

	public int updateState(final User manager, Long[] ids, final boolean enabled) {
		assert (null == ids || ids.length < 1);
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) CollectionUtils.select(getUsers(ids), new Predicate() {
			public boolean evaluate(Object object) {
				User one = (User) object;
				return isManagedBy(manager, one) && !manager.equals(one) && (one.isEnabled() != enabled);
			}
		});

		for (int i = 0; i < users.size(); i++) {
			User cur = users.get(i);
			cur.setEnabled(enabled);
		}
		if (!users.isEmpty()) {
			entityDao.saveOrUpdate(users);
			publish(new UserStatusEvent(users, enabled));
		}
		return users.size();
	}

	public void saveOrUpdate(User user) {
		user.setUpdatedAt(new Date(System.currentTimeMillis()));
		if (!user.isPersisted()) {
			user.setCreatedAt(new Date(System.currentTimeMillis()));
		}
		entityDao.saveOrUpdate(user);
		try {
			publish(new UserAlterationEvent(Collections.singletonList(user)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	// workground for no session
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Group> getGroups(User user, GroupMember.Ship ship) {
		if (isAdmin(user) && !ObjectUtils.equals(ship, GroupMember.Ship.MEMBER)) return entityDao
				.getAll(Group.class);
		OqlBuilder builder = OqlBuilder.from(GroupMember.class, "gm");
		builder.where("gm.user=:user", user).select("distinct gm.group");
		if (null != ship) {
			if (ship.equals(GroupMember.Ship.MEMBER)) builder.where("gm.member=true");
			if (ship.equals(GroupMember.Ship.MANAGER)) builder.where("gm.manager=true");
			if (ship.equals(GroupMember.Ship.GRANTER)) builder.where("gm.granter=true");
		}
//		builder.cacheable();
		return entityDao.search(builder);
	}

	public List<GroupMember> getGroupMembers(User user, GroupMember.Ship ship) {
		if (isAdmin(user) && !ObjectUtils.equals(ship, GroupMember.Ship.MEMBER)) {
			List<GroupMember> members = CollectUtils.newArrayList();
			List<Group> groups = entityDao.getAll(Group.class);
			for (Group group : groups) {
				GroupMemberBean gmb = new GroupMemberBean(group, user, GroupMember.Ship.MEMBER);
				gmb.setGranter(true);
				gmb.setManager(true);
				members.add(gmb);
			}
			return members;
		}
		OqlBuilder<GroupMember> builder = OqlBuilder.from(GroupMember.class, "gm");
		String typesql = "";
		if (null != ship) {
			if (ship.equals(GroupMember.Ship.MEMBER)) typesql = " gm.member=true";
			if (ship.equals(GroupMember.Ship.MANAGER)) typesql = " gm.manager=true";
			if (ship.equals(GroupMember.Ship.GRANTER)) typesql = " gm.granter=true";
		}
		builder.where("gm.user=:user and " + typesql, user, user.getId());
		return entityDao.search(builder);
	}

	public void createUser(User creator, User newUser) {
		newUser.setCreator(creator);
		newUser.setUpdatedAt(new Date(System.currentTimeMillis()));
		newUser.setCreatedAt(new Date(System.currentTimeMillis()));
		entityDao.saveOrUpdate(newUser);
		publish(new UserCreationEvent(Collections.singletonList(newUser)));
	}

	public void removeUser(User manager, User user) {
		List<User> removed=CollectUtils.newArrayList();
		if (isManagedBy(manager, user)) {
			entityDao.remove(user);
			removed.add(user);
		}
		publish(new UserRemoveEvent(removed));
	}

	public boolean isManagedBy(User manager, User user) {
		return (isAdmin(manager) || manager.equals(user.getCreator()) || isMember(manager, user));
	}
	
	/**
	 * 是否成员
	 * @param manager 当前用户
	 * @param user 要处理用户
	 * @return
	 */
	protected boolean isMember(User manager, User user){
		boolean isMember = false;
		if(!manager.equals(user)){
			Set<GroupMember> gms = manager.getGroups();
			Set<Group> managers = CollectUtils.newHashSet();
			for(Iterator<GroupMember> it = gms.iterator();it.hasNext();){
				GroupMember gm = it.next();
				if(gm.isManager()){
					managers.add(gm.getGroup());
				}
			}
			if(!managers.isEmpty()){
				Set<GroupMember> gms2 = user.getGroups();
				for(Iterator<GroupMember> it = gms2.iterator();it.hasNext();){
					GroupMember gm = it.next();
					if(gm.isMember()){
						if(managers.contains(gm.getGroup())){
							isMember = true;
							break;
						}
					}
				}
			}
		}
		return isMember;
	}

	public void createGroup(User owner, Group group) {
		group.setUpdatedAt(new Date(System.currentTimeMillis()));
		group.setCreatedAt(new Date(System.currentTimeMillis()));
		group.setOwner(owner);
		group.getMembers().add(new GroupMemberBean(group, owner, GroupMember.Ship.MANAGER));
		entityDao.saveOrUpdate(group);
		publish(new GroupCreationEvent(group));
	}

	/**
	 * 超级管理员不能删除
	 */
	public void removeGroup(User manager, List<Group> groups) {
		List<Object> removed = CollectUtils.newArrayList();
		for (final Group group : groups) {
			if (group.getOwner().equals(manager) || isAdmin(manager)) entityDao.remove(group);
		}
		entityDao.remove(removed);
	}

	public List<User> findUser(Group group) {
		Assert.notNull(group, "用户组不能为空！");
		Assert.notNull(group.getId(), "用户组不能为空！");
		OqlBuilder<User> query = OqlBuilder.from(User.class, "user");
		query.join("user.groups", "gm");
		query.where("gm.group.id = :groupid and gm.member = true", group.getId());
		return entityDao.search(query);
	}
}
