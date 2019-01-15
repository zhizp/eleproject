package com.ele.project.common.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ele.project.common.SupportAction;
import com.ele.project.common.service.LoginService;
import com.ele.project.sysmanager.user.pojo.UserDTO;
import com.ele.project.util.MD5;

@Controller
@RequestMapping(value = "/")
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
    @RequestMapping(value = "/login")
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest req,@RequestBody Map<String, Object> paramsMap) throws IOException{
    	Map<String, Object> result = new HashMap<String, Object>();
    	try {
		 UserDTO user=new UserDTO();
	     //获取登录名和密码
		 if(paramsMap.containsKey("login_name")){
			 user.setLogin_name(paramsMap.get("login_name").toString());
         }
		 if(paramsMap.containsKey("user_pwd")){
			 user.setUser_pwd(MD5.getMD5(paramsMap.get("user_pwd").toString().getBytes()));
		 }
	     UserDTO returnUser = loginService.selectUser(user);
	     //List<UserDTO> list = loginService.getUserDTOByName(username);
	     if(returnUser!=null) {
	    	 result.put("success", true);
			 result.put("userid", returnUser.getUserid());
			 req.getSession().setAttribute("user", returnUser);
			 req.setAttribute("user", returnUser);
	     }else {
	    	 result.put("success", false);
	     }
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
    @RequestMapping(value = "/main")
    public String toIndex(HttpServletResponse response,HttpServletRequest request){
    	UserDTO user=(UserDTO)request.getSession().getAttribute("user");
//    	String menuState=loginService.selectMenuState(user);
//    	request.getSession().setAttribute("menuState", menuState);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String nowdate=sdf.format(new Date());
    	request.setAttribute("nowdate", nowdate);
    	request.getSession().setAttribute("user", user);
        return  "main";
    }
    
    
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        //清除session
        session.invalidate();
        //重定向到登录页面的跳转方法
        return "redirect:toLogin";
    }
    
    
    
	
}
