package com.ele.project.sysmanager.role.dao;

import java.util.List;
import java.util.Map;

public interface RoleDao {

	public List<Map<String,Object>> getRolesList(Map<String, Object> params);
	public int getRolesListCount(Map<String, Object> params) ;
	public int addRole(Map<String, Object> params);
}
