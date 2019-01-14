package com.ele.project.sysmanager.menu.dao;

import java.util.List;
import java.util.Map;

public interface MenuDao {
	public List<Map<String, Object>> getMenuListByUserId(Map<String, Object> map);
}
