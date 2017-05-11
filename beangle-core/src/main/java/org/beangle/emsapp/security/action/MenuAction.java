/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.Authority;
import org.beangle.ems.security.Category;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.model.CategoryBean;
import org.beangle.ems.security.model.ResourceBean;
import org.beangle.ems.security.model.SubSystem;
import org.beangle.ems.security.nav.Menu;
import org.beangle.ems.security.nav.MenuProfile;
import org.beangle.ems.security.nav.model.MenuBean;
import org.beangle.ems.security.nav.model.MenuProfileBean;
import org.beangle.ems.security.nav.service.MenuService;
import org.beangle.emsapp.security.helper.MenuPropertyExtractor;
import org.beangle.emsapp.security.service.ResourceService;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.exporter.PropertyExtractor;
import org.beangle.model.util.HierarchyEntityUtil;
import org.beangle.website.common.action.FileAction;
import org.beangle.website.common.util.FileUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.opensymphony.xwork2.ActionContext;

/**
 * 系统模块(菜单)管理响应类
 * 
 * @author 鄂州蚊子 2008-8-4
 */
public class MenuAction extends FileAction {

	private MenuService menuService;
	private ResourceService resourceService;
	
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	protected void indexSetting() {
		put("profiles", entityDao.getAll(MenuProfile.class));
	}

	protected void editSetting(Entity<?> entity) {
		put("profiles", entityDao.getAll(MenuProfile.class));
		Menu menu = (Menu) entity;
		List<Menu> folders = CollectUtils.newArrayList();
		OqlBuilder<Resource> builder = OqlBuilder.from(Resource.class, "r");
		if (null != menu.getProfile() && null != menu.getProfile().getId()) {
			MenuProfile profile = entityDao.get(MenuProfile.class, menu.getProfile().getId());
			builder.where("exists(from r.categories as rc where rc=:category)", profile.getCategory());
			// 查找可以作为父节点的菜单
			OqlBuilder<Menu> folderBuilder = OqlBuilder.from(Menu.class, "m");
			folderBuilder.where("m.entry is null and m.profile=:profile", profile);
			folderBuilder.orderBy("m.code");
			folders = entityDao.search(folderBuilder);
			if (null != menu.getParent() && !folders.contains(menu.getParent())) folders
					.add(menu.getParent());
			folders.removeAll(HierarchyEntityUtil.getFamily(menu));
		}
		List<Resource> resurces = entityDao.search(builder);
		Set<Resource> existResources = menu.getResources();
		if (null != resurces) {
			resurces.removeAll(existResources);
		}
		put("parents", folders);
		put("resources", resurces);
		put("subSystems",entityDao.getAll(SubSystem.class));
	}

	@Override
	protected String removeAndForward(Collection<?> entities) {
		@SuppressWarnings("unchecked")
		List<Menu> menus = (List<Menu>) entities;
		List<Menu> parents = CollectUtils.newArrayList();
		for (Menu menu : menus) {
			if (null != menu.getParent()) {
				menu.getParent().getChildren().remove(menu);
				parents.add(menu.getParent());
			}
		}
		entityDao.saveOrUpdate(parents);
		return super.removeAndForward(entities);
	}

	protected String saveAndForward(Entity<?> entity) {
		Menu menu = (Menu) entity;
		try {
			List<Resource> resources = entityDao.get(Resource.class, getAll("resourceId", Long.class));
			menu.getResources().clear();
			menu.getResources().addAll(resources);
			Long newParentId = getLong("parent.id");
			int indexno = getInteger("indexno");
			Menu parent = null;
			if (null != newParentId) {
				parent = entityDao.get(Menu.class, newParentId);
			}
			menuService.move(menu, parent, indexno);
			String iconPath = get("iconPath");
			if(StringUtils.isNotEmpty(iconPath)){
				menu.setIcon(saveIcon(iconPath));
				delete(iconPath);
			}
			if(getBool("removeIcon")){
				menu.setIcon(null);
			}
			entityDao.saveOrUpdate(menu);
		} catch (Exception e) {
			e.printStackTrace();
			return forward(ERROR);
		}
		return redirect("search", "info.save.success");
	}

	/**
	 * 禁用或激活一个或多个模块
	 * 
	 * @return
	 */
	public String activate() {
		Long[] menuIds = getEntityIds(getShortName());
		Boolean enabled = getBoolean("isActivate");
		if (null == enabled) {
			enabled = Boolean.TRUE;
		}
		List<Menu> menus = entityDao.get(Menu.class, menuIds);
		for (Menu menu : menus) {
			menu.setEnabled(enabled);
		}
		entityDao.saveOrUpdate(menus);
		return redirect("search", "info.save.success");
	}

	/**
	 * 打印预览功能列表
	 * 
	 * @return
	 */
	public String preview() {
		OqlBuilder<Menu> query = OqlBuilder.from(Menu.class, "menu");
		populateConditions(query);
		query.orderBy("menu.code asc");
		put("menus", entityDao.search(query));

		query.cleanOrders();
		query.select("max(length(menu.code)/2)");
		List<?> rs = entityDao.search(query);
		put("depth", rs.get(0));
		return forward();
	}

