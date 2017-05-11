/**
 * 
 * APDPlat - Application Product Development Platform
 * Copyright (c) 2013, 杨尚川, yang-shangchuan@qq.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package org.beangle.softwareRegister.action;

import java.util.List;

import org.beangle.platform.util.FileUtils;
import org.beangle.platform.util.Struts2Utils;
import org.beangle.softwareRegister.service.SecurityCheck;
import org.beangle.website.common.action.BaseCommonAction;

/**
 *
 * 注册码激活检测
 * @author zjm
 */
public class ActiveAction extends BaseCommonAction{
    private String licence;
    
    
	/**
	 * 主页面
	 * 
	 * @return
	 */
	public String index() throws Exception {
//		String licence ="D413-C471-188E-7824-75D3-56A7-7075-996F";
		 String licence="";
		if(FileUtils.existsFile("/WEB-INF/classes/licences/beangle.licence")){
		   List licences=FileUtils.getTextFileContent("/WEB-INF/classes/licences/beangle.licence");
		   if(licences.size()>0){
			   licence=licences.get(0).toString();
		   }
	   }else{
		  Struts2Utils.renderText("您的注册码文件不存在，激活失败！");
			FileUtils.createAndWriteFile("/WEB-INF/classes/licences/beangle.licence", licence);
	   }
		Struts2Utils.renderText("注册码："+licence+"<br>");
        SecurityCheck.check();
        if(FileUtils.existsFile("/WEB-INF/licence")){
                Struts2Utils.renderText("您的注册码不正确，激活失败！");
        }else{
                Struts2Utils.renderText("激活成功，感谢您的购买！");
        }
		return forward();
	}
	
    public String buy(){
        
        return null;
    }
    public String active(){
        FileUtils.createAndWriteFile("/WEB-INF/classes/licences/beangle.licence", licence);
        SecurityCheck.check();
        if(FileUtils.existsFile("/WEB-INF/licence")){
                Struts2Utils.renderText("您的注册码不正确，激活失败！");
        }else{
                Struts2Utils.renderText("激活成功，感谢您的购买！");
        }
        return null;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }
}