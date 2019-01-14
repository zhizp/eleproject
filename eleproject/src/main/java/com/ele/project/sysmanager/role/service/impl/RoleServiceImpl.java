package com.ele.project.sysmanager.role.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ele.project.sysmanager.role.dao.RoleDao;
import com.ele.project.sysmanager.role.service.RoleService;
@Service("/roleService")
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleDao roleDao;
	
	public List<Map<String,Object>> getRolesList(Map<String, Object> params){
		return roleDao.getRolesList(params);
	}
	public int getRolesListCount(Map<String, Object> params) {
		return roleDao.getRolesListCount(params);
	}
	public int addRole(Map<String, Object> params) {
		return roleDao.addRole(params);
	}
}