	@Override
	public String info() {
		Long entityId = getEntityId(getShortName());
		if (null == entityId) {
			logger.warn("cannot get paremeter {}Id or {}.id", getShortName(), getShortName());
		}
		Menu menu = (Menu) getModel(getEntityName(), entityId);
		put(getShortName(), menu);
		if (!menu.getResources().isEmpty()) {
			OqlBuilder<Authority> groupQuery = OqlBuilder.from(Authority.class, "auth");
			groupQuery.where("auth.resource in(:resources)", menu.getResources()).select(
					"distinct auth.group");
			put("groups", entityDao.search(groupQuery));
		}
		return forward();
	}

	public String xml() {
		put("resources", entityDao.getAll(Resource.class));
		put("menuProfiles", entityDao.getAll(MenuProfile.class));
		return forward();
	}

	protected PropertyExtractor getPropertyExtractor() {
		return new MenuPropertyExtractor(getTextResource());
	}

	@Override
	protected String getEntityName() {
		return Menu.class.getName();
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
	
	protected Map<String, Object> getParams() {
		return ActionContext.getContext().getParameters();
	}
	
	/**
	 * 保存文件
	 * 
	 * @return
	 * @throws Exception 
	 */
	protected byte[] saveIcon(String iconPath) throws Exception {
		File file = getFileByPath(iconPath);
		FileInputStream fis = new FileInputStream(file);
		long size = file.length();
		System.out.println("size:::::===" + size);
		byte[] data = new byte[(int) size];
		fis.read(data);
		fis.close();
		return data;
	}

	public void exportXML(){
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("root");
		List<Menu> menus = (List<Menu>) entityDao.search(getQueryBuilder().limit(null));
		for(Menu m : menus){
			Element em = root.addElement("menu");
			em.addAttribute("id", m.getId().toString());
			em.addAttribute("code", m.getCode());
			em.addAttribute("name", m.getName());
			em.addAttribute("title", m.getTitle());
			em.addAttribute("entry", m.getEntry());
			em.addAttribute("remark", m.getRemark());
			em.addAttribute("profileId", m.getProfile().getId().toString());
			if(m.getParent() != null){
				em.addAttribute("parentId", m.getParent().getId().toString());
			}
			for(Resource res : m.getResources()){
				Element eres = em.addElement("resource");
				eres.addAttribute("id", res.getId().toString());
				eres.addAttribute("name", res.getName());
				eres.addAttribute("title", res.getTitle());
				eres.addAttribute("remark", res.getRemark());
				eres.addAttribute("scope", res.getScope()+"");
				for(Category c : res.getCategories()){
					Element ec = eres.addElement("category");
					ec.addAttribute("id", c.getId().toString());
				}
			}
		}
		
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setHeader("Content-Disposition", "attachment;filename=menu.xml");
			response.setContentType("application/x-msdownload");
			OutputFormat of = new OutputFormat();
            of.setIndent(true);
            of.setNewlines(true);
			XMLWriter writer = new XMLWriter(getResponse().getOutputStream(), of);
			writer.write(doc);
			writer.close();
		} catch (UnsupportedEncodingException e) {
			logger.error("exportXML error", e);
		} catch (IOException e) {
			logger.error("exportXML error", e);
		}
	}

	public String importXML(){
		return forward();
	}

	private void addMenu(Element emenu, Map<String, Resource> rmap) {
		Menu menu = new MenuBean();
		menu.setCode(emenu.attributeValue("code"));
		menu.setName(emenu.attributeValue("name"));
		menu.setTitle(emenu.attributeValue("title"));
		menu.setRemark(emenu.attributeValue("remark"));
		menu.setProfile(getProfile(emenu.attributeValue("profileId")));
		menu.setEntry(emenu.attributeValue("entry"));
		menuService.getParentMenu(menu);
		menu.setParent(menuService.getParentMenu(menu));
		menu.setResources(new HashSet<Resource>());
		for(Element eres : (List<Element>)emenu.elements("resource")){
			Resource res = rmap.get(eres.attributeValue("name"));
			if(res == null){
				res = new ResourceBean();
				res.setName(eres.attributeValue("name"));
				res.setTitle(eres.attributeValue("title"));
				res.setScope(Integer.parseInt(eres.attributeValue("scope")));
				res.setCategories(new HashSet<Category>());
				for(Element ec : (List<Element>)eres.elements("category")){
					res.getCategories().add(new CategoryBean(Long.parseLong(ec.attributeValue("id"))));
				}
				entityDao.save(res);
				rmap.put(res.getName(), res);
			}
			menu.getResources().add(res);
		}
		entityDao.save(menu);
	}

	private MenuProfile getProfile(String id) {
		MenuProfile profile = new MenuProfileBean();
		profile.setId(Long.parseLong(id));
		return profile;
	}

	public String saveImportXML(){
		File file = getImportFile();
		String text = FileUtil.readFile(file);
		try {
			Document doc = DocumentHelper.parseText(text);
			Element root = doc.getRootElement();
			List<Resource> resources = resourceService.findAll();
			Map<String , Resource> rmap = new HashMap<String, Resource>();
			for(Resource r : resources){
				rmap.put(r.getName(), r);
			}
			for(Element emenu : (List<Element>)root.elements()){
				addMenu(emenu, rmap);
			}
		} catch (DocumentException e) {
			logger.error("importXML error", e);
		}
		return redirect("search");
	}

}
