package com.ele.project.sysmanager.menu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ele.project.sysmanager.menu.dao.MenuDao;
import com.ele.project.sysmanager.menu.service.IMenuService;
@Transactional
@Service("menuService")
public class MenuService implements IMenuService {
	@Autowired
    private MenuDao menuDao;
	
	public List<Map<String, Object>> getMenuListByUserId(String userId, String parentId){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("logon_name", userId);
		map.put("parentid", parentId);
		return menuDao.getMenuListByUserId(map);
	}
}
