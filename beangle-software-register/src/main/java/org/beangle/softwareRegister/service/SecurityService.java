package org.beangle.softwareRegister.service;

/**
 * 登录验证密钥
 * @author zjm
 */
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.property.PropertyHolder;
import org.beangle.platform.util.DateUtil;
import org.beangle.platform.util.MD5Util;
import org.beangle.website.common.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityService {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityService.class);
    
    public void checkSeq(String seq){
        if(StringUtils.isNotBlank(seq)){
        	 LOG.debug("机器码为："+seq);
        	if(!validityDate()){
        		 authFail(seq);
        		 System.out.println("系统已过有效期");
        		 LOG.debug("系统已过有效期");
        	}else{
        		if(valide(seq)){
                    authSuccess();
                    LOG.debug("产品已经取得合法授权");
                }else{
                    LOG.debug("产品没有取得授权");
                    authFail(seq);
                }
        	}
        }else{
            LOG.debug("机器码获取失败");
            LOG.debug("产品没有取得授权");
            authFail(seq);
        }
    }
    private void authSuccess(){
        FileUtil.removeFile(FileUtil.FILE_PATH + "licences/server.licence");
        FileUtil.removeFile(FileUtil.FILE_PATH + "licences/failure.licence");
    }
    private void authFail(String seq){
        FileUtil.createAndWriteFile(FileUtil.FILE_PATH + "licences/server.licence",seq);
        FileUtil.createAndWriteFile(FileUtil.FILE_PATH + "licences/failure.licence",seq);
    }
    
    /**
     * 计算软件注册码
     * @param machineCode
     * @return
     */
    private String auth(String machineCode){
    	//读取系统参数获取产品ID,产品版本号，产品有效期
    	String productID =PropertyHolder.getProperty("productID");
        String versionNumber =PropertyHolder.getProperty("versionNumber");
        Date validityDate = DateUtil.stringToDate(PropertyHolder.getProperty("validityDate"));
        String validity =String.valueOf(validityDate.getTime());
        String newCode=machineCode+productID+versionNumber+validity;
        String code = MD5Util.md5(newCode).toUpperCase();
        return getSplitString(code);
    }
    
    /**
     * 计算软件注册码
     * MD5+YZ_4位字母+V3.0.0+时间的毫秒值
     * @param machineCode
     * @param productID
     * @param versionNumber
     * @param validity
     * @return
     */
    public String auth(String machineCode,String productID,String versionNumber,String validity){
        String newCode=machineCode+productID+versionNumber+validity;
        String code = MD5Util.md5(newCode).toUpperCase();
        return getSplitString(code);
    }
    
    private String getSplitString(String str){ 
        return getSplitString(str, "-", 4);
    }
    private String getSplitString(String str, String split, int length){        
        int len=str.length();
        StringBuilder temp=new StringBuilder();
        for(int i=0;i<len;i++){
            if(i%length==0 && i>0){
                temp.append(split);
            }
            temp.append(str.charAt(i));
        }
        String[] attrs=temp.toString().split(split);
        StringBuilder finalMachineCode=new StringBuilder();
        for(String attr : attrs){
            if(attr.length()==length){
                finalMachineCode.append(attr).append(split);
            }
        }
        String result=finalMachineCode.toString().substring(0, finalMachineCode.toString().length()-1);
        return result;
    }
    
    private boolean valide(String  seq) {
        try{
            String authCode=auth(seq);
            if(StringUtils.isBlank(authCode)){
                return false;
            }
            Collection<String> licences=FileUtil.getTextFileContent(FileUtil.FILE_PATH + "licences/beangle.licence");
            for(String no : licences){
                if(authCode.equals(no)){
                    return true;
                }
            }
        }catch(Exception e){
            LOG.debug("安全检查出错",e);
        }
        return false;
    }
    
    /**
     * 验证有效期
     * @param seq
     * @return
     */
    private boolean validityDate() {
    	Date nowDate = new Date();
    	Date validityDate = null;
    	try {
    		validityDate = DateUtil.stringToDate(PropertyHolder.getProperty("validityDate"));
		} catch (Exception e) {
			return false;
		}
        return nowDate.before(validityDate)?true:false;
    }
}