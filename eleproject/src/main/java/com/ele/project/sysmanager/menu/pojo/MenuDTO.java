package com.ele.project.sysmanager.menu.pojo;

import java.io.Serializable;
import java.util.Date;

public class MenuDTO implements Serializable{

	private static final long serialVersionUID = -1248693080180835359L;
	
	private String menu_id;//
    private String name;//菜单名称
    private String url;//url
    private String parentid;//父级菜单  第一级为0
    private int displayorder;//显示顺序

    private String menuicon;//菜单图标
    private String operator_organization;//操作人组织机构
    private String operator_department;//操作人处室
    private String operator;//操作人
    private Date operate_date;//操作时间
    private Date ts ;//
    private String rs;//
    private String remarks;//
    private Integer idx;//
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public int getDisplayorder() {
		return displayorder;
	}
	public void setDisplayorder(int displayorder) {
		this.displayorder = displayorder;
	}
	public String getMenuicon() {
		return menuicon;
	}
	public void setMenuicon(String menuicon) {
		this.menuicon = menuicon;
	}
	public String getOperator_organization() {
		return operator_organization;
	}
	public void setOperator_organization(String operator_organization) {
		this.operator_organization = operator_organization;
	}
	public String getOperator_department() {
		return operator_department;
	}
	public void setOperator_department(String operator_department) {
		this.operator_department = operator_department;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOperate_date() {
		return operate_date;
	}
	public void setOperate_date(Date operate_date) {
		this.operate_date = operate_date;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public String getRs() {
		return rs;
	}
	public void setRs(String rs) {
		this.rs = rs;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
    
	
}
