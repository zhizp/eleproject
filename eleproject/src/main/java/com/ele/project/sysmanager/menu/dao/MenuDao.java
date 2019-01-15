package com.ele.project.sysmanager.menu.dao;

import java.util.List;
import java.util.Map;

public interface MenuDao {
	public List<Map<String, Object>> getMenuListByUserId(Map<String, Object> map);
	public List<Map<String, Object>> getAllMenuList();
	public List<Map<String, Object>> getMenuList(Map<String, Object> map);
	public List<Map<String, Object>> getMenuPage(Map<String, Object> params);
	public int getMenuPageCount(Map<String, Object> params) ;
	public int addMenu(Map<String, Object> params);
}
