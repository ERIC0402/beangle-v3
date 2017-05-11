package org.beangle.emsapp.security.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.model.ResourceBean;
import org.beangle.emsapp.security.service.ResourceService;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;

public class ResourceServiceImpl implements ResourceService {
	
	private EntityDao entityDao;
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public List<Resource> findAll() {
		OqlBuilder<Resource> oql = OqlBuilder.from(Resource.class, "r");
		return entityDao.search(oql);
	}

	public boolean hasResouceName(String name) {
		long count = entityDao.count(Resource.class, "name", name);
		if(count > 0){
			return true;
		}
		return false;
	}

	public void saveOrUpdateResource(String oldName, String newName, String title, int scope) {
		Resource r = getResourceByName(oldName);
		if(r == null){
			r = new ResourceBean();
		}
		r.setName(newName);
		r.setTitle(title);
		r.setScope(scope);
		entityDao.saveOrUpdate(r);
	}
	
	public Resource getResourceByName(String name){
		OqlBuilder<Resource> query = OqlBuilder.from(Resource.class, "r");
		query.where("r.name=:name",name);
		List<Resource> resources = entityDao.search(query);
		if(CollectionUtils.isNotEmpty(resources)){
			return resources.get(0);
		}
		return null;
	}

}
