package com.fxb.world.redis;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.fxb.world.service.RedisService;
import com.fxb.world.util.CookieUtil;
import com.fxb.world.util.SerializeUtil;
import com.fxb.world.util.SessionKey;
import com.fxb.world.util.security.LoginUtil;
import com.fxb.world.util.security.UserInfo;
import com.fxb.world.util.security.UserResMenu;


public class UserUtil {

	public UserUtil(){}
	
	/**
	 * 获取登陆后的功能菜单
	 * @param request
	 * @return
	 */
	public static String getToken(HttpServletRequest request){
		String 	  token = request.getParameter("token");//屏端token
		if(StringUtils.isBlank(token)){
			token = CookieUtil.getCookie(SessionKey.ZHC_COOKIE_USER_NAME, request);
		}
		return token;
	}
	/**
	 * 获取用户所在系统中的sessionId
	 * @param request
	 * @return
	 */
	public static String getSessionId(HttpServletRequest request){
		String token = CookieUtil.getCookie(SessionKey.ZHC_COOKIE_USER_NAME, request);
		
		if(StringUtils.isBlank(token)){
			return null;
		}
		return token;
		
	}
	
	/**
	 * 获取登陆后的User信息
	 * @param request
	 * @return
	 */
	public static UserInfo getUserInfo(RedisService redisService,HttpServletRequest request){
		String 	  token = request.getParameter("token");//屏端token
		if(StringUtils.isBlank(token)){
			token = LoginUtil.checkLoginByCookie(request, SessionKey.ZHC_COOKIE_USER_NAME);//web端token
		}
		
		
		if(StringUtils.isBlank(token)){
			return null;
		}
		UserInfo loginInfo = (UserInfo) SerializeUtil.unserialize(redisService.get(token));
		
		if(null == loginInfo){
			return null;
		}
		loginInfo.setResourcesMenu(null);
		loginInfo.setSessionId(token);
		
		return loginInfo;
		
	}
	/**
	 * 获取登陆后的功能菜单
	 * @param request
	 * @return
	 */
	public static List<UserResMenu> getUserResMenu(RedisService redisService,HttpServletRequest request){
		String 	  token = request.getParameter("token");//屏端token
		if(StringUtils.isBlank(token)){
			token = CookieUtil.getCookie(SessionKey.ZHC_COOKIE_USER_NAME, request);
		}
		if(StringUtils.isBlank(token)){
			return null;
		}
		UserInfo loginInfo = (UserInfo) SerializeUtil.unserialize(redisService.get(token));
		
		if(null == loginInfo){
			return null;
		}
		
		return loginInfo.getResourcesMenu();
	}
	

	/**
	 * 获取登陆后的UserId
	 * @param request
	 * @return
	 */
	public static Long getUserId(RedisService redisService,HttpServletRequest request){
		

		UserInfo loginInfo = getUserInfo(redisService,request);
		
		if(null != loginInfo)
			return loginInfo.getUserId();
		
		return null;
		
	}
	
	/**
	 * 获取登陆后的角色类型
	 * @param request
	 * @return
	 */
	public static Integer getUserRoleType(RedisService redisService,HttpServletRequest request){
		

		UserInfo loginInfo = getUserInfo(redisService,request);
		
		if(null != loginInfo)
			return loginInfo.getRoleType();
		
		return null;
		
	}



	/**
	 * 获取用户名称
	 * @param request
	 * @return
	 */
	public static String getUserName(RedisService redisService,HttpServletRequest request){

		UserInfo loginInfo = getUserInfo(redisService,request);
		
		if(null != loginInfo)
			return loginInfo.getLoginName();
		return null;
	}
	

}
