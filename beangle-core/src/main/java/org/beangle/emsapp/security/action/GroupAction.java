/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.lang.StrUtils;
import org.beangle.ems.security.Category;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;
import org.beangle.ems.security.model.GroupMemberBean;
import org.beangle.ems.security.service.AuthorityService;
import org.beangle.ems.security.service.UserService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.emsapp.security.helper.GroupPropertyExtractor;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.exporter.PropertyExtractor;

/**
 * 用户组信息维护响应类
 * 
 * @author beangle 2005-9-29
 */
public class GroupAction extends SecurityActionSupport {

	private UserService userService;

	@Override
	protected String getShortName() {
		return "userGroup";
	}

	protected void indexSetting() {
		put("categories", entityDao.getAll(Category.class));
	}

	protected void editSetting(Entity<?> entity) {
		put("categories", entityDao.getAll(Category.class));
	}

	protected OqlBuilder<Group> getQueryBuilder() {
		OqlBuilder<Group> entityQuery = OqlBuilder.from(getEntityName(), "userGroup");
		if (!isAdmin()) {
			entityQuery.join("userGroup.members", "gm");
			entityQuery.where("gm.user.id=:me and gm.manager=true", getUserId());
		}
		populateConditions(entityQuery);
		entityQuery.limit(getPageLimit()).orderBy("userGroup.id");
		return entityQuery;
	}

	protected PropertyExtractor getPropertyExtractor() {
		return new GroupPropertyExtractor(getTextResource());
	}

	protected String saveAndForward(Entity<?> entity) {
		Group group = (Group) entity;
		if (entityDao.duplicate(Group.class, group.getId(), "name", group.getName())) { return redirect(
				"edit", "error.notUnique"); }
		if (!group.isPersisted()) {
			User creator = userService.get(getUserId());
			userService.createGroup(creator, group);
		} else {
			group.setUpdatedAt(new Date(System.currentTimeMillis()));
			if (!group.isPersisted()) {
				group.setCreatedAt(new Date(System.currentTimeMillis()));
			}
			entityDao.saveOrUpdate(group);
		}
		return redirect("search", "info.save.success");
	}

	/**
	 * 删除一个或多个用户组
	 * 
	 * @return
	 */
	public String remove() {
		Long[] groupIds = getEntityIds(getShortName());
		User curUser = userService.get(getUserId());
		try {
			userService.removeGroup(curUser, entityDao.get(Group.class, groupIds));
		} catch (Exception e) {
			logger.info("removeAndForwad failure", e);
			return redirect("search", "info.delete.failure");
		}
		return redirect("search", "info.remove.success");
	}
	
	/**
	 * 分配用户
	 * @return
	 */
	public String allocationUser(){
		Group group = getEntity(Group.class,"userGroup");
		OqlBuilder<User> query = getUserQueryBuilder(group);
		put("users",entityDao.search(query));
		put("group",group);
		return forward();
	}
	
	private OqlBuilder<User> getUserQueryBuilder(Group group){
		User manager = entityDao.get(User.class, getUserId());
		OqlBuilder<User> userQuery = OqlBuilder.from(User.class, "user");
		// 查询用户组
		StringBuilder sb = new StringBuilder("exists(from user.groups ug where ");
		List<Object> params = CollectUtils.newArrayList();
		boolean queryGroup = false;
		if (!isAdmin()) {
			List<Group> mngGroups = userService.getGroups(manager, GroupMember.Ship.MANAGER);
			if (mngGroups.isEmpty()) {
				sb.append("1=0");
			} else {
				sb.append("ug.group in(:groups) ");
				params.add(mngGroups);
			}
			queryGroup = true;
		}
		if (group != null) {
			if (queryGroup) {
				sb.append(" and ");
			}
			sb.append("ug.member=1 and ug.group.id=:groupId ");
			params.add(group.getId());
			queryGroup = true;
		}
		if (queryGroup) {
			sb.append(')');
			Condition groupCondition = new Condition(sb.toString());
			groupCondition.params(params);
			userQuery.where(groupCondition);
		}

		populateConditions(userQuery);
		userQuery.orderBy("user.name").limit(getPageLimit());
		return userQuery;
	}
	
	public String addUser(){
		Group group = getEntity(Group.class,"userGroup");
		put("group",group);
		return forward();
	}
	
	public String saveUserGroup(){
		Group group = getEntity(Group.class,"userGroup");
		Long[] userIds = StrUtils.splitToLong(get("userIds"));
		Set<GroupMember> newGms = CollectUtils.newHashSet();
		Set<GroupMember> updateGms = CollectUtils.newHashSet();
		if(userIds != null && userIds.length > 0){
			List<User> users = entityDao.get(User.class, userIds);
			for (User user : users) {
				Set<GroupMember> gms = user.getGroups();
				boolean hasGM = false;
				for (GroupMember groupMember : gms) {
					if(group != null && group.getId() != null && group.getId().equals(groupMember.getGroup().getId())){
						groupMember.setMember(true);
						hasGM = true;
						updateGms.add(groupMember);
					}
				}
				if(!hasGM){
					GroupMember gm = new GroupMemberBean(group, user, GroupMember.Ship.MEMBER);
					newGms.add(gm);
				}
			}
			entityDao.saveOrUpdate(newGms);
			entityDao.saveOrUpdate(updateGms);
		}
		return redirect("allocationUser","权限分配成功","userGroup="+group.getId());
	}
	
	public String removeUserGroup(){
		Group group = getEntity(Group.class,"userGroup");
		Long[] userIds = getEntityIds("user");
		OqlBuilder<GroupMember> query = OqlBuilder.from(GroupMember.class,"gm");
		query.where("gm.group.id=:groupId", group.getId());
		query.where("gm.member=1");
		if(userIds != null && userIds.length >0){
			query.where("gm.user.id in (:userIds)",userIds);
		}else{
			query.where("1!=1");
		}
		List<GroupMember> gms = entityDao.search(query);
		Set<GroupMember> removeGms = CollectUtils.newHashSet();
		if(CollectionUtils.isNotEmpty(gms)){
			for (GroupMember groupMember : gms) {
				groupMember.setMember(false);
				if(!groupMember.isMember() && !groupMember.isManager() && !groupMember.isGranter()){
					removeGms.add(groupMember);
				}
			}
		}
		entityDao.saveOrUpdate(gms);
		entityDao.remove(removeGms);
		return redirect("allocationUser","成功解除所选用户的权限","userGroup="+group.getId());
	}
	
	public String findUserByGroup(){
		String key = get("key");
		OqlBuilder<User> query = OqlBuilder.from(User.class,"u");
		query.where("(u.name like '%" + key + "%' or u.fullname like '%" + key + "%')");
		put("users",entityDao.search(query));
		return forward();
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected String getEntityName() {
		return Group.class.getName();
	}

}
