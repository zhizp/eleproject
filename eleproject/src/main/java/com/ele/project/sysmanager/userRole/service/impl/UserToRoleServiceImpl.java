package com.ele.project.sysmanager.userRole.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ele.project.sysmanager.userRole.dao.UserToRoleDao;
import com.ele.project.sysmanager.userRole.pojo.UserToRoleDTO;
import com.ele.project.sysmanager.userRole.service.UserToRoleService;

@Service("/userToRoleService")
public class UserToRoleServiceImpl implements UserToRoleService {
	
	@Resource
	private UserToRoleDao userToRoleDao;
	
	public List<Map<String, Object>> getRoleById(String userid){
		return userToRoleDao.getRoleById(userid);
	}
	
	public int addUserToRole(UserToRoleDTO uRole) {
		return userToRoleDao.addUserToRole(uRole);
	}
	
	public int deleteUserToRole(String userid) {
		return userToRoleDao.deleteUserToRole(userid);
	}
	public List<Map<String, Object>> selectUserToRole(String userid){
		return userToRoleDao.selectUserToRole(userid);
	}

}
