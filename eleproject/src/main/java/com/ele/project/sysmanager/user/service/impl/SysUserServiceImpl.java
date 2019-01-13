package com.ele.project.sysmanager.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ele.project.sysmanager.user.dao.SysUserDao;
import com.ele.project.sysmanager.user.pojo.UserDTO;
import com.ele.project.sysmanager.user.service.SysUserService;
/**
 * userService 接口的实现类
 * 
 */
@Transactional
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	@Resource
	private SysUserDao sysUserDao;
	public int updatePwd(Map<String, Object> params) {
		return sysUserDao.updatePwd(params);
	}
	
	public List<Map<String, Object>> queryUserList(Map<String, Object> params) {
		return sysUserDao.queryUserList(params);
	}
	public int queryUserListCount(Map<String, Object> params) {
		return sysUserDao.queryUserListCount(params);
	}
	
	public int saveUser(Map<String, Object> params) {
		return sysUserDao.saveUser(params);
	}
}
