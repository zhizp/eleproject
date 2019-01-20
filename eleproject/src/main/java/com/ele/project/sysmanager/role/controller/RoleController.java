package com.ele.project.sysmanager.role.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.ele.project.sysmanager.role.service.RoleService;
import com.ele.project.util.PageDTO;
import com.ele.project.util.PageUtils;
import com.ele.project.util.StringUtil;

@Controller
@RequestMapping("/roleController")
public class RoleController {
	private static Logger logger = LoggerFactory.getLogger(RoleController.class);
	/**
	 * 使用@Autowired也可以，@Autowired默认按类型装配
	 * 
	 * @Resource 默认按名称装配，当找不到与名称匹配的bean才会按类型装配。
	 */
	@Resource
	private RoleService roleService;

	/**
	 * 分页查询角色列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/getRolePage")
	@ResponseBody
	public Map<String, Object> getRolePage(HttpServletRequest req) {

		Map<String, Object> paramsMap = PageUtils.getParameters(req);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		int count = 0;
		try {
			returnList = roleService.getRolesList(paramsMap);
			count = roleService.getRolesListCount(paramsMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		returnMap.put("currentpage", paramsMap.get("currentpage"));
		returnMap.put("list", returnList);
		returnMap.put("total", count);
		return returnMap;
	}

	/**
	 * 新增角色
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/saveRole")
	@ResponseBody
	public Map<String, Object> saveRole(HttpServletRequest req, @RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			if (paramsMap.get("parent_guid") == null || paramsMap.get("parent_guid").equals("")) {
				paramsMap.put("parent_guid", 0);
			}
			paramsMap.put("sysdate", new Date());
			String role_id = StringUtil.getUUID();
			paramsMap.put("role_id", role_id);
			int i = roleService.addRole(paramsMap);
			if (i < 1) {
				returnMap.put("rtflag", "保存失败！");
				returnMap.put("rt", false);
				return returnMap;
			}
			returnMap.put("rt", true);
			// 添加业务操作日志
			String[] s = new String[] { "新增角色", "角色管理", "saveRole", paramsMap.toString(), "" };
		} catch (Exception e) {
			logger.error(e.getMessage());
			returnMap.put("rt", false);
			returnMap.put("rtflag", "保存失败！");
		}
		return returnMap;

	}

	/**
	 * 查询某个角色
	 * 
	 * @param req
	 * @param paramsMap
	 */
	@RequestMapping("/getRole")
	@ResponseBody
	public Map<String, Object> getRole(HttpServletRequest req) {

		Map<String, Object> paramsMap = PageUtils.getParameters(req);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			if (paramsMap.get("role_id") != null && !paramsMap.get("role_id").equals("")) {
				returnMap = roleService.getRoleById(paramsMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return returnMap;

	}

	/**
	 * 修改角色
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateRole")
	@ResponseBody
	public Map<String, Object> updateRole(HttpServletRequest req, @RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			paramsMap.put("sysdate", new Date());
			int i = roleService.updateRole(paramsMap);
			if (i < 1) {
				returnMap.put("rt", false);
				return returnMap;
			}
			returnMap.put("rt", true);
			// 添加业务操作日志
			String[] s = new String[] { "修改角色", "角色管理", "updateRole", paramsMap.toString(), "" };
			// logUtils.saveOprationLog(req, s);

		} catch (Exception e) {
			logger.error(e.getMessage());
			returnMap.put("rt", false);
			returnMap.put("rtflag", "修改失败！");
		}
		return returnMap;
	}

	/**
	 * 删除某个角色
	 * 
	 * @param req
	 * @param paramsMap
	 */
	@RequestMapping("/deleteRole")
	@ResponseBody
	public Map<String, Object> deleteRole(HttpServletRequest req) {
		Map<String, Object> paramsMap = PageUtils.getParameters(req);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			if (paramsMap.get("role_id") != null && !paramsMap.get("role_id").equals("")) {
				int i = roleService.deleteRoles(paramsMap);
				if (i < 1) {
					returnMap.put("rt", false);
					returnMap.put("rtflag", "删除角色信息失败！");
					return returnMap;
				}
				// 查询用户角色关联表是否有信息，有才删除
				int userRoleCount = roleService.getUserRoles(paramsMap);
				if (userRoleCount > 0) {
					int j = roleService.deleteUserRoles(paramsMap);
					if (j < 1) {
						returnMap.put("rt", false);
						returnMap.put("rtflag", "删除角色关联用户信息失败！");
						return returnMap;
					}
				}
				// 查询角色菜单表是否有数据，有才删除
				int MenuRoleCount = roleService.getMenuRoles(paramsMap);
				if (MenuRoleCount > 0) {
					int k = roleService.deleteMenuRoles(paramsMap);
					if (k < 1) {
						returnMap.put("rt", false);
						returnMap.put("rtflag", "删除角色关联菜单信息失败！");
						return returnMap;
					}
				}

				returnMap.put("rt", true);
			}
			// 添加业务操作日志
			String[] s = new String[] { "删除角色", "角色管理", "deleteRole", paramsMap.toString(), "" };
		} catch (Exception e) {
			logger.error(e.getMessage());
			returnMap.put("rt", false);
			returnMap.put("rtflag", "删除失败！");
		}
		return returnMap;
	}

	/**
	 * 查询角色列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/getRoleList")
	@ResponseBody
	public List<Map<String, Object>> getRoleList(HttpServletRequest req) {

		List<Map<String, Object>> returnlist = new ArrayList<Map<String, Object>>();
		try {
			Map<String, Object> paramsMap = PageUtils.getParameters(req);
			returnlist = roleService.getRoleLists(paramsMap);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return returnlist;
	}

	/**
	 * 根据用户ID查询角色
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/getRoleIdsByUserId")
	@ResponseBody
	public String getRoleIdsByUserId(HttpServletRequest req) {

		Map<String, Object> paramsMap = PageUtils.getParameters(req);
		String userId = paramsMap.get("userid").toString();
		String str = "";
		try {

			str = roleService.getRoleIdsByUserId(userId);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return str;
	}
}
