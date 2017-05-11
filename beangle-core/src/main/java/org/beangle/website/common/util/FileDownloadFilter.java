package org.beangle.website.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class FileDownloadFilter implements Filter {

	private String basePath = "/file";
	private String dir = "/tmp";
	private String imgs = "gif,jpg,jpeg,png,bmp";
	private String exclude = "class, ftl";

	public String getDir() {
		return dir;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String basePath = filterConfig.getInitParameter("basePath");
		if (basePath != null) {
			this.basePath = basePath;
		}
		String dir = filterConfig.getInitParameter("dir");
		if (dir != null) {
			this.dir = dir;
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getRequestURI();
		String filePath = path.substring(basePath.length());
		path = getDir() + filePath;
		File file = new File(path);
		InputStream in = null;
		boolean isImg = isImg(file);
		String fileName = "";
		if (file.exists()) {
			fileName = file.getName();
			in = new FileInputStream(file);
		} else {
			if(isexclude(filePath)){
				return;
			}
			in =  this.getClass().getClassLoader().getResourceAsStream(filePath);
			if(in == null){
				if (isImg) {
					fileName = "noimg.gif";
					in = this.getClass().getClassLoader().getResourceAsStream("static/img/noimg.gif");
				} else {
					return;
				}
			}
		}
		OutputStream out = null;
		try {
			HttpServletResponse res = (HttpServletResponse) response;
			String contextType = null;
			if (path.endsWith(".flv") || path.endsWith(".swf")) {
				contextType = "application/x-shockwave-flash";
			} else {
				if (isImg) {
					contextType = "image/jpeg";
				} else {
					contextType = "application/zip";
				}
			}
			res.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
			res.setContentType(contextType);
			out = response.getOutputStream();
			byte[] bufferd = new byte[1024];
			int len = 0;

			while ((len = in.read(bufferd)) > 0) {
				out.write(bufferd, 0, len);
			}
		} catch (IOException e) {
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
		return;
	}

	private boolean isexclude(String filePath) {
		String ext = filePath.substring(filePath.lastIndexOf(".") + 1);
		ext = ext.toLowerCase();
		if (exclude.indexOf(ext) >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据扩展名判断是不是图片
	 * @param file
	 * @return
	 */
	private boolean isImg(File file) {
		String ext = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1);
		ext = ext.toLowerCase();
		if (imgs.indexOf(ext) >= 0) {
			return true;
		}
		return false;
	}

	public void destroy() {
	}

}
