package com.ele.project.sysmanager.role.pojo;

import java.io.Serializable;
import java.util.Date;

public class RoleDTO implements Serializable {
    private static final long serialVersionUID = -1248693080180835359L;

    private String role_id;//
    private String name;//角色名称
    private String code_name;//角色编码
    private String description;//描述
    private String isadmin;//是否管理员0:是  1：否
    private Date createtime;//创建时间
    private Date modify_time;//修改时间
    private String admin_level;
    private int idx;//顺序号
    private String rs;
    private Date ts;
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsadmin() {
		return isadmin;
	}
	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getModify_time() {
		return modify_time;
	}
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}
	public String getAdmin_level() {
		return admin_level;
	}
	public void setAdmin_level(String admin_level) {
		this.admin_level = admin_level;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getRs() {
		return rs;
	}
	public void setRs(String rs) {
		this.rs = rs;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
    
    
}
