package org.beangle.softwareRegister.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.platform.util.FileUtils;
import org.beangle.softwareRegister.service.SecurityCheck;

public class SoftwareRegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public SoftwareRegisterServlet() {
	}

	public void init() {
		boolean con;
		do{
			con = activeSoftWare();
			if(!con){
				Scanner cin = new Scanner(System.in);
				System.out.println("请再次输入软件注册码:");
				String licence = cin.nextLine();
				FileUtils.createAndWriteFile(
							"/WEB-INF/classes/licences/beangle.licence", licence);
			}else{
				break;
			}
		}while(true);
	}
	

	public void doGet(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse) throws ServletException,
			IOException {
	}
	
	public boolean activeSoftWare(){
		boolean isActive = true;
		String licence = "";
		if (FileUtils.existsFile("/WEB-INF/classes/licences/beangle.licence")) {
			List licences = FileUtils
					.getTextFileContent("/WEB-INF/classes/licences/beangle.licence");
			if (licences.size() > 0) {
				licence = licences.get(0).toString();
			}
		} else {
			Scanner cin = new Scanner(System.in);
			System.out.println("请输入软件注册码：");
		    licence = cin.nextLine();
			FileUtils.createAndWriteFile(
					"/WEB-INF/classes/licences/beangle.licence", licence);
		}
		System.out.println("注册码：" + licence);
		SecurityCheck.check();
		if (FileUtils.existsFile("/WEB-INF/licence")) {
			System.out.println("您的注册码不正确，激活失败！");
			isActive = false;
		} else {
			System.out.println("激活成功，感谢您的购买！");
			isActive = true;
		}
		return isActive;
	}

	public void destory() {
		
	}
}
