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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import org.beangle.commons.property.PropertyHolder;
import org.beangle.platform.util.ConvertUtils;
import org.beangle.platform.util.FileUtils;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *机器码生成的通用服务
 * @author zjm
 */
public abstract class AbstractSequenceService   implements SequenceService{
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractSequenceService.class);

    /**
     * 对一段String生成MD5摘要信息
     * @param message 要摘要的String
     * @return 生成的MD5摘要信息
     */
    protected String getMD5(String message) {
        message += "{apdplat}";
        try {
        	 try {
                 MessageDigest md = MessageDigest.getInstance("MD5");
                 LOG.debug("MD5摘要长度：" + md.getDigestLength());
                 byte[] b = md.digest(message.getBytes("utf-8"));
                 String md5 = ConvertUtils.byte2HexString(b)+message.length();
                 return getSplitString(md5);
             } catch (NoSuchAlgorithmException  e) {
                 LOG.error("MD5摘要失败",e);
             }
		} catch (UnsupportedEncodingException e) {
			  LOG.error("MD5摘要失败",e);
		}
       
        return null;
    }
    /**
     * 将很长的字符串以固定的位数分割开，以便于人类阅读
     * @param str
     * @return 
     */
    protected String getSplitString(String str){ 
        return getSplitString(str, "-", 4);
    }
    /**
     * 将很长的字符串以固定的位数分割开，以便于人类阅读
     * 如将
     * 71F5DA7F495E7F706D47F3E63DC6349A
     * 以-，每4个一组，则分割为
     * 71F5-DA7F-495E-7F70-6D47-F3E6-3DC6-349A
     * @param str 字符串
     * @param split 分隔符
     * @param length 长度
     * @return 
     */
    protected String getSplitString(String str, String split, int length){        
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
    /**
     * 利用sigar来生成机器码，当然这个实现不是很好，无法获得CPU ID，希望有兴趣的朋友来改进这个实现
     * @param osName 操作系统类型
     * @return 机器码
     */
    protected String getSigarSequence(String osName) {
        try {
        	//URL url = AbstractSequenceService.class.getClassLoader().getResource("/libsigar/"+PropertyHolder.getProperty("libsigar."+osName));//this.getClass().getClassLoader().getResource("/");
        	String fileName = PropertyHolder.getProperty("libsigar."+osName);
        	InputStream inputStream = AbstractSequenceService.class.getClassLoader().getResourceAsStream("libsigar/"+fileName);//this.getClass().getClassLoader().getResource("/");
        	//System.out.println("url:"+inputStream);
        	//FileUtil.readFile(inputStream);
        	//File libFile = new File(inputStream);
            //File libFile = new File(FileUtils.getAbsolutePath("/WEB-INF/lib/"+PropertyHolder.getProperty("libsigar."+osName)));
            //LOG.debug("libsigar."+osName+" : "+libFile.getAbsolutePath());
            String tempDir = AbstractSequenceService.class.getClassLoader().getResource("").getPath();//FileUtils.getAbsolutePath("/WEB-INF/temp/");
            BufferedInputStream reader = null;
            FileOutputStream writer = null;
            System.out.println(tempDir);
            System.out.println(AbstractSequenceService.class.getClassLoader().getResource(""));
            File file = new File(tempDir + fileName);
            if(!file.exists()){
            	try{
            		//file.createNewFile();
            		FileUtils.copyFile(inputStream, file);
//            		reader = new BufferedInputStream(inputStream);
//            		writer = new FileOutputStream(file);
//            		byte[] buffer = new byte[1024];
//            		while(reader.read(buffer) > 0){
//            			writer.write(buffer);
//            			buffer = new byte[1024];
//            		}
            	}catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(inputStream != null){
						inputStream.close();
					}
					if(writer != null){
						writer.close();
					}
				}
            }
            
            System.load(file.toString());
            Set<String> result = new HashSet();
            Sigar sigar = new Sigar();
            String[] ifaces = sigar.getNetInterfaceList();
            for (String iface : ifaces) {
                NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(iface);
                if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())  
                        || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0  
                        || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {  
                    continue;  
                }
                String mac = cfg.getHwaddr();
                result.add(mac);
                LOG.debug("mac: " + mac);
                break;
            }
            if(result.size()<1){
                return null;
            }
            
            // 取当前操作系统的信息  
            OperatingSystem OS = OperatingSystem.getInstance();  
            // 操作系统内核类型如： 386、486、586等x86  
            result.add(OS.getArch());
            result.add(OS.getCpuEndian());
            result.add(OS.getDataModel());
            result.add(OS.getVersion());
            

            LOG.debug("result:    " + result);
            String machineCode = getMD5(result.toString());

            return machineCode;
        } catch (Throwable ex) {
            LOG.error("生成 "+osName+" 平台下的机器码失败", ex);
        }
        return null;
    }
}