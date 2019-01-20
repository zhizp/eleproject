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
	public Map<String,Object> getRoleById(Map<String, Object> params){
		return roleDao.getRoleById(params);
	}
	public int updateRole(Map<String, Object> params) {
		return roleDao.updateRole(params);
	}
	public int deleteRoles(Map<String, Object> params) {
		return roleDao.deleteRoles(params);
	}
	public int getUserRoles(Map<String, Object> params) {
		return roleDao.getUserRoles(params);
	}
	public int deleteUserRoles(Map<String, Object> params) {
		return roleDao.deleteUserRoles(params);
	}
	public int getMenuRoles(Map<String, Object> params) {
		return roleDao.getMenuRoles(params);
	}
	public int deleteMenuRoles(Map<String, Object> params) {
		return roleDao.deleteMenuRoles(params);
	}
	public List<Map<String, Object>> getRoleLists(Map<String, Object> params) {
		return roleDao.getRoleLists(params);
	}
	public String getRoleIdsByUserId(String userid) {
		return roleDao.getRoleIdsByUserId(userid);
	}
}
