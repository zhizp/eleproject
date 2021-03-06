package com.ele.project.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SupportAction {
  protected void responseInitialize(HttpServletResponse response) {
     response.setContentType("text/html;charset=UTF-8");
     response.setHeader("Cache-Control", "no-cache");
  }

  protected String formatResponse(boolean flag, String msg) {
     String format = "{\"success\":%1$b,\"msg\":\"%2$s\"}";
     return String.format(format, new Object[]{Boolean.valueOf(flag), msg});
  }

  protected String formatResponse(String result) {
     String format = "{\"success\":true,\"result\":\"%1$s\"}";
     return String.format(format, new Object[]{result});
  }

  protected String formatResponseTree(String result) {
     String format = "{\"success\":true,\"result\":%1$s}";
     return String.format(format, new Object[]{result});
     
  }
  
  protected Object getUser(HttpServletRequest request,HttpServletResponse response) {
	return request.getSession().getAttribute("user");  
  }


/*
  protected SysLogBean getSysLogBean(HttpServletRequest request, Class callerClass, String sfId) {
     String smlSessionId = request.getSession().getId();
     SysUserBean sysUserBean = (SysUserBean)request.getSession().getAttribute("user");

     String suId = "";
     String suName = "";
     if(sysUserBean != null) {
        suId = sysUserBean.getSuId();
        suName = sysUserBean.getSuName();
     }
     String smlIp = request.getRemoteAddr();
     SysLogBean sysLogBean = new SysLogBean();
     sysLogBean.setSmlSessionId(smlSessionId);
     sysLogBean.setSuId(suId);
     sysLogBean.setSuName(suName);
     sysLogBean.setSmlIp(smlIp);
     sysLogBean.setSfId(sfId);
     sysLogBean.setSmlClassName(callerClass.getName());
     String o = request.getParameter("logFlag");
     if(o != null) {
        String flag = o.toString();
        if(flag.equals("false")) {
           sysLogBean.setLogFlag(false);
        }
     } else {
        sysLogBean.setLogFlag(true);
     }
     return sysLogBean;
  }*/
}
