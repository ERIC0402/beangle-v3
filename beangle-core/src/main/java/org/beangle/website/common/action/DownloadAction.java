package org.beangle.website.common.action;



/**
 * 将文件上传到指定目录 文件名使用uuid
 * 
 * @author CHII
 */
public class DownloadAction extends FileAction{

	@Override
	public String index() throws Exception {
		download();
		return null;
	}
	
	public String online(){
		return null;
	}
}
