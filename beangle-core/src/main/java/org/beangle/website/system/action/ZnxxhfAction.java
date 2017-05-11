package org.beangle.website.system.action;

import java.util.Date;
import java.util.List;

import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.convention.route.Action;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.website.system.model.Znxx;
import org.beangle.website.system.model.Znxxhf;

/**
 * 站内消息回复
 * @author GOKU
 *
 */
public class ZnxxhfAction extends BaseCommonAction {

	@Override
	protected String getEntityName() {
		return Znxx.class.getName();
	}
	
	@Override
	public String index() throws Exception {
		return forward(new Action(this, "search"));
	}

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<Znxx> oql = OqlBuilder.from(Znxx.class, "znxx");
		populateConditions(oql);
		oql.join("znxx.receives", "r");
		oql.where("r.id=:userId", getUserId());
		oql.orderBy(getOrderString("znxx.time desc"));
		oql.limit(getPageLimit());
		return oql;
	}
	
	@Override
	public String edit() {
		Znxx znxx = (Znxx) getEntity(Znxx.class,"znxx");
		put("znxxhfs",getZnxxhfs(znxx.getId(), getUserId()));
		put("znxxhf",new Znxxhf());
		put("znxx",znxx);
		return forward();
	}
	
	protected List<Znxxhf> getZnxxhfs(Long znxxId,Long userId){
		OqlBuilder<Znxxhf> oql = OqlBuilder.from(Znxxhf.class,"hf");
		oql.where("hf.znxx.id=:znxxId",znxxId);
		oql.where("hf.user.id=:userId",userId);
		oql.orderBy("hf.time desc");
		return entityDao.search(oql);
	}
	
	public String save(){
		Long znxxId = getLong("znxx.id");
		Znxx znxx = entityDao.get(Znxx.class, znxxId);
		Znxxhf znxxhf = populateEntity(Znxxhf.class,"znxxhf");
		znxxhf.setZnxx(znxx);
		znxxhf.setUser(getCurrentUser());
		znxxhf.setTime(new Date());
		entityDao.saveOrUpdate(znxxhf);
		if(!znxx.isReply()){
			znxx.setReply(true);
			entityDao.saveOrUpdate(znxx);
		}
		return redirect("search","info.save.success");
	}
	
}
