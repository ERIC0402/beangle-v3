/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.property.PropertyConfig;
import org.beangle.commons.property.PropertyConfigEvent;
import org.beangle.commons.property.PropertyConfigListener;
import org.beangle.ems.security.model.SystemConfig;
import org.beangle.ems.security.nav.Menu;
import org.beangle.ems.security.nav.model.MenuBean;
import org.beangle.website.common.util.Base64Util;
import org.beangle.website.common.util.ImageSizer;
import org.beangle.website.common.util.ReturnValue;

import com.opensymphony.xwork2.ActionContext;

/**
 * 将文件上传到指定目录 文件名使用uuid
 * 
 * @author CHII
 */
public class FileAction extends BaseCommonAction implements PropertyConfigListener {

	private static final String UPLOAD_TYPE_CKEDITOR = "ckeditor";
	// 照片路径
	protected static String baseDir;

	public final static String TEMP_DIR = "temp/";
	public final static String KEY_FILE_DIR = "file.dir";

	/**
	 * 下载文件
	 * 
	 * @return
	 */
	public String download() {
		String filePath = get("file");
		if (StringUtils.isEmpty(filePath)) {
			filePath = get("folder");
		}
		if (StringUtils.isEmpty(filePath)) {
			filePath = get("default");
		}
		outPutFile(filePath);
		return null;
	}

	protected void setBaseDir(String baseDir) {
		//防止被空替换
		if(StringUtils.isEmpty(baseDir)){
			return;
		}
		if (baseDir != null) {
			baseDir = baseDir.replaceAll("\\\\", "/");
			if (!baseDir.endsWith("/")) {
				baseDir += "/";
			}
		}
		FileAction.baseDir = baseDir;
	}

	protected String getBaseDir() {
		if (null == baseDir) {
			PropertyConfig config = getSystemConfig();
			String dir = config.get(String.class, FileAction.KEY_FILE_DIR);
			dir = dir.replaceAll("\\", "/");
			if(!dir.endsWith("/")){
				dir += "/";
			}
			setBaseDir(dir);
		}
		return baseDir;
	}

	protected void getFile(String filePath, Boolean isShowImage) {
		outPutFile(filePath);
	}

	/**
	 * 根据文件路径获取文件
	 * 
	 * @param filePath
	 */
	public void outPutFile(String filePath) {
		String fileName = get("fileName");
		if (StringUtils.isEmpty(fileName)) {
			int start = filePath.lastIndexOf("/") + 1;
			int end = filePath.length();
			fileName = filePath.substring(start, end);
		}
		outPutFile(filePath, fileName);
	}

