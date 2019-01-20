package com.ele.project.sysmanager.user.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ele.project.sysmanager.user.pojo.UserDTO;
import com.ele.project.sysmanager.user.service.SysUserService;
import com.ele.project.sysmanager.userRole.pojo.UserToRoleDTO;
import com.ele.project.sysmanager.userRole.service.UserToRoleService;
import com.ele.project.util.Constant;
import com.ele.project.util.MD5;
import com.ele.project.util.PageDTO;
import com.ele.project.util.PageUtils;
import com.ele.project.util.StringUtil;

@Controller
@RequestMapping("/userController")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	/**
	 * 使用@Autowired也可以，@Autowired默认按类型装配
	 * 
	 * @Resource 默认按名称装配，当找不到与名称匹配的bean才会按类型装配。
	 */
	@Resource
	private SysUserService sysUserService;
	@Resource
	private UserToRoleService userToRoleService;

	/**
	 * 修改密码
	 * 
	 * @param req
	 */
	@RequestMapping("/updatePwd")
	@ResponseBody
	public int updatePwd(HttpServletRequest req) {
		Map<String, Object> paramsMap = PageUtils.getParameters(req);
		int ret = 0;
		if (StringUtil.isNotEmpty(paramsMap.get("userid"))) {// 管理员重置密码
			paramsMap.put("user_pwd", MD5.getMD5(Constant.resetPassword.getBytes()));
		} else {// 个人修改密码
			paramsMap.put("userid", paramsMap.get("userid"));
			paramsMap.put("user_pwd", MD5.getMD5(paramsMap.get("user_pwd").toString().getBytes()));
		}

		try {
			ret = sysUserService.updatePwd(paramsMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ret;
	}

	/**
	 * 用户列表-分页
	 */
	@RequestMapping("/userlist")
	@ResponseBody
	public Map<String, Object> userlist(HttpServletRequest req) {
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Map<String, Object>> userlist = null;
		try {
			if(req.getParameter("username")!=null && req.getParameter("username")!="") {
				String username= URLDecoder.decode(req.getParameter("username"),"UTF-8");
				params.put("username", username);
			}
			userlist = sysUserService.queryUserList(params);
			int rowsCount = sysUserService.queryUserListCount(params);
			if (userlist != null && userlist.size() > 0) {
				for (int i = 0; i < userlist.size(); i++) {
					if (userlist.get(i).get("enabled").equals("1")) {
						userlist.get(i).put("enabled", "启用");
					} else {
						userlist.get(i).put("enabled", "注销");
					}
					String userid = userlist.get(i).get("userid").toString();
					Object name = getRoleById(userid);
					userlist.get(i).put("name", name);
				}
			}
			returnMap.put("list", userlist);
			returnMap.put("total", rowsCount);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return returnMap;

	}

	// 去掉列表中单个角色所带，号
	public Object getRoleById(String userid) {
		Object name = "";
		try {
			List<Map<String, Object>> list = userToRoleService.getRoleById(userid);
			if (list.size() > 0) {
				name = list.get(0).get("name").toString();
				for (int j = 1; j < list.size(); j++) {
					name += "," + list.get(j).get("name").toString();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return name;
	}

	/**
	 * 增加用户列表
	 */
	@RequestMapping("/insertUser")
	@ResponseBody
	public Map<String, Object> insertUser(HttpServletRequest req, UserDTO userDTO) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params = PageUtils.getParameters(req);
			UUID uuid = UUID.randomUUID();
			String guid = uuid.toString().replace("-", "");
			params.put("userid", guid);
			int i = sysUserService.saveUser(params);
			if (i < 1) {
				returnMap.put("rtflag", "用户信息保存失败！");
				returnMap.put("rt", false);
				return returnMap;
			}
			String roleid = params.get("roleid").toString();
			int rflag = 0;
			if (StringUtil.isNotEmpty(roleid)) {
				rflag = saveRoleUser(guid, roleid);
				if (rflag < 1) {
					returnMap.put("rt", false);
					returnMap.put("rtflag", "用户角色信息保存失败！");
					return returnMap;
				}
			}
			returnMap.put("rt", true);
			// 添加业务操作日志
			String[] s = new String[] { "新增用户", "用户管理", "insertUser", params.toString(), "" };
			// logUtils.saveOprationLog(req, s);
		} catch (Exception e) {
			logger.error(e.getMessage());
			returnMap.put("rtflag", "保存失败！");
			returnMap.put("rt", false);
		}
		return returnMap;
	}

	protected int saveRoleUser(String guid, String roleid) {
		String[] roleids = roleid.split(",");
		UserToRoleDTO rUser = new UserToRoleDTO();
		int rtflag = 0;
		try {
			for (int i = 0; i < roleids.length; i++) {
				rUser.setRole_id(roleids[i]);
				rUser.setUserid(guid);
				rUser.setUr_id(StringUtil.getUUID());
				rtflag = userToRoleService.addUserToRole(rUser);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return rtflag;
	}

	/**
	 * 
	 * 用户分配角色
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("setUserRole")
	@ResponseBody
	public Map<String, Object> setUserRole(HttpServletRequest req) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> params = PageUtils.getParameters(req);
		String userid = String.valueOf(params.get("userid"));
		String roleid = String.valueOf(params.get("roleid"));
		try {
			if (StringUtil.isNotEmpty(userid)) {// 删除该用户的所有角色
				List<Map<String, Object>> userRolelist=userToRoleService.selectUserToRole(userid);
				if(userRolelist.size()>0) {
					int i = userToRoleService.deleteUserToRole(userid);
					if (i < 1) {
						returnMap.put("rtflag", "删除该用户的所有角色失败！");
						returnMap.put("rt", false);
						return returnMap;
					}
				}
			}
			if (StringUtil.isNotEmpty(roleid)) {// 新增已勾选角色
				int j = saveRoleUser(userid, roleid);
				if (j < 1) {
					returnMap.put("rtflag", "保存用户角色信息失败！");
					returnMap.put("rt", false);
					return returnMap;
				}
			}
			returnMap.put("rt", true);
			// 添加业务操作日志
			String[] s = new String[] { "用户分配角色", "用户管理", "setUserRole", params.toString(), "" };
		} catch (Exception e) {
			logger.error(e.getMessage());
			returnMap.put("rtflag", "保存失败！");
			returnMap.put("rt", false);
		}
		return returnMap;
	}
	
	/**
	 * 新增用户
	 * @param req
	 * @return
	 */
	@RequestMapping("/saveUser")
	@ResponseBody
	public Map<String, Object> saveUser(HttpServletRequest req, @RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			String userid = StringUtil.getUUID();
			paramsMap.put("userid", userid);
			paramsMap.put("operator_organization", "0");
			paramsMap.put("operator_department", "0");
			paramsMap.put("operator", "0");
			paramsMap.put("rs", 1);

			int i = sysUserService.saveUser(paramsMap);
			if (i < 1) {
				returnMap.put("rt", false);
				return returnMap;
			}
			returnMap.put("rt", true);
			// 添加业务操作日志
			String[] s = new String[] { "新增用户", "用户管理", "saveUser", paramsMap.toString(), "" };

		} catch (Exception e) {
			logger.error(e.getMessage());
			returnMap.put("rt", false);
		}
		return returnMap;
	}

	
	/**
	 * 修改用户
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateUser")
	@ResponseBody
	public Map<String, Object> updateUser(HttpServletRequest req, @RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			paramsMap.put("operator_organization", "0");
			paramsMap.put("operator_department", "0");
			paramsMap.put("operator", "0");

			int i = sysUserService.updateUser(paramsMap);
			if (i < 1) {
				returnMap.put("rt", false);
				return returnMap;
			}
			returnMap.put("rt", true);
			// 添加业务操作日志
			String[] s = new String[] { "修改用户", "用户管理", "updateUser", paramsMap.toString(), "" };

		} catch (Exception e) {
			logger.error(e.getMessage());
			returnMap.put("rt", false);
		}
		return returnMap;
	}
	/**
	 * 删除用户
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteUser")
	@ResponseBody
	public Map<String, Object> deleteUser(HttpServletRequest req, @RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			
			int i = sysUserService.deleteUser(paramsMap);
			if (i < 1) {
				returnMap.put("rt", false);
				return returnMap;
			}
			returnMap.put("rt", true);
			// 添加业务操作日志
			String[] s = new String[] { "删除用户", "用户管理", "deleteUser", paramsMap.toString(), "" };
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			returnMap.put("rt", false);
		}
		return returnMap;
	}
}
