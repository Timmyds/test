package com.fxb.world.util.security;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fxb.world.util.SessionKey;

/**
 * 
 * 类名 WebUtil.java 用户登陆信息工具类 创建日期 2014年6月7日 作者 zhangx
 */
@Component
public class LoginUtil {

	private static Logger logger = Logger.getLogger(LoginUtil.class);

	public LoginUtil() {
	}

	/**
	 * 将用户信息写入到Session里 用户信息
	 * 
	 */
	public static Boolean saveUserInfo(UserInfo loginInfo, String sessionID, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Cookie joinSessionId =getNewCookie(request, SessionKey.ZHC_COOKIE_USER_NAME, sessionID, SessionKey.COOKIE_EXPIRATIOIN_TIME);
			response.addCookie(joinSessionId);
			return true;
			// 保存相关用户信息到Cookie里
			//return LoginInfoWriteCookie(loginInfo, sessionID, request, response);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("write the user login info into session occured excepton:" + e);
			return false;
		}

	}

	

	

	public static String checkLoginByCookie(HttpServletRequest request,String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {

				if (cookie != null && cookie.getName().equals(cookieName)) {

					return cookie.getValue();

				}

			}

		}
		return null;
	}



	/**
	 * 清除所有的登录信息Session及Cookie的值
	 * 
	 * @param request
	 * @param response
	 */

	public static void clearLoginInfo(HttpServletRequest request, HttpServletResponse response) {
		// 清空Cookie
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				Cookie c = getNewCookie(request, cookie.getName(), null, 0);
				response.addCookie(c);

			}
		}
		// 清空Session
		request.getSession().invalidate();
	}

	public static Cookie getNewCookie(HttpServletRequest request, String cookieName, String cookieValue, int iMaxAge) {

		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setPath("/");// 设置成跟写入cookies一样的
		String domain = getServerDomain(request);
		if (!StringUtils.isEmpty(domain)) {
			cookie.setDomain(domain);// 设置成跟写入cookies一样的
		}

		cookie.setMaxAge(-1);
		return cookie;
	}

/*	public static Cookie getNewJoinSessionId(HttpServletRequest request, int iMaxAge, String sessionID) {

		Cookie cookie = new Cookie("JSESSIONID", sessionID);
		cookie.setPath("/");// 设置成跟写入cookies一样的
		String domain = getServerDomain(request);
		if (!StringUtils.isEmpty(domain)) {
			cookie.setDomain(domain);// 设置成跟写入cookies一样的
		}
		cookie.setMaxAge(iMaxAge);
		return cookie;
	}*/

	// 根据名字获取Cookie
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	// 根据名字获取Base64 decode之后的cookie值
	public static String getCookieByNameBase64Decode(HttpServletRequest request, String name) {
		String cookieValueDecode = null;
		Cookie userCookie = getCookieByName(request, name);

		try {
			if (userCookie != null) {
				cookieValueDecode = new String(Base64.decodeBase64(userCookie.getValue().getBytes()));
			}
		} catch (Exception e) {
			cookieValueDecode = null;
		}
		return cookieValueDecode;
	}

	 
	
	// 获取客户端IP地址,考虑到代理、反向代理等
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		System.setProperty("java.net.preferIPv4Stack", "true");// 针对WIN7系统的IPV6地址为首选的情况而添加的，指定获取ipv4地址
		// System.setProperty("java.net.preferIPv6Addresses", "true");
		// 指定获取ipv6地址

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			
			if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")){
				//根据网卡取本机配置的IP  
                InetAddress inet=null;  
                try {  
                    inet = InetAddress.getLocalHost();  
                } catch (UnknownHostException e) {  
                    e.printStackTrace();  
                }  
                ip= inet.getHostAddress();  
			}
		}
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
        if(ip!=null && ip.length()>15){ //"***.***.***.***".length() = 15  
            if(ip.indexOf(",")>0){  
            	ip = ip.substring(0,ip.indexOf(","));  
            }  
        }  
        return ip;
		

	}

	// 获取主域名.xxx.xxx 如www.baidu.com的主域名为.baidu.com
	public static String getServerDomain(HttpServletRequest request) {
		String serverName = request.getServerName();
		StringBuffer domain = new StringBuffer();
		if (serverName != null) {
			String[] nameParts = serverName.split("\\.");
			if (nameParts.length >= 2) {
				//domain.append(".");
				domain.append(nameParts[nameParts.length - 2]);
				domain.append(".");
				domain.append(nameParts[nameParts.length - 1]);
			} else {
				//domain.append(".");
				domain.append(serverName);
			}
			return domain.toString();
		} else
			return null;
	}

/**
	 * 写入cookie
	 * 
	 * @param userInfo
	 * @param request
	 * @param response
	 * @return
	 */
	public static Boolean writeCookie(String cookieName, String cookieValue, HttpServletResponse response,
			HttpServletRequest request) {
		Boolean result = true;
		try {
			Cookie ck = getNewCookie(request, cookieName, cookieValue, SessionKey.COOKIE_EXPIRATIOIN_TIME);
			response.addCookie(ck);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	/*	
	 * 得到cookie值的集合
	 */
	public static String[] getArrayOfCookieValue(String cookieName, HttpServletRequest request) {
		String base64DecodeStr = getCookieByNameBase64Decode(request, cookieName);
		if (base64DecodeStr != null)
			return base64DecodeStr.split(":");
		return null;
	}

	// 讲string ip地址转换为long
	public static long ipToLong(HttpServletRequest request) {

		String strIp = getClientIpAddr(request);

		long[] ip = new long[4];
		// 先找到IP地址字符串中.的位置
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		// 将每个.之间的字符串转换成整型
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	// 再提供一个粗略的将long转换为IP字符串的JAVA方法：
	public static String longToIP(long longIp) {
		StringBuffer sb = new StringBuffer("");
		// 直接右移24位
		sb.append(String.valueOf((longIp >>> 24)));
		sb.append(".");
		// 将高8位置0，然后右移16位
		sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
		sb.append(".");
		// 将高16位置0，然后右移8位
		sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
		sb.append(".");
		// 将高24位置0
		sb.append(String.valueOf((longIp & 0x000000FF)));
		return sb.toString();
	}
}

