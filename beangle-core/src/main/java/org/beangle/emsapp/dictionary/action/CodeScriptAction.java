/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.dictionary.action;

import java.util.Date;

import org.beangle.ems.dictionary.model.CodeScript;
import org.beangle.ems.dictionary.service.CodeFixture;
import org.beangle.ems.dictionary.service.CodeGenerator;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.entity.Model;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.convention.route.Action;
import org.beangle.struts2.helper.Params;

/**
 * 代码生成脚本
 * 
 * @author beangle
 * @version $Id: CodeScriptAction.java Jun 29, 2011 5:17:46 PM beangle $
 */
public class CodeScriptAction extends SecurityActionSupport {

	private static final long serialVersionUID = 5763797691688360592L;

	private CodeGenerator codeGenerator;

	/**
	 * 主页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return @
	 */
	public String index() {
		return forward();
	}

	/**
	 * 搜索
	 */
	public String search() {
		OqlBuilder<CodeScript> builder = OqlBuilder.from(CodeScript.class, "codeScript")
				.limit(getPageLimit()).orderBy(get("orderBy"));
		populateConditions(builder);
		put("codeScripts", entityDao.search(builder));
		return forward();
	}

	/**
	 * 编辑
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return @
	 */
	public String edit() {
		Long codeScriptId = getLong("codeScriptId");
		CodeScript codeScript = null;
		if (null == codeScriptId) {
			codeScript = (CodeScript) populate(CodeScript.class, "codeScript");
		} else {
			codeScript = (CodeScript) entityDao.get(CodeScript.class, codeScriptId);
		}
		put("codeScript", codeScript);
		return forward();
	}

	/**
	 * 查看
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return @
	 */
	public String info() {
		Long codeScriptId = getLong("codeScriptId");
		CodeScript codeScript = (CodeScript) entityDao.get(CodeScript.class, codeScriptId);
		put("codeScript", codeScript);
		return forward();
	}

	/**
	 * 保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return @
	 */
	public String save() {
		Long codeScriptId = getLong("codeScript.id");
		CodeScript codeScript = null;
		if (null == codeScriptId) {
			codeScript = new CodeScript();
			codeScript.setCreatedAt(new Date(System.currentTimeMillis()));
		} else {
			codeScript = (CodeScript) entityDao.get(CodeScript.class, codeScriptId);
		}
		codeScript.setUpdatedAt(new Date(System.currentTimeMillis()));
		Model.populate(Params.sub("codeScript"), codeScript);
		entityDao.saveOrUpdate(codeScript);
		return redirect("search", "info.save.success");
	}

	/**
	 * 保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return @
	 */
	public String test() {
		CodeFixture codeFixture = new CodeFixture(Params.sub("codeFixture"));
		String testResult = codeGenerator.test(codeFixture);
		if (null == testResult) {
			testResult = "null";
		}
		put("testResult", testResult);
		return forward(new Action(this.getClass(), "edit"));
	}

	/**
	 * 删除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return @
	 */
	public String remove() {
		Long codeScriptId = getLong("codeScriptId");
		entityDao.remove(entityDao.get(CodeScript.class, codeScriptId));
		return redirect("search", "info.delete.success");
	}

	public void setCodeGenerator(CodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}

}
