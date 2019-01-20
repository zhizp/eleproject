package com.ele.project.sysmanager.user.service;

import java.util.List;
import java.util.Map;

import com.ele.project.sysmanager.user.pojo.UserDTO;

public interface SysUserService {
	public int updatePwd(Map<String, Object> params);
	public List<Map<String, Object>> queryUserList(Map<String, Object> params);
	public int queryUserListCount(Map<String, Object> params);
	public int saveUser(Map<String, Object> params) ;
	public int updateUser(Map<String, Object> params) ;
	public int deleteUser(Map<String, Object> params) ;
}
