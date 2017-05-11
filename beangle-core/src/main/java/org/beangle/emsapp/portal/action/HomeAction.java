package org.beangle.emsapp.portal.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.property.PropertyConfig;
import org.beangle.commons.property.PropertyConfigFactory;
import org.beangle.ems.security.User;
import org.beangle.ems.security.model.SystemConfig;
import org.beangle.ems.security.nav.Menu;
import org.beangle.ems.security.nav.MenuProfile;
import org.beangle.ems.security.nav.service.MenuService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.EntityQuery;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.util.HierarchyEntityUtil;
import org.beangle.security.auth.AuthenticationManager;
import org.beangle.security.core.AuthenticationException;
import org.beangle.website.system.model.Zntz;
import org.beangle.website.system.model.Znxx;

import com.opensymphony.xwork2.ActionContext;

/**
 * 加载用户主界面
 * 
 * @author duyaming
 * @author beangle
 */
/**
 * @author qianjia
 */
public class HomeAction extends SecurityActionSupport {

	private static final long serialVersionUID = 2839594070697670971L;
	protected AuthenticationManager authenticationManager;
	protected MenuService menuService;
	protected PropertyConfigFactory configFactory;

	public PropertyConfig getSystemConfig() {
		return configFactory.getConfig();
	}

	public String index() {
		Long userId = getUserId();
		if (null == userId) {
			throw new AuthenticationException("without login");
		}
		Long categoryId = getLong("security.categoryId");
		if (null == categoryId) {
			categoryId = getUserCategoryId();
		}
	        // else {
	        // if (!categoryId.equals(getUserCategoryId())) {
	        // Category newCategory = entityDao.get(Category.class, categoryId);
	        // SecurityUtils.getPrincipal().changeCategory(newCategory);
	        // }
	        // }
		put("categoryId", categoryId);
		ActionContext.getContext().getSession().put("security.categoryId", categoryId);
		User user = entityDao.get(User.class, getUserId());
		List<Menu> dd = menuService.getMenus(getMenuProfile(categoryId), user);
		List topMenus = CollectUtils.newArrayList();
		for (Menu m : dd) {
			if (null == m.getParent()) {
				topMenus.add(m);
			}
		}
		// StringBuilder sb = new StringBuilder();
		// sb.append("1111111111111").append("1111111111111").append("1111111111111").append("1111111111111").append("1111111111111");
		put("menus", topMenus);
		allSubmenus();
		put("user", user);
		put("systemConfig", getSystemConfig());

		java.util.Date date = new java.util.Date();
		// 星期
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		put("week", calendar.get(Calendar.DAY_OF_WEEK) - 1);
		put("date", date);
		put("isAdmin",isAdmin());
		//系统配置
		//put("sc",getSc());
		
		putNotice();
		putMessage();
		return forward();
	}
	
	protected void putNotice(){
		//站内通知
		OqlBuilder<Zntz> zntzQuery = OqlBuilder.from(Zntz.class,"zntz");
		zntzQuery.where("zntz.state=1");
		zntzQuery.orderBy("zntz.time desc");
		put("zntzs",entityDao.search(zntzQuery));
	}
	
	protected void putMessage(){
		OqlBuilder<Znxx> znxxQuery = OqlBuilder.from(Znxx.class,"znxx");
		znxxQuery.where("znxx.reply=0");
		znxxQuery.join("znxx.receives", "receive");
		znxxQuery.where("receive.name=:name", getUsername());
		znxxQuery.orderBy("znxx.time desc");
		put("znxxs",entityDao.search(znxxQuery));
	}

	protected List<MenuProfile> getMenuProfile(Long categoryId) {
		EntityQuery query = new EntityQuery(MenuProfile.class, "mp");
		query.add(new Condition("category.id=:categoryId", categoryId));
		query.setCacheable(true);
		List<MenuProfile> mps = (List) entityDao.search(query);
		if (mps.isEmpty()) {
			return null;
		} else {
			return mps;
		}
	}

