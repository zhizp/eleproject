package com.ele.project.common.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ele.project.common.SupportAction;
import com.ele.project.common.service.LoginService;
import com.ele.project.sysmanager.user.pojo.UserDTO;
import com.ele.project.util.MD5;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/loginAction")
public class LoginController extends SupportAction {
	@Resource 
	private LoginService loginService;	
	
	public LoginService getLoginService() {
		return loginService;
	}

	/**
     * 向用户登录页面跳转
     */
    @RequestMapping(value = "/toLogin")
    public String toLogin(){
        return  "login";
    }

    /**
     * 用户登录，根据用户登录名和密码判断数据库中是否有此用户
     * @param user
     * @param model
     * @param session
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(HttpServletResponse response,HttpServletRequest request,UserDTO user) throws IOException{
    	Map<String, Object> result = new HashMap<String, Object>();
    	try {
	     //获取用户名和密码
	     String username=user.getUsername();
	     String password=user.getUser_pwd();
	     user.setUsername(username);
	     user.setUser_pwd(MD5.getMD5(password.getBytes()));
	     UserDTO returnUser = loginService.selectUser(user);
	     List<UserDTO> list = loginService.getUserDTOByName(username);
	     
	     result.put("success", true);
		 result.put("guid", returnUser.getGuid());
		 request.getSession().setAttribute("USER_SESSION", returnUser);
	     request.setAttribute("user", returnUser);
    	}catch(Exception e) {
    		e.printStackTrace();
			result.put("success", false);
			result.put("msg", "login error," + e.getMessage());
    	}
    	return result;
    }
    
    /**
     * 向主页面跳转
     */
    @RequestMapping(value = "/toIndex")
    public String toIndex(HttpServletResponse response,HttpServletRequest request){
    	System.out.println("loginAction/toIndex/进入主页");
    	UserDTO user=(UserDTO)request.getSession().getAttribute("USER_SESSION");
//    	String menuState=loginService.selectMenuState(user);
//    	request.getSession().setAttribute("menuState", menuState);
    	request.getSession().setAttribute("user", user);
        return  "index";
    }
    /**
     * 
     * @param menuid 0:年度;1:建设单位;
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/toLeftMenu")
    public String toLeftMenu(String menuid, HttpServletResponse response,HttpServletRequest request){
    	UserDTO user=(UserDTO)request.getSession().getAttribute("USER_SESSION");
//    	String menuSatate=loginService.selectMenuState(user);
//    	request.setAttribute("menuSatate", menuSatate);
    	request.setAttribute("menuid", "1");
        return  "leftmenu";
    }

    @RequestMapping(value = "/main")
    public String toMain(){
        return "main";
    }
    
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        //清除session
        session.invalidate();
        //重定向到登录页面的跳转方法
        return "redirect:toLogin";
    }
    
    
    
    
    
	
}
