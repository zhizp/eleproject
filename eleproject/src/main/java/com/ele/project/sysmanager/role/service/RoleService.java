package com.ele.project.sysmanager.role.service;

import java.util.List;
import java.util.Map;

public interface RoleService {

	public List<Map<String,Object>> getRolesList(Map<String, Object> params);
	public int getRolesListCount(Map<String, Object> params);
	public int addRole(Map<String, Object> params);
}
