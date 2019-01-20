package com.ele.project.sysmanager.roleMenu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ele.project.sysmanager.roleMenu.service.RoleToMenuService;
import com.ele.project.util.PageUtils;
import com.ele.project.util.StringUtil;

@Controller
@RequestMapping("/roleToMenuController")
public class RoleToMenuController {
	private static Logger logger = LoggerFactory.getLogger(RoleToMenuController.class);
	@Resource
	private RoleToMenuService roleToMenuService;

	/**
	 * 根据角色查询权限
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/getMenuIds")
	@ResponseBody
	public Map<String, Object> getMenuIds(HttpServletRequest req) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramsMap = PageUtils.getParameters(req);

			String menuids[] = null;

			if (paramsMap.get("role_id") != null && !paramsMap.get("role_id").equals("")) {
				list = roleToMenuService.getRoleToMenuList(paramsMap.get("role_id").toString());
				menuids = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					menuids[i] = list.get(i).get("menu_id").toString();
				}
			}
			returnMap.put("menuids", menuids);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return returnMap;
	}

	/**
	 * 新增
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/saveRoleToMenu")
	@ResponseBody
	public Map<String, Object> saveRoleToMenu(HttpServletRequest req, @RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> paramlist = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> roleMenulist = new ArrayList<Map<String, Object>>();
			roleMenulist = roleToMenuService.getRoleToMenuList(paramsMap.get("role_id").toString());
			if(roleMenulist.size()>0) {
				// 先删除该菜单下所有的关联记录，然后重新插入数据
				int j=roleToMenuService.deleteRoleToMenu(paramsMap);
				if (j < 1) {
					returnMap.put("rt", false);
					returnMap.put("rtflag", "删除原有角色菜单关联信息失败");
					return returnMap;
				}
			}
			String menu_ids = paramsMap.get("menu_ids").toString();
			if (menu_ids != null && !menu_ids.equals("")) {
				String idArray[] = menu_ids.split(",");
				for (int i = 0; i < idArray.length; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("rtf_id", StringUtil.getUUID());
					map.put("role_id", paramsMap.get("role_id"));
					map.put("menu_id", idArray[i]);
					if (paramsMap.containsKey("parent_guid") && paramsMap.get("parent_guid").equals("0")) {// 是否是根节点 0：是 1：否
						map.put("is_leaf", "0");
					} else {
						map.put("is_leaf", "1");
					}
					paramlist.add(map);
				}
				int k=roleToMenuService.addBatchRoleToMenu(paramlist);
				if (k < 1) {
					returnMap.put("rt", false);
					returnMap.put("rtflag", "保存角色菜单关联信息失败");
					return returnMap;
				}
			}
			returnMap.put("rt", true);

			// 添加业务操作日志
			String[] s = new String[] { "新增角色菜单关联记录", "角色管理", "saveRoleToMenu", paramsMap.toString(), "" };

		} catch (Exception e) {
			logger.error(e.getMessage());
			returnMap.put("rtflag", "保存失败");
		}
		return returnMap;
	}
}
