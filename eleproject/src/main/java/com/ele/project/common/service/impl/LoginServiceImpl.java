package com.ele.project.common.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ele.project.common.dao.LoginDao;
import com.ele.project.common.service.LoginService;
import com.ele.project.sysmanager.user.pojo.UserDTO;

@Transactional
@Service("loginService")
public class LoginServiceImpl implements LoginService {

	private LoginDao loginDao;
	
	public UserDTO selectUser(UserDTO user) {
		return loginDao.selectUser(user);
	}
	public List<UserDTO> getUserDTOByName(String username){
		return loginDao.getUserDTOByName(username);
	}
}
