package com.ele.project.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ele.project.sysmanager.user.pojo.UserDTO;


public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
    HttpSession session;
	//不拦截的页面
    private static final String[] IGNORE_URI={"/login","/logout","/main"};   //过滤的路径

	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
//		System.out.println("请求完成！...........");
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object arg2) throws Exception {
//		System.out.println("进入拦截方法 preHandle()");
		boolean flag=false;   //用于存储判断登录的结果，让路径是否通过
//      //对请求路径进行判断
      String servletPath=request.getServletPath(); 
//      System.out.println(servletPath);
      //判断请求是否需要拦截
      for(String s:IGNORE_URI){
          if(servletPath.equals(s)){
              flag=true;    //如果是许可范围内的访问路径
              break;
          }
      }
      
    //获取session
      //拦截请求,当前是访问路径在许可范围内，以下所做的就是对非允许范围内的路径(没登录的用户)做拦截
      if(flag){   //如果是需要拦截的页面,User==null，用户信息为空
              //是不用拦截的页面
//              System.out.println("放行请求-->controller");
      }else{
          //不允许的访问路径，需要判断是否有用户信息，没有的话，让其重新登录，就跳转登录界面
          //所以需要用户在登录或者注册后就把用户信息放入session中，后面使用邮件发送验证信息，注册的页面就不用拦截.
    	  UserDTO u=(UserDTO) session.getAttribute("USER_SESSION");
          //如果用户不为空，表示已经有登录的用户信息，放行
          if(u==null){
//              System.out.println("没有对象信息,请重新登录");
              response.sendRedirect(request.getContextPath()+"/toLogin");
              flag=false;
          }else {
        	  flag=true;
          }
      }

      return flag;
	}

}
