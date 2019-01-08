package com.ele.project.sysmanager.user.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ele.project.sysmanager.user.pojo.UserDTO;
import com.ele.project.util.PageUtils;


@Controller
public class UserController {
	 private static Logger logger = LoggerFactory.getLogger(UserController.class);
	 
	/**
	 * 使用@Autowired也可以，@Autowired默认按类型装配
	 * @Resource 默认按名称装配，当找不到与名称匹配的bean才会按类型装配。
	 */
	//	@Resource 
//	private SysUserService sysUserService;
	
	//	@Resource
//	private SysRoleService sysRoleService;
	
	//	@Resource
//	private UnitInfoService unitInfoService;
	
	@RequestMapping("/user/userList")
	public String userList( Model model) {
//		List<UserDTO> userList = UserService.userList();
//		List<RoleDTO> roleList = sysRoleService.roleList();
//		model.addAttribute("userList", userList);
//		model.addAttribute("roleList", roleList);
		return "sys_BaseInformation/UserManagement";
	}
	
	/**
     * 人员维护-分页列表
     * @param req
     * @return
     */
      @RequestMapping("/pageEmp")
	  @ResponseBody
	  public Map<String, Object> pageEmp(HttpServletRequest req) {
	      
    	  Map<String, Object> paramsMap = PageUtils.getParameters(req);
	      Map<String, Object> returnMap = new HashMap<String, Object>();
	      try {
	  	    
//	           page = empBO.pageEmp(paramsMap);
	         
	      } catch (Exception e) {
	          logger.error(e.getMessage());
	      }
	      returnMap.put("currentpage", paramsMap.get("currentpage"));
//	      returnMap.put("list", page.getData());
//          returnMap.put("total", page.getTotal());
	      return returnMap;
	  } 
      
	/**
	 * ajax添加或修改数据
	 * @param user
	 * @param response
	 
	@ResponseBody
	@RequestMapping(value="/user/saveUser", method = RequestMethod.POST)
	public Map<String, String> saveUser(@ModelAttribute("user")UserDTO user,HttpServletResponse response,HttpServletRequest request){
//		System.out.println("进来了"+user.getName());
		UserDTO sessUser = (UserDTO) request.getSession().getAttribute("USER_SESSION");
		
		user.setOperatorid(sessUser.getId());
		int rs;
		if(user.getId()!=null && user.getId()>0){
			
			rs = sysUserService.updateSysUserById(user);
		}else{
			String pwd = user.getPassword();
			user.setPassword(MD5.getMD5(pwd.getBytes()));
			rs = sysUserService.insertSysUser(user);
		}

		Map<String, String> result = new HashMap<String, String>();
		result.put("result", rs+"");
		return result;
	}*/
	/**
	 * ajax根据用户ID置用户为删除状态
	 * @param id
	 * @param model
	 * @return
	
	@ResponseBody
	@RequestMapping(value="/user/delUsers", method = RequestMethod.POST)
	public String delUsers(@RequestParam(value = "id") String id,HttpServletResponse response,HttpServletRequest request) {
		com.newhero.urbanconstruction.sysmanager.user.pojo.UserDTO sessUser = (com.newhero.urbanconstruction.sysmanager.user.pojo.UserDTO)request.getSession().getAttribute("USER_SESSION");
		int rs = 0;
		if(ToolsUtil.isNotEmpty(id)){
			String[] ids = id.split(",");
			rs = sysUserService.delSysUserByIds(ids, sessUser.getId());
		}
		return Integer.toString(rs);
	} */
	/**
	 * ajax根据用户ID重置密码
	 * @param id
	 * @param model
	 * @return
	 
	@ResponseBody
	@RequestMapping(value="/user/resPwd", method = RequestMethod.POST)
	public String resPwd(@RequestParam(value = "id") Integer id ) {
		int rs = 0;
		if(id !=null && id>0){
			UserDTO user = new UserDTO();
			user.setId(id);
			user.setPassword(MD5.getMD5(Constant.resetPassword.getBytes()));
			
			rs = sysUserService.updateSysUserPwdById(user);
		}
		
		return Integer.toString(rs);
	}*/

}
