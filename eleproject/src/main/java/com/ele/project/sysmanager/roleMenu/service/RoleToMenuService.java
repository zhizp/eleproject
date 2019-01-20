package com.ele.project.sysmanager.roleMenu.service;

import java.util.List;
import java.util.Map;

public interface RoleToMenuService {

	public List<Map<String, Object>> getRoleToMenuList(String role_id);
	public int deleteRoleToMenu(Map<String, Object> params);
	public int addBatchRoleToMenu(List<Map<String, Object>> paramlist);
}
