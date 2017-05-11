package org.beangle.website.system.action;

import org.beangle.commons.collection.Order;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.User;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.convention.route.Action;
import org.beangle.website.common.action.BaseCommonAction;


public class SelectUserAction  extends BaseCommonAction{
	@Override
	protected String getEntityName() {
		return User.class.getName();
	}
	
	public String index() {
		return forward(new Action(this, "search"));
	}
	
	/**
	 * 查询语句
	 */
	protected OqlBuilder<User> getQueryBuilder() {
		OqlBuilder<User> oql = OqlBuilder.from(User.class,"user");
		populateConditions(oql);
		String paramId = get("paramId");
		String isSingle = get("isSingle");
		put("paramId",paramId);
		put("isSingle",isSingle);
		oql.where("user.enabled =:enabled",true);
		String order = get(Order.ORDER_STR);
		oql.orderBy(order);
		oql.limit(getPageLimit());
		return oql;
	}
	
	/**
	 * 按角色选择
	 */
	public String jueSe(){
		OqlBuilder<User> oql = OqlBuilder.from(User.class,"user");
		String paramId = get("paramId");
		String isSingle = get("isSingle");
		put("paramId",paramId);
		put("isSingle",isSingle);
		oql.where("user.enabled =:enabled",true);
		populateConditions(oql);
		String order = get(Order.ORDER_STR);
		oql.orderBy(order);
		oql.limit(getPageLimit());
		
		put("users",entityDao.search(oql));
		
		OqlBuilder<Group> oql2 = OqlBuilder.from(Group.class,"group");
		oql.where("group.enabled =:enabled",true);
		populateConditions(oql2);
		String order2 = get(Order.ORDER_STR);
		oql2.orderBy(order2);
		oql2.limit(getPageLimit());
		
		put("groups",entityDao.search(oql2));
		
		return forward();
		
	}
}
