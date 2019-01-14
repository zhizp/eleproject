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


@Controller("/roleController")
public class RoleController {
	 private static Logger logger = LoggerFactory.getLogger(RoleController.class);
	 /**
	 * 使用@Autowired也可以，@Autowired默认按类型装配
	 * @Resource 默认按名称装配，当找不到与名称匹配的bean才会按类型装配。
	 */
	 @Resource
	 private RoleService roleService;
	
	 
	 /**
      * 分页查询角色列表
      * @param req
      * @return
      */
      @RequestMapping("/getRolePage")
	  @ResponseBody
	  public Map<String, Object> getRolePage(HttpServletRequest req) {
	      
    	  Map<String, Object> paramsMap = PageUtils.getParameters(req);
	      Map<String, Object> returnMap = new HashMap<String, Object>();
	      List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
	      PageDTO page =null;
	      int count=0;
	      try {
	    	  returnList = roleService.getRolesList(paramsMap);
	    	  count = roleService.getRolesListCount(paramsMap);
	    	  page.setData(returnList);
	    	  page.setTotal(count);
	      } catch (Exception e) {
	          logger.error(e.getMessage());
	      }
	      returnMap.put("currentpage", paramsMap.get("currentpage"));
	      returnMap.put("list", page.getData());
          returnMap.put("total", page.getTotal());
	      return returnMap;
	  }
      
      /**
       * 新增角色
       * @param req
       * @return
       */
      @RequestMapping("/saveRole")
	  @ResponseBody
	  public Map<String, Object> saveRole(HttpServletRequest req,@RequestBody Map<String, Object> paramsMap) {
    	  Map<String, Object> returnMap=new HashMap<String, Object>();
	      try {
	    	  if( paramsMap.get("parent_guid")==null || paramsMap.get("parent_guid").equals("")){
	    		  paramsMap.put("parent_guid", 0);
	    	  }
	    	  paramsMap.put("sysdate", new Date());
	    	  String role_id=StringUtil.getUUID(); 
	    	  paramsMap.put("role_id", role_id);
	    	  int i=roleService.addRole(paramsMap);
	    	  if(i<1){
                returnMap.put("rtflag","保存失败！");
                returnMap.put("rt",false);
                return returnMap;
	    	  }
	    	  returnMap.put("rt",true);
              //添加业务操作日志
              String[] s = new String[]{"新增角色", "角色管理", "saveRole", paramsMap.toString(), ""};
	      } catch (Exception e) {
	          logger.error(e.getMessage());
	      }
		return returnMap;
	
	  }
	 

}
