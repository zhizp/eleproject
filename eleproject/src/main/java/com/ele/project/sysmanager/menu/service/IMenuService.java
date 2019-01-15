package com.ele.project.sysmanager.menu.service;

import java.util.List;
import java.util.Map;

public interface IMenuService {

	/**
    * 导航菜单--根据用户ID
    * 登录初始化左侧菜单
    * @param userId 用户登录id
    * @param parentId 
    * @return
    */
    List<Map<String, Object>> getMenuListByUserId(String login_name, String parentId);
    
    List<Map<String, Object>> getAllMenuList();
    
    public List<Map<String, Object>> getMenuList(String menu_id, String parentId);
    
    public List<Map<String, Object>> getMenuPage(Map<String, Object> params);
    public int getMenuPageCount(Map<String, Object> params);
    public int addMenu(Map<String, Object> params);
}
