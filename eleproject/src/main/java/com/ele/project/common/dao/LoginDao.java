package com.ele.project.common.dao;

import java.util.List;

import com.ele.project.sysmanager.user.pojo.UserDTO;

public interface LoginDao {

//	boolean checkPwdByUser(String username,String password);
	public UserDTO selectUser(UserDTO user);
//	String selectMenuState(UserDTO user);
	public List<UserDTO> getUserDTOByName(String username);
}
