package org.beangle.emsapp.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefererFilter implements Filter {
	
	private static final Logger log = LoggerFactory.getLogger(RefererFilter.class);

	private String[] serverNames = new String[] { "localhost" };

	public void init(FilterConfig filterConfig) throws ServletException {
		String sns = filterConfig.getInitParameter("serverNames");
		if(StringUtils.isNotEmpty(sns)){
			serverNames = sns.split(",");
		}
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String referer = request.getHeader("Referer");
		if (referer != null) {
			String serverName = null;
			if(referer.indexOf("http://") == 0){
				serverName = referer.substring("http://".length());
			}else{
				serverName = referer.substring("https://".length());
			}
			serverName = serverName.substring(0, serverName.indexOf("/"));
			if (serverName.indexOf(":") > 0) {
				serverName = serverName.substring(0, serverName.indexOf(":"));
			}
			boolean safe = false;
			for (String s : serverNames) {
				if (s.equals(serverName)) {
					safe = true;
				}
			}
			if(!safe){
				String method = request.getParameter("method");
				if(StringUtils.isEmpty(method)){
					int i = request.getRequestURI().indexOf("!");
					if( i > 0){
						method = request.getRequestURI().substring(i+1);
						method = method.substring(0, method.indexOf(".action"));
					}
				}
				if(StringUtils.isBlank(method) || "index".equals(method)){
					safe = true;
				}
			}
			if (!safe) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				log.info("{} is not safe on serverName:{}", request.getRequestURI(), serverName);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}
