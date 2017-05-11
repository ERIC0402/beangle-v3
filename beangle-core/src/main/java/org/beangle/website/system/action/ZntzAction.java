package org.beangle.website.system.action;

import java.util.Date;

import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.convention.route.Action;
import org.beangle.website.common.action.FileAction;
import org.beangle.website.system.model.Zntz;


/**
 * 站内通知
 * 
 * @author GOKU
 * 
 */
public class ZntzAction extends FileAction {

	@Override
	protected String getEntityName() {
		return Zntz.class.getName();
	}
	
	@Override
	public String index(){
		return forward(new Action(this, "search"));
	}

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<Zntz> oql = OqlBuilder.from(Zntz.class, "zntz");
		populateConditions(oql);
		if (!isAllSitesAdmin()) {
			oql.where("zntz.sender.id=:senderId", getUserId());
		}
		oql.orderBy(getOrderString("zntz.time desc"));
		oql.limit(getPageLimit());
		return oql;
	}
	
	@Override
	protected void editSetting(Entity<?> entity) {
		put("currentDate",new Date());
	}
	
	@Override
	protected String saveAndForward(Entity<?> entity) {
		Zntz zntz = (Zntz) entity;
		zntz.setSender(getCurrentUser());
		zntz.setTime(new Date());
		String oldFileName = get("oldFileName");
		String tempFileName = zntz.getFilePath();
		zntz.setFilePath(moveAndRemoveAnnex(tempFileName, oldFileName));
		entityDao.saveOrUpdate(zntz);
		return redirect("search","info.save.success");
	}
	
	public String remove(){
		Long[] zntzIds = getEntityIds();
		for(int i = 0;i < zntzIds.length;i++){
			Zntz zntz = entityDao.get(Zntz.class, zntzIds[i]);
			try {
				entityDao.remove(zntz);
				//删除文件
				delete(zntz.getFilePath());
			} catch (Exception e) {
			}
		}
		return redirect("search","删除成功！");
	}
}
