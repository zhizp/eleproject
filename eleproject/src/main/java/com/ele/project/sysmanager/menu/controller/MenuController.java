package com.ele.project.sysmanager.menu.controller;

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

import com.ele.project.sysmanager.menu.service.IMenuService;
import com.ele.project.sysmanager.user.controller.UserController;
import com.ele.project.util.PageDTO;
import com.ele.project.util.PageUtils;
import com.ele.project.util.StringUtil;

@Controller
@RequestMapping("/menuController")
public class MenuController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Resource
	private IMenuService menuService;
	
	@RequestMapping("/getAllMenuList")
	@ResponseBody
    public Map<String, Object> getAllMenuList(HttpServletRequest req) {
        Map<String, Object> reMap = new HashMap<>();

        try {
            List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> allList = menuService.getAllMenuList();

            menuList = getNextMenuList("00", allList);
            Map<String, Object> deMap = new HashMap<>();
            deMap.put("children", "childMenu");
            deMap.put("label", "NAME");

            reMap.put("data", menuList);
            reMap.put("defaultProps", deMap);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return reMap;
    }
	
	private List<Map<String, Object>> getNextMenuList(String parentID, List<Map<String, Object>> list) {
        List<Map<String, Object>> childList = new ArrayList<>();
        for (Map<String, Object> map : list) {

            if (map.get("parentid") == null) {
                continue;
            }
            if (String.valueOf(map.get("parentid")).equals(parentID)) {
                childList.add(map);
            }


        }
        for (Map<String, Object> map : childList) {
            if (null == map.get("menu_id")) {
                continue;
            }
            map.put("childMenu", getNextMenuList(String.valueOf(map.get("menu_id")), list));

        }
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
	
	
	/**
     * 查询树形菜单
     * @param req
     * @return
     */
    @RequestMapping("/queryMenuTree")
	@ResponseBody
    private List<Map<String, Object>> queryMenuTree(HttpServletRequest req) {
        
    	List<Map<String, Object>> returnlist = menuService.getMenuList("", "");
    	returnlist=queryChildMenu(returnlist,"0");
        return returnlist;
    }
    
    /**
     * 递归查询树形菜单
     * @param returnlist
     * @param parentid
     * @return
     */
    public List<Map<String, Object>> queryChildMenu(List<Map<String, Object>> list,String parentid){
    	
      	 List<Map<String, Object>> returnlist=new ArrayList<Map<String, Object>>();
       	 
         for(int i=0;i<list.size();i++){
 
        	 if(list.get(i).get("parentid").equals(parentid)){
            	 List<Map<String, Object>> childlist=new ArrayList<Map<String, Object>>();
             	 Map<String, Object> returnMap =new HashMap<String, Object>();
             	 String MENU_ID=list.get(i).get("menu_id").toString();
             	 returnMap.put("label", list.get(i).get("name"));
             	 returnMap.put("menu_id", MENU_ID);
//             	 list.remove(list.get(i));//移除已使用的
             	 //子菜单
             	 childlist=queryChildMenu(list,MENU_ID);
             	 if(childlist.size()>0){
             		returnMap.put("children", childlist);
             	 }
             	returnlist.add(returnMap);
        	 }
        	 

         }
		return returnlist;
    }
    
      /**
      * 分页查询菜单列表
      * @param req
      * @return
      */
      @SuppressWarnings("null")
      @RequestMapping("/getMenuPage")
	  @ResponseBody
	  public Map<String, Object> getMenuPage(HttpServletRequest req) {
	      
    	  Map<String, Object> paramsMap = PageUtils.getParameters(req);
	      Map<String, Object> returnMap = new HashMap<String, Object>();
	      List<Map<String, Object>> returnList=new ArrayList<Map<String, Object>>();
	      int count=0;
	      PageDTO page =new PageDTO();
	      try {
	         
	    	  returnList = menuService.getMenuPage(paramsMap);
	    	  count = menuService.getMenuPageCount(paramsMap);
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
       * 新增
       * @param req
       * @return
       */
      @RequestMapping("/saveMenu")
  	  @ResponseBody
  	  public Map<String, Object> saveMenu(HttpServletRequest req,@RequestBody Map<String, Object> paramsMap) {
    	  Map<String, Object> returnMap=new HashMap<String, Object>();
  	      try {
  	    	  if( paramsMap.get("parentid")==null || paramsMap.get("parentid").equals("")){
  	    		  paramsMap.put("parentid", 0);
  	    	  }
  	    	  paramsMap.put("sysdate", new Date());
  	    	  String menu_id=StringUtil.getUUID(); 
  	    	  paramsMap.put("menu_id", menu_id);
  	    	  //后期维护字段
//  	    	  paramsMap.put("MENUICON", ""); 
  	    	  paramsMap.put("operator_organization", "0");
  	    	  paramsMap.put("operator_department", "0");
  	    	  paramsMap.put("operator", "0");
  	    	  paramsMap.put("rs", 1);
  	    	  
  	    	  int i= menuService.addMenu(paramsMap);
  	    	  if(i<1) {
    			returnMap.put("rt",false);
    			returnMap.put("rtflag","用户角色信息保存失败！");
    			return returnMap;
  	    	  }
  	    	  returnMap.put("rt",true);
              //添加业务操作日志
              String[] s = new String[]{"新增菜单", "菜单管理", "saveMenu", paramsMap.toString(), ""};
  	          
  	      } catch (Exception e) {
  	          logger.error(e.getMessage());
  	      }
  	      return returnMap;
  	  }
      
      @RequestMapping("/getOneMenuList")
      @ResponseBody
      public Map<String, Object> getOneMenuList(HttpServletRequest req) {
          Map<String, Object> map = new HashMap<>();
          try {
              Map<String, Object> parametersmap = PageUtils.getParameters(req);
              String menuID = parametersmap.get("menuid") == null ? "" : parametersmap.get("menuid").toString();
              List<Map<String, Object>> list = menuService.getMenuList(menuID, "");
              map = list.get(0);
              //查询上级菜单名称
              if(map.get("parentid")!=null && !map.get("parentid").equals("") && !map.get("parentid").equals("0")){
              	List<Map<String, Object>> plist = menuService.getMenuList(map.get("parentid").toString(), "");
              	String PARENTNAME="";
              	if(plist.size()>0){
              		PARENTNAME=plist.get(0).get("name").toString();
              	}
              	map.put("parentname", PARENTNAME);
              }
          } catch (Exception e) {
              logger.error(e.getMessage());
          }
          return map;
      }
      
	
	
}
