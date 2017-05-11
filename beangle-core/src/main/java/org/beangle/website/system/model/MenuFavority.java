package org.beangle.website.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.beangle.ems.security.User;
import org.beangle.ems.security.nav.Menu;
import org.beangle.model.pojo.LongIdObject;
/**
 * 菜单收藏
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.system.model.MenuFavority")
public class MenuFavority extends LongIdObject {

	/**
	 * 序号
	 */
	@Column(name="ORDERS")
	private int order;
	
	/**
	 * 用户
	 */
	private User user;
	/**
	 * 菜单
	 */
	private Menu menu;
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
}
