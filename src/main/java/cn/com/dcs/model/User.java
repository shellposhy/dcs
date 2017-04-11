package cn.com.dcs.model;

import cn.com.dcs.framework.base.BaseEntity;

/**
 * 用户对象
 * 
 * @author shishb
 * @version 1.0
 */
public class User extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String name;
	private String realName;
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
