package com.ele.project.sysmanager.user.dao;

import java.util.List;
import java.util.Map;

import com.ele.project.sysmanager.user.pojo.UserDTO;
/**
 * User类Dao层接口
 */
public interface SysUserDao {
	public int updatePwd(Map<String, Object> params);
	public List<Map<String, Object>> queryUserList(Map<String, Object> params);
	public int queryUserListCount(Map<String, Object> params);
	public int saveUser(Map<String, Object> params) ;
}
