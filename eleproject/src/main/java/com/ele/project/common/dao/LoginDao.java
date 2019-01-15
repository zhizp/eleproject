package com.ele.project.common.dao;

import java.util.List;

import com.ele.project.sysmanager.user.pojo.UserDTO;

public interface LoginDao {

	public UserDTO selectUser(UserDTO user);
	public List<UserDTO> getUserDTOByName(String login_name);
}
