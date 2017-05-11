package org.beangle.web.filter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

public class StaticFilter implements Filter {
	
	private ServletContext servletContext;
	

	public void init(FilterConfig filterConfig) throws ServletException {
		servletContext = filterConfig.getServletContext();
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		uri = uri.replace(req.getContextPath(), "");
		uri = uri.replaceAll("//", "/");
//		String path = uri.substring(path.indexOf("/", 1));
		File file = new File(servletContext.getRealPath(uri));
		if(!file.exists()){
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
			URL url = Thread.currentThread().getContextClassLoader().getResource(uri.substring(1));
			InputStream in = null;
			OutputStream out = null;
			try {
				in = url.openStream();
				out = new FileOutputStream(file);
				byte[] bufferd = new byte[1024];
				int len = 0;
				while ((len = in.read(bufferd)) > 0) {
					out.write(bufferd, 0, len);
				}
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(out);
			}
		}
		 chain.doFilter(request, response);
	}

	public void destroy() {

	}

}
