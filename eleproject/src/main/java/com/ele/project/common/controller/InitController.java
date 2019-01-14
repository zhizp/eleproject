package com.ele.project.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ele.project.common.pojo.InitDTO;
import com.ele.project.sysmanager.menu.service.IMenuService;
import com.ele.project.sysmanager.user.pojo.UserDTO;
import com.ele.project.util.DateUtil;
import com.ele.project.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

/*import hecz.erp.bo.IEmpBO;
import hecz.erp.bo.IMenuBO;
import hecz.erp.dto.InitDTO;
import hecz.erp.dto.PageDTO;
import hecz.erp.utils.CacheUtils;
import hecz.erp.utils.ConfigUtil;
import hecz.erp.utils.HttpUtil;
import hecz.erp.utils.TokenUtils;*/

@Controller
@RequestMapping(value = "/initController")
public class InitController {
    private static Logger logger = LoggerFactory.getLogger(InitController.class);

    @Autowired
    private IMenuService menuService;

    @RequestMapping("/getSysUrl")
    public String getSysUrl(HttpServletRequest request) {
        String rz = null;
        String sys = request.getParameter("sys");//前端传回用户要进入的子系统代码
        //TODO:根据子系统代码和TOKEN（可以知道当前用户是谁），查询当前用户是否有子系统权限
        //TODO:#1#得到子系统初始化地址（内部地址）（从数据库子系统表中获取），默认打开系统从配置文件读取
        //TODO:通过初始化地址获取子系统相关菜单及初始化数据

        String url = null;//TODO:@1#此地址应为从数据库中取得。并且必须是http地址，目前不支持https。内部服务器间访问应仅走http
        if (sys != null && !sys.isEmpty()) {
            rz = url;
        } else {
            rz = "http://127.0.0.1:8888/eleproject/init";
        }
        return "forward: ptinit?isu=" + rz;
    }

    /**
     * 平台初始化入口（生成菜单、获取子系统返回的用户信息等）
     *
     * @param request
     * @return
     */
    @RequestMapping("/ptinit")
    @ResponseBody
    public InitDTO initWebPT(HttpServletRequest request) {
    	String sysUrl = null;
    	String sys = request.getParameter("sys");//前端传回用户要进入的子系统代码
        //TODO:根据子系统代码和TOKEN（可以知道当前用户是谁），查询当前用户是否有子系统权限
        //TODO:#1#得到子系统初始化地址（内部地址）（从数据库子系统表中获取），默认打开系统从配置文件读取
        //TODO:通过初始化地址获取子系统相关菜单及初始化数据

        String url = null;//TODO:@1#此地址应为从数据库中取得。并且必须是http地址，目前不支持https。内部服务器间访问应仅走http
        if (sys != null && !sys.isEmpty()) {
        	sysUrl = url;
        } else {
        	sysUrl = "http://127.0.0.1:8888/eleproject/init";
        }
        
        
        ObjectMapper mapper = new ObjectMapper();
        InitDTO rz = new InitDTO();
        Map<String, Object> initInfo = new HashedMap();
        Map<String, Object> userInfo = new HashMap<String, Object>();
        Map<String, Object> menus = null;
        UserDTO user=(UserDTO)request.getSession().getAttribute("user");
        try {
            //String token = TokenUtils.getToken(request);
            //String sysUrl = request.getParameter("isu");//init system url子系统初始化url

            //1.访问子系统，获取子系统用户信息
            Map<String, Object> req = new HashedMap();
            //req.put("token", token);

//            String udata = HttpUtil.doPost(sysUrl, req);//request.isSecure() ? HttpUtil.doPostSSL(sysUrl, req) : HttpUtil.doPost(sysUrl, req);
//            userInfo = mapper.readValue(udata, Map.class);
            userInfo.put("username",user.getUsername());
            rz.setUser(userInfo);

            //2.获取菜单信息
            menus = this.queryMenuNavByRoleId(request);
            menus.put("opens", new ArrayList());
            rz.setMenus(menus);

            //3.写入成功信息
            initInfo.put("success", "1");
            initInfo.put("msg", "初始化成功");
            initInfo.put("date", DateUtil.getCurrentDateTimeByFormat("yyyy-MM-dd"));
            //
        } catch (Exception ex) {
            initInfo.put("success", "0");
            initInfo.put("msg", ex.getMessage());
        } finally {
            rz.setInfo(initInfo);
        }

        return rz;
    }

