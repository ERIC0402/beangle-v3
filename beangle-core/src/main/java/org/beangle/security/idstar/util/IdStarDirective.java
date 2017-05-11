package org.beangle.security.idstar.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.beangle.web.url.UrlBuilder;

import com.wiscom.is.IdentityManager;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class IdStarDirective implements TemplateDirectiveModel {

	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		IdentityManager im = IdstarUtils.getIdentityManager();
		String gotoURL = params.get("goto").toString();
		String loginURL = UrlBuilder.getFullPath("/login.action");
		StringBuilder sb = new StringBuilder(loginURL);
		sb.append("?backurl=").append(gotoURL);
		gotoURL = URLEncoder.encode(sb.toString(), "UTF-8");
		loginURL = im.getLoginURL() + "?goto=" + gotoURL;
		env.setVariable("loginURL", ObjectWrapper.DEFAULT_WRAPPER.wrap(loginURL));
		body.render(env.getOut());
	}

}
