package com.ele.project.sysmanager.role.dao;

import java.util.List;
import java.util.Map;

public interface RoleDao {

	public List<Map<String,Object>> getRolesList(Map<String, Object> params);
	public int getRolesListCount(Map<String, Object> params) ;
	public int addRole(Map<String, Object> params);
	public Map<String,Object> getRoleById(Map<String, Object> params);
	public int updateRole(Map<String, Object> params) ;
	public int deleteRoles(Map<String, Object> params);
	public int getUserRoles(Map<String, Object> params);
	public int deleteUserRoles(Map<String, Object> params);
	public int getMenuRoles(Map<String, Object> params);
	public int deleteMenuRoles(Map<String, Object> params);
	public List<Map<String, Object>> getRoleLists(Map<String, Object> params);
	public String getRoleIdsByUserId(String userId) ;
}
