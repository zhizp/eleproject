package com.ele.project.sysmanager.roleMenu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ele.project.sysmanager.roleMenu.dao.RoleToMenuDao;
import com.ele.project.sysmanager.roleMenu.service.RoleToMenuService;

@Service("/roleToMenuService")
public class RoleToMenuServiceImpl implements RoleToMenuService {
	@Resource
	private RoleToMenuDao roleToMenuDao;
	
	public List<Map<String, Object>> getRoleToMenuList(String role_id) {
		return roleToMenuDao.getRoleToMenuList(role_id);
	}
	public int deleteRoleToMenu(Map<String, Object> params) {
		return roleToMenuDao.deleteRoleToMenu(params);
	}

	public int addBatchRoleToMenu(List<Map<String, Object>> paramlist) {
		return roleToMenuDao.addBatchRoleToMenu(paramlist);
	}
}
