package org.beangle.website.common.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.beangle.ems.security.User;
import org.beangle.model.query.builder.OqlBuilder;

/**
 * Chosen Action
 * @author CHII
 *
 */
public class ChosenAction extends BaseCommonAction {

	public String user(){
		String term = get("term");
		if(StringUtils.isEmpty(term)){
			term = get("key");
		}
		String paramValue = "%" + term + "%";
		OqlBuilder<User> query = OqlBuilder.from(User.class, "u");
		query.limit(getPageLimit());
//		query.where("(u.fullname like (:key) or u.name like (:key) or u.department.name like (:key) )", paramValue);
		query.where("(u.fullname like (:key) or u.name like (:key))", paramValue);
		query.where("enabled=true");
		List<User> users = entityDao.search(query);
		String type = get("type");
		put("type", type);
		put("users", users);
		return forward();
	}
	
	
}