	public void outPutFile(String filePath, String fileName) {
		outPutFile(filePath, fileName, null);
	}
	public void outPutFile(String filePath, String fileName, String contentType) {
		if (StringUtils.isEmpty(filePath)) {
			return;
		}
		if (filePath.indexOf("../") >= 0) {
			return;
		}
		StringBuilder sb = new StringBuilder(filePath);
		if ('/' == sb.charAt(0)) {
			sb.deleteCharAt(0);
		}

		File file = new File(getBaseDir() + sb.toString());

		InputStream in = null;
		if (!file.exists()) {
			URL url = Thread.currentThread().getContextClassLoader().getResource(sb.toString());
			if (url == null) {
				String noimg = get("default");
				if(StringUtils.isEmpty(noimg)){
					noimg = "static/img/noimg.gif";
				}
				fileName = "noimg.gif";
				contentType = "image/jpeg";
				url = this.getClass().getClassLoader().getResource(noimg);
			}
			if (url != null) {
				file = new File(url.getFile());
				try {
					in = url.openStream();
				} catch (IOException e) {
				}
			}
		} else {
			try {
				in = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		outPutFile(in, fileName, contentType, file);
	}

	public void outPutFile(InputStream in, String fileName, String contentType) {
		outPutFile(in, fileName, contentType, null);
	}

	public void outPutFile(InputStream in, String fileName, String contentType, File file) {
		if (in == null) {
			return;
		}
		HttpServletResponse res = ServletActionContext.getResponse();
		OutputStream out = null;
		try {
			res.setDateHeader("Expires", System.currentTimeMillis() + 3*60*60*1000L);
			if (file != null && file.exists()) {
				res.setHeader("Content-Length", file.length() + "");
			}
			if (contentType == null && fileName != null) {
				if (fileName.endsWith(".flv") || fileName.endsWith(".swf")) {
					contentType = "application/x-shockwave-flash";
				} else {
					if(fileName.endsWith(".mp4")){
						contentType = "video/mp4";
					}else if(fileName.endsWith(".pdf")){
						contentType = "application/pdf";
					}else{
						String imgtypes = "jpg,jpeg,gif,png,bmp";
						String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
						if (imgtypes.indexOf(ext) >= 0) {
							contentType = "image/jpeg";
						} else {
							contentType = "application/zip";
						}
					}
				}
			}
			res.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
			res.setContentType(contentType);

			out = res.getOutputStream();
			byte[] bufferd = new byte[1024];
			int len = 0;
			while ((len = in.read(bufferd)) > 0) {
				out.write(bufferd, 0, len);
			}
			out.flush();
		} catch (IOException e) {
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * 根据菜单id显示图标
	 * 
	 * @return
	 * @throws IOException
	 */
	public void downIconById() throws IOException {
		Long menuId = this.getLong("menuId");
		Menu menu = entityDao.get(MenuBean.class, menuId);
		if (menu != null) {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("image/jpeg");
			response.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode("图标", "UTF-8") + "\"");
			ServletOutputStream sos = response.getOutputStream();
			byte[] content = menu.getIcon();
			sos.write(content);
			sos.close();
		}
	}
	
	/**
	 * 根据系统配置id显示图标
	 * 
	 * @return
	 * @throws IOException
	 */
	public void downIconBySCId() throws IOException {
		Long scId = this.getLong("scId");
		SystemConfig sc = entityDao.get(SystemConfig.class, scId);
		if (sc != null) {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("image/jpeg");
			response.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode("图标", "UTF-8") + "\"");
			ServletOutputStream sos = response.getOutputStream();
			byte[] content = sc.getHeadImage();
			sos.write(content);
			sos.close();
		}
	}

	/**
	 * 根据相对路径获取文件
	 * 
	 * @param path
	 * @return
	 */
	public File getFileByPath(String path) {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		File file = new File(getBaseDir() + path);
		if (file.exists() && !file.isDirectory()) {
			return file;
		}
		return null;
	}

	public void onConfigEvent(PropertyConfigEvent event) {
		setBaseDir(event.getSource().get(String.class, FileAction.KEY_FILE_DIR));
	}

	protected Map<String, Object> getParams() {
		return ActionContext.getContext().getParameters();
	}

	protected File getFile() {
		String pagePara = "fileData";
		File[] files = (java.io.File[]) this.getParams().get(pagePara);
		if (files != null && files.length == 1) {
			return files[0];
		}
		return null;
	}

	protected File getFile(String name) {
		File[] files = (java.io.File[]) this.getParams().get(name);
		if (files != null && files.length == 1) {
			return files[0];
		}
		return null;
	}

	protected File getImportFile() {
		return getFile("importFile");
	}

	protected String getFileName() {
		String pagePara = "fileData";
		String[] fileNames = (String[]) this.getParams().get(pagePara + "FileName");
		if (fileNames != null && fileNames.length == 1) {
			return fileNames[0];
		}
		return null;
	}

	/**
	 * 保存文件
	 * 
	 * @return
	 * @throws Exception 
	 */
	protected ReturnValue saveFile() throws Exception {
		ReturnValue value = new ReturnValue();
		String type = get("type");
		String pagePara = "fileData";
		if (UPLOAD_TYPE_CKEDITOR.equals(type)) {
			pagePara = "upload";
		}
		File file = get(pagePara, File.class);
		String fileName = get(pagePara + "FileName");
		if (file == null || fileName == null) {
			return value;
		}

		// 是否压缩图片，如果为false可支持非图片格式保存
		Boolean zipImg = getBool("zipImg");
		if (zipImg != null && zipImg) {
			Integer width = getInteger("width");
			if (width == null) {
				width = 600;
			}
			ImageSizer.saveImg(file.getAbsolutePath(), file.getAbsolutePath(), width, 800);
		}
		// 保存文件
		String folder = get("folder");
		if (StringUtils.isEmpty(folder)) {
			folder = "files/";
		}
		if (folder.startsWith("/")) {
			folder = folder.substring(1);
		}
		if (!folder.endsWith("/")) {
			folder = folder + "/";
		}
		StringBuilder sb = new StringBuilder(TEMP_DIR + folder);
		sb.append(UUID.randomUUID().toString());
		sb.append(fileName.substring(fileName.lastIndexOf(".")).toLowerCase());

		File newFile = new File(getBaseDir() + sb.toString());
		if (!newFile.getParentFile().exists()) {
			newFile.getParentFile().mkdirs();
		}

		//renameTo在Linux系统下不能移动文件
//		file.renameTo(newFile);
		FileUtils.moveFile(file, newFile);
		if (UPLOAD_TYPE_CKEDITOR.equals(type)) {
			String newPath = moveAndRemoveAnnex(sb.toString(), null);
			sb = new StringBuilder(newPath);
		}
		value.setFileName(fileName);
		value.setFilePath(sb.toString());
		value.setFileSize(newFile.length());
		return value;
	}

	/**
	 * 上传图片返回值
	 * 
	 * @throws IOException
	 */
	public String upload() throws Exception {
		ReturnValue value = saveFile();
		JSONObject data = JSONObject.fromObject(value);
		data.put("img", value.getFilePath());
		data.put("name", value.getFileName());
		String type = get("type");
		if (UPLOAD_TYPE_CKEDITOR.equals(type)) {
			HttpServletRequest request = getRequest();
			String CKEditorFuncNum = request.getParameter("CKEditorFuncNum");
			put("CKEditorFuncNum", CKEditorFuncNum);
			put("filePath", value.getFilePath());
			return forward("ckeditor");
		} else {
			writeResponseMessage(data.toString());
		}
		return null;
	}
	
	public String uploadBase64(){
		String base64 = get("base64");
		try {
			byte[] bytes = Base64Util.base64Str2Img(base64, null);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/");
			String filePath = sdf.format(new Date()) + UUID.randomUUID()  + ".jpg";
			File file = new File(getBaseDir() + filePath);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bytes);
			IOUtils.closeQuietly(fos);
			JSONObject data = new JSONObject();
			data.put("filePath", filePath);
			writeResponseMessage(data.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 上传文件返回值
	 * 
	 * @throws IOException
	 */
	public void uploadFile() throws Exception {
		ReturnValue value = saveFile();
		JSONObject data = new JSONObject();
		data.put("filePath", value.getFilePath());
		data.put("fileName", value.getFileName());
		data.put("fileSize", value.getFileSize());
		writeResponseMessage(data.toString());
	}

	/**
	 * 返回信息
	 * 
	 * @param msg
	 * @throws IOException
	 */
	protected void writeResponseMessage(String msg) throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(msg);
	}

	/**
	 * 移动临时文件和删除旧文件
	 * 
	 * @param tempFileName
	 *            临时文件
	 * @param oldFileName
	 *            旧文件
	 * @throws IOException 
	 */
	public String moveAndRemoveAnnex(String tempFileName, String oldFileName) {
		if (StringUtils.isEmpty(tempFileName)) {
			return null;
		}
		if (tempFileName.equals(oldFileName)) {
			return tempFileName;
		}
		String newFileName = tempFileName;
		if (tempFileName.startsWith(TEMP_DIR)) {
			newFileName = tempFileName.substring(TEMP_DIR.length());
			File newFile = new File(getBaseDir() + newFileName);
			if (!newFile.getParentFile().exists()) {
				newFile.getParentFile().mkdirs();
			}
			File tempFile = new File(getBaseDir() + tempFileName);
			if (tempFile.exists()) {
//				renameTo在Linux系统下不能移动文件
//				tempFile.renameTo(newFile);
				try {
					FileUtils.moveFile(tempFile, newFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 删除旧文件
		delete(oldFileName);
		return newFileName;
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 *            文件路径
	 */
	public void delete(String path) {
		if (StringUtils.isEmpty(path)) {
			return;
		}
		File file = new File(getBaseDir() + path);
		if (file.exists() && !file.isDirectory()) {
			file.delete();
		}
	}

	/**
	 * 0根据文件id下载文件(内部通讯)
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public void downFile() throws UnsupportedEncodingException {
		download();
	}

	public String getMinImg(String path) {
		File file = new File(ServletActionContext.getServletContext().getRealPath(path));
		if (!file.exists()) {
			return null;
		}
		StringBuilder toFileName = new StringBuilder(path);
		toFileName.insert(toFileName.lastIndexOf("."), "_min");
		File toFile = new File(ServletActionContext.getServletContext().getRealPath(toFileName.toString()));
		ImageSizer.saveImg(file.getAbsolutePath(), toFile.getAbsolutePath(), 100, 100);
		return toFileName.toString();
	}

	public void setPropertyConfig(PropertyConfig config) {
		if (null != config) {
			config.addConfigListener(this);
			setBaseDir(config.get(String.class, KEY_FILE_DIR));
		}
	}

	public String getFilePath(String pic) {
		return getBaseDir() + pic;
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
}
