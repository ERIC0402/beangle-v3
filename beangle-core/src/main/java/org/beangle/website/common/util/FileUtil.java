package org.beangle.website.common.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.beangle.platform.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作类
 * @author XMAN
 *
 */
public class FileUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);
	
    public static final String FILE_PATH = FileUtil.class.getClassLoader().getResource("").getPath();

    public static String readFile(File file) {
    	FileInputStream fis = null;
        try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
		}
        return readFile(fis);
    }


    public static String readFile(InputStream fis) {
		if(fis == null){
			return "";
		}
		StringBuffer content = null;
        BufferedReader reader = null;
        DataInputStream in = null;
        try {
            content = new StringBuffer();
            in = new DataInputStream(fis);
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    in.close();
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
        return content.toString();
	}


	public static void writeFile(String path, String content) throws IOException {
		writeFile(new File(path), content);
	}

	public static void writeFile(File file, String content) throws IOException {
		writeFile(file.getParent(), file.getName(), content);
	}
	
    public static void writeFile(String path, String name, String content) throws IOException {
        File file = new File(path + "/" + name);
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        if (file.exists()) {
        	file.delete();
        }
        file.createNewFile();
        // true 是添加到文件末尾
        FileOutputStream fos = new FileOutputStream(file, false);
        Writer out = new OutputStreamWriter(fos, "UTF-8");

        try {
            out.write(content);
        } finally {
            out.close();
            fos.close();
        }
    }

    public static void outputStream(String path, byte[] content) throws IOException {

        DataOutputStream out;

        out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
        try {
            out.write(content);


        } finally {
            out.close();

        }

    }

    /**
     * 获得WebApp路径
     * @return
     */
    public static String getWebAppPath() {
        return ServletActionContext.getServletContext().getRealPath(".");
    }
    
    /**
     * 获得类的包路径
     * @param clazz
     * @return
     */
    public static String  getClassDir(Class<?> clazz){
    	String dir = clazz.getPackage().getName();
    	dir = dir.replaceAll("\\.", "/");
    	return dir;
    }
    
    /**
     * 获得控制器的页面路径
     * @param clazz
     * @return
     */
    public static String  getActionDir(Class<?> clazz){
    	StringBuilder dir = new StringBuilder(clazz.getName().replaceAll("\\.", "/"));
    	String className = dir.substring(dir.lastIndexOf("/")+1);
    	dir.delete(dir.length() - className.length(), dir.length());
    	dir.append(className.substring(0,1).toLowerCase());
    	dir.append(className.substring(1, className.length()-6));
    	return dir.toString();
    }
    
    public static String getMD5(String fileName) {
        InputStream fis = null;
        MessageDigest md5 = null;
        try {
            fis = new FileInputStream(fileName);
            md5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int numRead = 0;
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
        } catch (Exception e) {
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
            }
        }
        return toHexString(md5.digest());
    }
    
    public static String getMD5(File file) {
        InputStream fis = null;
        MessageDigest md5 = null;
        try {
            fis = new FileInputStream(file);
            md5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int numRead = 0;
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
        } catch (Exception e) {
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
            }
        }
        return toHexString(md5.digest());
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[((b[i] & 0xF0) >>> 4)]);
            sb.append(hexChar[(b[i] & 0xF)]);
        }
        return sb.toString();
    }
    
    private static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    
    /**
     * 判断一个文件是否存在
     * @param path 相对路径或绝对路径
     * @return 是否存在
     */
    public static boolean existsFile(String path){
        try{
            File file=new File(path);
            if(file.exists()){
                return true;
            }
        }catch(Exception ex){
            LOG.error("文件操作失败",ex);
        }
        return false;
    }
    
    /**
     * 获取文本内容
     * @param path 相对路径或绝对路径
     * @return 行的列表
     */
    public static List<String> getTextFileContent(String path) {
        try {
            return FileUtils.getTextFileContent(new FileInputStream(path));
        } catch (FileNotFoundException ex) {
            LOG.error("文件不存在", ex);
        }
        //Null Object设计模式
        return Collections.emptyList();
    }

    /**
     * 删除文件
     * @param path 相对路径或绝对路径
     * @return 是否成功
     */
    public static boolean removeFile(String path){
        try{
            File file=new File(path);
            if(file.exists()){
                if(!file.delete()){
                    file.deleteOnExit();
                }
            }
            return true;
        }catch(Exception ex){
            LOG.error("文件操作失败",ex);
        }
        return false;
    }
    
    /**
     * 把文本内容写入一个新文件
     * 如果文件已经存在
     * 则覆盖
     * @param path 相对路径或绝对路径
     * @param text 文本
     * @return 文件
     */
    public static File createAndWriteFile(String path, String text){
        try{
            File file=new File(path);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            
            // true 是添加到文件末尾
            FileOutputStream fos = new FileOutputStream(file, false);
            Writer out = new OutputStreamWriter(fos, "UTF-8");

            try {
                out.write(text);
            } finally {
                out.close();
                fos.close();
            }
            
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
//            writer.write(text);
            return file;
        }catch(Exception ex){
            LOG.error("文件操作失败",ex);
        }
        return null;
    }
}
