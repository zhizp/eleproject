package com.ele.project.sysmanager.menu.service;

import java.util.List;
import java.util.Map;

public interface IMenuService {

	/**
    * 导航菜单--根据用户ID
    * @param userId
    * @param parentId
    * @return
    */
    List<Map<String, Object>> getMenuListByUserId(String userId, String parentId);
}
