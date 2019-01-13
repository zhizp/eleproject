package com.ele.project.sysmanager.user.controller;

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


@Controller("/userController")
public class UserController {
	 private static Logger logger = LoggerFactory.getLogger(UserController.class);
	 /**
	 * 使用@Autowired也可以，@Autowired默认按类型装配
	 * @Resource 默认按名称装配，当找不到与名称匹配的bean才会按类型装配。
	 */
	 @Resource
	 private SysUserService sysUserService;
	 @Resource
	 private UserToRoleService userToRoleService;
	 
	 
	    /**
	     * 修改密码
	     * @param req
	     */
	    @RequestMapping("/updatePwd")
	    @ResponseBody
	    public int updatePwd(HttpServletRequest req) {
	        Map<String, Object> paramsMap = PageUtils.getParameters(req);
	        int ret=0;
	        if(StringUtil.isNotEmpty(paramsMap.get("userid"))){//管理员重置密码
	            paramsMap.put("user_pwd",MD5.getMD5(Constant.resetPassword.getBytes()));
	        }else{//个人修改密码
	            paramsMap.put("userid", paramsMap.get("userid"));
	            paramsMap.put("user_pwd",MD5.getMD5(paramsMap.get("user_pwd").toString().getBytes()));
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
	     * */
	    @RequestMapping("/userlist")
	    @ResponseBody
	    public PageDTO userlist(HttpServletRequest req){
	    	Map<String, Object> params=PageUtils.getParameters(req);
	    	Object token=params.get("token");
	    	PageDTO pagelist=null;
	    	List<Map<String, Object>> userlist=null;
	    	try{
		    	if(token!=null){
		    			userlist=sysUserService.queryUserList(params);	
		    			int rowsCount = sysUserService.queryUserListCount(params) ;
				    	if (userlist!=null&&userlist.size()>0) {
							for(int i=0;i<userlist.size();i++){						
								if (userlist.get(i).get("enabled").equals("1")) {
									userlist.get(i).put("enabled","启用");
								}else {
									userlist.get(i).put("enabled","注销");
								}
								String userid=userlist.get(i).get("userid").toString();
								Object name=getRoleById(userid);
								userlist.get(i).put("name", name);
							}
						}
				    	pagelist.setData(userlist);
		    			pagelist.setTotal(rowsCount);
		    		}
	    	  } catch (Exception e) {
		          logger.error(e.getMessage());
		      }
			return pagelist;
	    	
	    }
	 
	    
	    //去掉列表中单个角色所带，号
	    public Object getRoleById(String userid){
	    	Object name="";
	    	try{
		    	List<Map<String, Object>> list=userToRoleService.getRoleById(userid);
		    	if(list.size()>0){
		    	name=list.get(0).get("name").toString();
				for (int j = 1; j < list.size(); j++) {
					 name+=","+list.get(j).get("name").toString();					 
					}
		    	  }
	    	} catch (Exception e) {
		          logger.error(e.getMessage());
		      }
			return name;
	    }
	 
	    
	    /**
	     * 增加用户列表
	     * */
	    @RequestMapping("/insertUser")
	    @ResponseBody
	    public Map<String, Object> insertUser(HttpServletRequest req,UserDTO userDTO){
	    	Map<String, Object> returnMap=new HashMap<String, Object>();
			try{
				Map<String, Object> params=PageUtils.getParameters(req);
		    	UUID uuid=UUID.randomUUID();
				String guid=uuid.toString().replace("-", "");
				params.put("userid", guid);
		    	int i=sysUserService.saveUser(params);
		    	if(i<1){
	                returnMap.put("rtflag","用户信息保存失败！");
	                returnMap.put("rt",false);
	                return returnMap;
	            }
		    	String roleid=params.get("roleid").toString();
		    	int rflag=0;
		    	if (StringUtil.isNotEmpty(roleid)){
		    		rflag=saveRoleUser(guid, roleid);
		    		if(rflag<1) {
		    			returnMap.put("rt",false);
		    			returnMap.put("rtflag","用户角色信息保存失败！");
		    			return returnMap;
		    		}
				}
	            returnMap.put("rt",true);
		    	//添加业务操作日志
		        String[] s = new String[]{"新增用户", "用户管理", "insertUser", params.toString(), ""};
//		        logUtils.saveOprationLog(req, s);
			 } catch (Exception e) {
		          logger.error(e.getMessage());
		     }
			return returnMap;
	    }
	    
	    protected int saveRoleUser(String  guid,String roleid){
	    	String[] roleids=roleid.split(",");
	    	UserToRoleDTO rUser=new UserToRoleDTO();
	    	int rtflag=0;
	    	try{
		    	for (int i = 0; i < roleids.length; i++) {
					rUser.setRoleid(roleids[i]);
					rUser.setUserid(guid);
					rUser.setUr_id(StringUtil.getUUID());
					rtflag=userToRoleService.addUserToRole(rUser);
				}
	    	 } catch (Exception e) {
		          logger.error(e.getMessage());
		      }
	    	return rtflag;
	    }
	 
	    /**
	     * 
	     * 用户分配角色
	     * @throws ParseException 
	     * */
	    @RequestMapping("setUserRole")
	    @ResponseBody
	    public void setUserRole(HttpServletRequest req){
	    	
	    	Map<String, Object> params=PageUtils.getParameters(req);
	    	String userid=String.valueOf(params.get("userid")); 
	    	String roleid=String.valueOf(params.get("roleid"));
	    	
	    	try {
	    	  	
	    	if (StringUtil.isNotEmpty(userid)) {//删除该用户的所有角色
	    		userToRoleService.deleteUserToRole(userid);
			}
	     	if (StringUtil.isNotEmpty(roleid)) {//新增已勾选角色
	    		saveRoleUser(userid,roleid);
			}	
	    	
	    	 //添加业务操作日志
	        String[] s = new String[]{"用户分配角色", "用户管理", "setUserRole", params.toString(), ""};
	    	} catch (Exception e) {
		          logger.error(e.getMessage());
		      }
	    } 
	 
	

}
