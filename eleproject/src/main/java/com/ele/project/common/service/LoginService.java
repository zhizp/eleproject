package com.ele.project.common.service;

import java.util.List;

import com.ele.project.sysmanager.user.pojo.UserDTO;

public interface LoginService {

	public UserDTO selectUser(UserDTO user);
	public List<UserDTO> getUserDTOByName(String username);
}