    /**
     * 查询导航菜单--根据用户ID
     *
     * @param req
     * @return
     */
    private Map<String, Object> queryMenuNavByRoleId(HttpServletRequest req) {
    	UserDTO user=(UserDTO)req.getSession().getAttribute("user");
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<Map<String, Object>> yhavechildlist = new ArrayList<Map<String, Object>>();//有子菜单
        List<Map<String, Object>> nhavechildlist = new ArrayList<Map<String, Object>>();//无子菜单
        //String token = TokenUtils.getToken(req);
        //String in_userid = CacheUtils.hget(token, "GUID");//平台用户id
        //String in_usercode = CacheUtils.hget(token, "LOGON_NAME");//平台用户logon_name
        
        List<Map<String, Object>> returnlist = menuService.getMenuListByUserId(user.getLogon_name(), "");//查询当前用户所有权限
        returnlist = queryChildMenuNavByRoleId(returnlist, "0", user.getLogon_name());
        //将有子菜单和没有子菜单的数据分离开
        for (int i = 0; i < returnlist.size(); i++) {
            if (returnlist.get(i).containsKey("subs")) {
                yhavechildlist.add(returnlist.get(i));
            } else {
                nhavechildlist.add(returnlist.get(i));
            }

        }

        returnMap.put("items", nhavechildlist);
        returnMap.put("subs", yhavechildlist);

        return returnMap;

    }

    /**
     * 递归查询导航菜单
     *
     * @param
     * @param parentid
     * @return
     */
    public List<Map<String, Object>> queryChildMenuNavByRoleId(List<Map<String, Object>> list, String parentid, String in_userid) {

        List<Map<String, Object>> returnlist = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).get("parentid").equals(parentid)) {
                List<Map<String, Object>> childlist = new ArrayList<Map<String, Object>>();
                Map<String, Object> returnMap = new HashMap<String, Object>();
                String MENU_ID = list.get(i).get("menu_id").toString();
                returnMap.put("key", list.get(i).get("menu_id"));
                returnMap.put("text", list.get(i).get("name"));
                returnMap.put("uri", list.get(i).get("url"));
                returnMap.put("menuicon", list.get(i).get("menuicon"));
                returnMap.put("idx", list.get(i).get("displayorder"));
                //子菜单
                childlist = queryChildMenuNavByRoleId(list, MENU_ID, in_userid);

                if (childlist.size() > 0) {
                    List<Map<String, Object>> yhavechildlist = new ArrayList<Map<String, Object>>();//有子菜单
                    List<Map<String, Object>> nhavechildlist = new ArrayList<Map<String, Object>>();//无子菜单
                    //将有子菜单和没有子菜单的数据分离开
                    for (int j = 0; j < childlist.size(); j++) {
                    	 
                        if (isHaveSonMenu(list,childlist.get(j).get("key").toString())) {//有子菜单
                            yhavechildlist.add(childlist.get(j));
                        } else {
                            nhavechildlist.add(childlist.get(j));
                        }
                    }
                    returnMap.put("subs", yhavechildlist);
                    returnMap.put("items", nhavechildlist);
                }
                returnlist.add(returnMap);
            }


        }
        return returnlist;
    }
    /**
     * 是否含有子菜单
     * @param list
     * @param id
     * @return
     */
	public boolean isHaveSonMenu(List<Map<String, Object>> list,String id){
		
		boolean b=false;
		for(int i=0;i<list.size();i++){
			if(list.get(i).get("parentid").equals(id)){
				b= true;
				break;
			}
		}
		return b;
	}
}
