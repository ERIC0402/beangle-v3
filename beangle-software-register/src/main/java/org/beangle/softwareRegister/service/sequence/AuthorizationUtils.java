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

package org.beangle.softwareRegister.service.sequence;

import java.util.Date;

import org.beangle.commons.property.PropertyHolder;
import org.beangle.platform.util.DateUtil;
import org.beangle.platform.util.MD5Util;


/**
 * 此工具负责根据用户的机器码来生成注册码
 * RSA加密｛机器码（MD5）+产品编号+版本号+有效期（结束日期）｝
 * MD5+YZ_4位字母+V3.0.0+时间的毫秒值
 * @author zjm
 */
public class AuthorizationUtils {
    private static final int SPLITLENGTH=4;
    public static void main(String args[]) throws Exception {
        String machineCode="C8C6-A708-10DC-D03E-4D76-4A0D-024A-4DCE";
        String productID ="YZ_JCJY";
        String versionNumber ="V3.0.0";
        
        Date validityDate = DateUtil.stringToDate("2015-01-01");
        String validity =String.valueOf(validityDate.getTime());
        
        String authCode=auth(machineCode);//注册码
//      String authCode=auth(machineCode, productID, versionNumber, validity);//注册码
        System.out.println("机器码："+machineCode);
        System.out.println("注册码："+authCode);
    }
    
    public static String auth(String machineCode,String productID,String versionNumber,String validity){
        String newCode=machineCode+productID+versionNumber+validity;
        String code = MD5Util.md5(newCode).toUpperCase();
        return getSplitString(code);
    }
    
    public static String auth(String machineCode){
    	String productID =PropertyHolder.getProperty("productID");
        String versionNumber =PropertyHolder.getProperty("versionNumber");
        Date validityDate = DateUtil.stringToDate(PropertyHolder.getProperty("validityDate"));
        String validity =String.valueOf(validityDate.getTime());
        String newCode=machineCode+productID+versionNumber+validity;
        String code = MD5Util.md5(newCode).toUpperCase();
        return getSplitString(code);
    }
    
    private static String getSplitString(String str){ 
        return getSplitString(str, "-", SPLITLENGTH);
    }
    private static String getSplitString(String str, String split, int length){        
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
}