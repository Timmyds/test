package com.fxb.world.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * cookie操作
 * 
 */
public class CookieUtil {
	protected static final Log logger = LogFactory.getLog(CookieUtil.class);

	/**
	 * 设置cookie</br>
	 * 
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param request
	 *            http请求
	 * @param response
	 *            http响应
	 */
	public static void setCookie(String name, String value, HttpServletRequest request, HttpServletResponse response) {
		int maxAge = -1;
		CookieUtil.setCookie(name, value, maxAge, request, response);
	}

	/**
	 * 设置cookie</br>
	 * 
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            最大生存时间
	 * @param request
	 *            http请求
	 * @param response
	 *            http响应
	 */
	public static void setCookie(String name, String value, int maxAge, HttpServletRequest request, HttpServletResponse response) {
		boolean httpOnly = false;
		CookieUtil.setCookie(name, value, maxAge, httpOnly, request, response);
	}

	/**
	 * 设置cookie</br>
	 * 
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            最大生存时间
	 * @param httpOnly
	 *            cookie的路径
	 * @param request
	 *            http请求
	 * @param response
	 *            http响应
	 */
	public static void setCookie(String name, String value, int maxAge, boolean httpOnly, HttpServletRequest request, HttpServletResponse response) {
		String domain = request.getServerName();
		// String domain = serverName;
		setCookie(name, value, maxAge, httpOnly, domain, response);
	}

	/**
	 * 设置cookie</br>
	 * 
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            最大生存时间
	 * @param httpOnly
	 *            cookie的路径
	 * @param request
	 *            http请求
	 * @param response
	 *            http响应
	 */
	public static void setTopDomainCookie(String name, String value, int maxAge, boolean httpOnly, HttpServletRequest request,
			HttpServletResponse response) {
		String domain = getServerDomain(request);
		setCookie(name, value, maxAge, httpOnly, domain, response);
	}

	public static void setCookie(String name, String value, int maxAge, boolean httpOnly, String domain, HttpServletResponse response) {
		assertNotEmpty(name, "cookie名称不能为空.");
		assertNotNull(value, "cookie值不能为空.");

		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(domain);
		cookie.setMaxAge(maxAge);
		if (httpOnly) {
			cookie.setPath("/");
		} else {
			cookie.setPath("/");
		}
		// TODO ahai 所有cookie都要写这些值吗?
		response.addCookie(cookie);
	}

	/**
	 * 获取cookie的值</br>
	 * 
	 * @param name
	 *            cookie名称
	 * @param request
	 *            http请求
	 * @return cookie值
	 */
	public static String getCookie(String name, HttpServletRequest request) {
		assertNotEmpty(name, "cookie名称不能为空.");

		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			if (name.equalsIgnoreCase(cookies[i].getName())) {
				return cookies[i].getValue();
			}
		}
		return null;
	}

	/**
	 * 删除cookie</br>
	 * 
	 * @param name
	 *            cookie名称
	 * @param request
	 *            http请求
	 * @param response
	 *            http响应
	 */
	public static void deleteCookie(String name, HttpServletRequest request, HttpServletResponse response) {
		assertNotEmpty(name, "cookie名称不能为空.");
		CookieUtil.setCookie(name, "", -1, false, request, response);
	}

	/**
	 * 删除cookie</br>
	 * 
	 * @param name
	 *            cookie名称
	 * @param request
	 *            http请求
	 * @param response
	 *            http响应
	 */
	public static void deleteCookie(String name, String domain, HttpServletResponse response) {
		assertNotEmpty(name, "cookie名称不能为空.");
		CookieUtil.setCookie(name, "", -1, false, domain, response);
	}
	/**
	 * 判断一个字符串是否为空</br>
	 * 
	 * @param str
	 *            字符串
	 * @param message
	 *            如果为空,抛出的异常信息
	 */
	public static void assertNotEmpty(String str, String message) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 判断一个对象是否为空</br>
	 * 
	 * @param obj
	 *            对象
	 * @param message
	 *            如果为空,抛出的异常信息
	 */
	public static void assertNotNull(Object obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message);
		}
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
}
