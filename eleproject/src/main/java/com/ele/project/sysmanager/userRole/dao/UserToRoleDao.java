package com.ele.project.sysmanager.userRole.dao;

import java.util.List;
import java.util.Map;

import com.ele.project.sysmanager.userRole.pojo.UserToRoleDTO;

public interface UserToRoleDao {
	public List<Map<String, Object>> getRoleById(String userid);
	public int addUserToRole(UserToRoleDTO uRole);
	public int deleteUserToRole(String userid);
	public List<Map<String, Object>> selectUserToRole(String userid);
}
