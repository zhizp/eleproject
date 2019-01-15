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
	
	public List<Map<String, Object>> getMenuListByUserId(String login_name, String parentId){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("login_name", login_name);
		map.put("parentid", parentId);
		return menuDao.getMenuListByUserId(map);
	}
	
	public List<Map<String, Object>> getAllMenuList(){
		return menuDao.getAllMenuList();
	}
	
    public List<Map<String, Object>> getMenuList(String menu_id, String parentid) {
    	Map<String, Object> map=new HashMap<String, Object>();
		map.put("menu_id", menu_id);
		map.put("parentid", parentid);
        return menuDao.getMenuList(map);
    }
    
    public List<Map<String, Object>> getMenuPage(Map<String, Object> params){
    	return menuDao.getMenuPage(params);
    }
    
    public int getMenuPageCount(Map<String, Object> params) {
    	return menuDao.getMenuPageCount(params);
    }
    
    public int addMenu(Map<String, Object> params) {
        return menuDao.addMenu(params);
    }
}
