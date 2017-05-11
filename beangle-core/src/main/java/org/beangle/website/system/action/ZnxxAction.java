package org.beangle.website.system.action;

import java.util.Date;
import java.util.List;

import org.beangle.commons.lang.StrUtils;
import org.beangle.ems.security.User;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.convention.route.Action;
import org.beangle.website.common.action.FileAction;
import org.beangle.website.system.model.Znxx;
import org.beangle.website.system.model.Znxxhf;


/**
 * 站内消息
 * 
 * @author GOKU
 * 
 */
public class ZnxxAction extends FileAction {

	@Override
	protected String getEntityName() {
		return Znxx.class.getName();
	}
	
	@Override
	public String index(){
		return forward(new Action(this, "search"));
	}

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<Znxx> oql = OqlBuilder.from(Znxx.class, "znxx");
		populateConditions(oql);
		if (!isAllSitesAdmin()) {
			oql.where("znxx.sender.id=:senderId", getUserId());
		}
		oql.orderBy(getOrderString("znxx.time desc"));
		oql.limit(getPageLimit());
		return oql;
	}
	
	@Override
	protected void editSetting(Entity<?> entity) {
		put("currentDate",new Date());
	}
	
	@Override
	protected String saveAndForward(Entity<?> entity) {
		Znxx znxx = (Znxx) entity;
		znxx.setSender(getCurrentUser());
		znxx.setTime(new Date());
		Long[] userIds = StrUtils.splitToLong(get("znxx.receives"));
		znxx.getReceives().clear();
		znxx.getReceives().addAll(entityDao.get(User.class, userIds));
		String oldFileName = get("oldFileName");
		String tempFileName = znxx.getFilePath();
		znxx.setFilePath(moveAndRemoveAnnex(tempFileName, oldFileName));
		entityDao.saveOrUpdate(znxx);
		//发送消息
//		for(int i=0;i<userIds.length;i++){
//			User user = entityDao.get(User.class, userIds[i]);
//			Znxxhf hf = getZnxxhf(znxx, user);
//			if(hf == null){
//				hf = new Znxxhf();
//				hf.setZnxx(znxx);
//				hf.setUser(user);
//				entityDao.saveOrUpdate(hf);
//			}
//		}
		return redirect("search","info.save.success");
	}
	
	protected Znxxhf getZnxxhf(Znxx znxx,User user){
		OqlBuilder<Znxxhf> oql = OqlBuilder.from(Znxxhf.class,"z");
		oql.where("z.znxx.id=:znxxId",znxx.getId());
		oql.where("z.user.id=:userId",user.getId());
		List<Znxxhf> hfs = entityDao.search(oql);
		if(hfs.isEmpty()){
			return null;
		}
		return hfs.get(0);
	}
	
	public String remove(){
		Long[] znxxIds = getEntityIds();
		for(int i = 0;i < znxxIds.length;i++){
			Znxx znxx = entityDao.get(Znxx.class, znxxIds[i]);
			try {
				String hql = "delete from org.beangle.website.e.system.model.Znxxhf hf ";
				hql += " where hf.znxx.id="+znxx.getId();
				entityDao.executeUpdateHql(hql);
				entityDao.remove(znxx);
				//删除文件
				delete(znxx.getFilePath());
			} catch (Exception e) {
			}
		}
		return redirect("search","删除成功！");
	}
	
	public String ckhfqk(){
		Long znxxId = getLong("znxx.id");
		Znxx znxx = entityDao.get(Znxx.class, znxxId);
		put("znxx",znxx);
		put("yhfs",getZnxxhfUser(znxxId));
		return forward();
	}
	
	public String ckhf(){
		Long znxxId = getLong("znxx.id");
		Long userId = getLong("user.id");
		OqlBuilder<Znxxhf> oql = OqlBuilder.from(Znxxhf.class,"z");
		oql.where("z.znxx.id=:znxxId",znxxId);
		oql.where("z.user.id=:userId",userId);
		oql.orderBy("z.time desc");
		put("znxxhfs",entityDao.search(oql));
		return forward();
	}
	
	/**
	 * 获取已回复的用户
	 * @param znxxId 站内消息ID
	 * @return
	 */
	protected List<Znxxhf> getZnxxhfUser(Long znxxId){
		OqlBuilder<Znxxhf> oql = OqlBuilder.from(Znxxhf.class,"z");
		oql.where("z.znxx.id=:znxxId",znxxId);
		oql.orderBy("z.time desc");
		oql.select("z.user.id");
		return entityDao.search(oql);
	}
}
