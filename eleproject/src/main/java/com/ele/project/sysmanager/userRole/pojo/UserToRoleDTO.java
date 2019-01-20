package com.ele.project.sysmanager.userRole.pojo;

import java.io.Serializable;

public class UserToRoleDTO implements Serializable{
	private static final long serialVersionUID = -6697548710283393785L;
	private String ur_id;
	private String userid;
	private String role_id;
	public String getUr_id() {
		return ur_id;
	}
	public void setUr_id(String ur_id) {
		this.ur_id = ur_id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	 
}