	/**
	 * 加载特定模块下的所有子模块
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String submenus() {
		Long menuId = getLong("menu.id");
		User user = entityDao.get(User.class, getUserId());
		List<Menu> modulesTree = menuService.getMenus(getMenuProfile(getUserCategoryId()), user);
		List<Menu> modules = CollectUtils.newArrayList();
		if (null != menuId) {
			Menu menu = entityDao.get(Menu.class, menuId);
			modulesTree.retainAll(HierarchyEntityUtil.getFamily(menu));
			for (Menu m : modulesTree) {
				if (m.getParent() == menu) {
					modules.add(m);
				}
			}
		} else {
			for (Menu m : modulesTree) {
				if (m.getParent() == null) {
					modules.add(m);
				}
			}
		}
		if (menuId == null) {
			return null;
		}
		put("menuPath", HierarchyEntityUtil.getPath(entityDao.get(Menu.class, menuId)));
		put("submenus", modules);
		put("userId", getUserId());

		// 获取站内通知、站内消息
		PageLimit pageLimit = getPageLimit();
		pageLimit.setPageSize(5);
		OqlBuilder<Zntz> zntzQuery = OqlBuilder.from(Zntz.class, "zntz");
		zntzQuery.where("zntz.state=1");
		zntzQuery.orderBy("zntz.time desc");
		zntzQuery.limit(pageLimit);
		put("zntzs", entityDao.search(zntzQuery));

		OqlBuilder<Znxx> znxxQuery = OqlBuilder.from(Znxx.class, "znxx");
		znxxQuery.join("znxx.receives", "r");
		znxxQuery.where("r.id=:userId", getUserId());
		znxxQuery.orderBy("znxx.time desc");
		znxxQuery.limit(pageLimit);
		put("znxxs", entityDao.search(znxxQuery));
		return forward();
	}
	
	public String allSubmenus() {
		User user = entityDao.get(User.class, getUserId());
		List<Menu> modulesTree = menuService.getMenus(getMenuProfile(getUserCategoryId()), user);
		List<Menu> modules = CollectUtils.newArrayList();
//		if (null != menuId) {
//			Menu menu = entityDao.get(Menu.class, menuId);
//			modulesTree.retainAll(HierarchyEntityUtil.getFamily(menu));
//			for (Menu m : modulesTree) {
//				if (m.getParent() == menu) {
//					modules.add(m);
//				}
//			}
//		} else {
		for (Menu m : modulesTree) {
			if (m.getParent() != null) {
				modules.add(m);
			}
		}
//		}
		//put("menuPath", HierarchyEntityUtil.getPath(entityDao.get(Menu.class, menuId)));
		put("submenus", modules);
		put("userId", getUserId());

		// 获取站内通知、站内消息
		PageLimit pageLimit = getPageLimit();
		pageLimit.setPageSize(5);
		OqlBuilder<Zntz> zntzQuery = OqlBuilder.from(Zntz.class, "zntz");
		zntzQuery.where("zntz.state=1");
		zntzQuery.orderBy("zntz.time desc");
		zntzQuery.limit(pageLimit);
		put("zntzs", entityDao.search(zntzQuery));

		OqlBuilder<Znxx> znxxQuery = OqlBuilder.from(Znxx.class, "znxx");
		znxxQuery.join("znxx.receives", "r");
		znxxQuery.where("r.id=:userId", getUserId());
		znxxQuery.orderBy("znxx.time desc");
		znxxQuery.limit(pageLimit);
		put("znxxs", entityDao.search(znxxQuery));
		return forward();
	}
	
	/**
	 * 加载特定模块下的所有子模块，无限级
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String menus() {
		Long menuId = getLong("menu.id");
		if (menuId == null) {
			return null;
		}
		Menu menu = entityDao.get(Menu.class, menuId);
		User user = entityDao.get(User.class, getUserId());
		ArrayList<MenuProfile> profiles = new ArrayList<MenuProfile>();
		profiles.add(menu.getProfile());
		List<Menu> modulesTree = menuService.getMenus(profiles, user);
		List<Menu> modules = CollectUtils.newArrayList();
		if (null != menuId) {
			modulesTree.retainAll(HierarchyEntityUtil.getFamily(menu));
			for (Menu m : modulesTree) {
				if (StringUtils.isNotEmpty(m.getCode()) && m.getCode().startsWith(menu.getCode())) {
					modules.add(m);
				}
			}
		}
		put("pmenu", menu);
		put("menuPath", HierarchyEntityUtil.getPath(entityDao.get(Menu.class, menuId)));
		put("modules", modules);
		put("userId", getUserId());
		
		return forward();
	}

	/**
	 * 加载 (三级菜单)
	 * 
	 * @return
	 */
	public String childmenus() {
		Long menuId = getLong("menu.id");
		User user = entityDao.get(User.class, getUserId());
		List<Menu> modulesParent = menuService.getMenus(getMenuProfile(getUserCategoryId()), user);
		List<Menu> modulesTree = menuService.getMenus(getMenuProfile(getUserCategoryId()), user);
		List<Menu> modules = CollectUtils.newArrayList();
		if (null != menuId) {
			Menu menu = entityDao.get(Menu.class, menuId);
			modulesTree.retainAll(HierarchyEntityUtil.getFamily(menu));
			modulesTree.remove(menu);
			modulesParent.retainAll(HierarchyEntityUtil.getFamily(menu.getParent()));
			for (Menu m : modulesParent) {
				if (m.getParent() == menu.getParent()) {
					modules.add(m);
				}
			}
			put("menu", menu);
			put("menuPath", HierarchyEntityUtil.getPath(menu));
		}

		put("submenus", modulesTree);
		put("parentmenus", modules);
		return forward();
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public final void setPropertyConfigFactory(PropertyConfigFactory configFactory) {
		this.configFactory = configFactory;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
}
