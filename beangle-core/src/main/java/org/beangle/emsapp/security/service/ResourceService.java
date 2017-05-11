package org.beangle.emsapp.security.service;

import java.util.List;

import org.beangle.ems.security.Resource;

public interface ResourceService {
	
	public List<Resource> findAll();

	public boolean hasResouceName(String name);
	
	public void saveOrUpdateResource(String oldName, String newName, String title, int scope);
	
	public Resource getResourceByName(String name);
}
