package com.fxb.world.util.bean;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fxb.world.util.security.LogResult;
import com.fxb.world.util.security.LogResultApi;
import com.fxb.world.util.security.UserInfo;

/**
 * 接口返回结果对象，该对象禁止返回给页面直接使用，页面分页返回Pagination或者PageReturnDTO,普通结果返回ReturnDTO,
 * 返回数据成功与否使用status和msg字段。
 * 
 * @author lichangwen
 * 
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String FAIL_MSG = "操作失败";
	public static final String SUCCESS_MSG = "操作成功";
	public static final String MOCK_MSG = "当前服务无法正常响应请求";
	public static int STATUS_ERROR = 500;
	public static int STATUS_OK = 200;
	public static int STATUS_MOCK = 503;//服务器无法正确处理请求
	private String msg = SUCCESS_MSG;
	private int status = STATUS_OK;
	private Object data;
	private String content;
	private String errorMsg;
	private LogResult logResult;
	private UserInfo userInfo;
	private Object obj;
	private int code;// 日志编码
	private String codeMsg;// 日志编码消息

	public Result() {
		super();
		this.logResult = new LogResult();
	}

	@SuppressWarnings("static-access")
	public Result(HttpServletRequest request, UserInfo userInfo, String teamName) {
		this.userInfo = userInfo;
		this.logResult = new LogResult(teamName, this.getRequstURL(request), this.getClientIp(request),
				this.getRequestParams(request), userInfo);
	}
	
	/**
	 * @return
	 */
	public Result Mock() {
		this.status = STATUS_MOCK;
		this.msg = MOCK_MSG;
		this.data = null;
		return this;
	}
	
	/**
	 * @param data
	 * @return
	 */
	public Result Mock(Object data) {
		this.status = STATUS_MOCK;
		this.msg = MOCK_MSG;
		this.data = data;
		return this;
	}

	/**
	 * @param status
	 * @param msg
	 * @return
	 */
	public Result NO(int status, String msg) {
		this.status = status;
		this.msg = msg;
		this.data = null;
		return this;
	}

	/**
	 * @param msg
	 * @return
	 */
	public Result NO(String msg) {
		this.msg = msg;
		this.status = STATUS_ERROR;
		this.data = null;
		return this;
	}
	
	/**
	 * @param msg
	 * @return
	 */
	public Result Mock(String msg) {
		this.status = STATUS_MOCK;
		this.msg = msg;
		this.data = null;
		return this;
	}

	/**
	 * @param status
	 * @param msg
	 * @param data
	 * @return
	 */
	public Result NO(int status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
		return this;
	}
	
	/**
	 * @param status
	 * @param msg
	 * @param data
	 * @return
	 */
	public Result Mock(String msg, Object data) {
		this.status = STATUS_MOCK;
		this.msg = msg;
		this.data = data;
		return this;
	}

	/**
	 * @param status
	 * @param msg
	 * @param data
	 * @return
	 */
	public Result NO(int status, String msg, int code, String codeMsg, Object data) {
		this.status = status;
		this.msg = msg;
		this.code = code;
		this.codeMsg = codeMsg;
		this.data = data;
		return this;
	}
	
	/**
	 * @param status
	 * @param msg
	 * @param code
	 * @param codeMsg
	 * @param data
	 * @return
	 */
	public Result Mock(String msg, int code, String codeMsg, Object data) {
		this.status = STATUS_MOCK;
		this.msg = msg;
		this.code = code;
		this.codeMsg = codeMsg;
		this.data = data;
		return this;
	}

	/**
	 * @param data
	 * @return
	 */
	public Result OK(Object data) {
		this.status = ReturnStatusEnum.OK.getValue();
		this.data = data;
		return this;
	}

	/**
	 * @param msg
	 * @param data
	 * @return
	 */
	public Result OK(String msg, Object data) {
		this.status = ReturnStatusEnum.OK.getValue();
		this.msg = msg;
		this.data = data;
		return this;
	}

	/**
	 * 前置
	 * 
	 * @param api
	 */
	public void beforePutApi(LogResultApi api) {
		logResult.beforePut(api);
	}

	/**
	 * 异常
	 * 
	 * @param api
	 */
	public void throwingPutApi(LogResultApi api) {
		logResult.throwingPut(api);
	}

	/**
	 * 最终(不管是否异常)
	 * 
	 * @param api
	 */
	public void afterPutApi(LogResultApi api) {
		logResult.afterPutApi(api);
	}

	public void log(String msgInfo) {
		if (StringUtils.isNotBlank(msgInfo)) {
			logResult.setContent(logResult.getContent() + "\r\n" + msgInfo);
		}
	}

	public void exception(String errorMessage) {
		this.status = status == STATUS_OK ? STATUS_ERROR : status;
		this.msg = msg.equals(SUCCESS_MSG) ? FAIL_MSG : msg;
		this.errorMsg = errorMessage;
		logResult.setLogType("error");
		if (StringUtils.isNotBlank(errorMessage)) {
			logResult.setContent(logResult.getContent() + "\r\n" + errorMessage);
		}
	}

	public String getStack(Throwable cause) {
		String message = cause.toString();
		StackTraceElement[] stes = cause.getStackTrace();
		if (stes!=null&&stes.length>0) {
			StackTraceElement stack = stes[0];
			message += "\r\n" + stack.getClassName() + "." + stack.getMethodName() + "(" + stack.getFileName() + ":"
					+ stack.getLineNumber() + ")";
		}
		return message;
	}

	public String getStackTrace(Throwable cause) {
		String stackTrace = cause.toString();
		StackTraceElement[] stes = cause.getStackTrace();
		for (StackTraceElement stack : stes) {
			stackTrace += "\r\n" + stack.getClassName() + "." + stack.getMethodName() + "(" + stack.getFileName() + ":"
					+ stack.getLineNumber() + ")";
		}
		if (cause.getCause() != null) {
			stackTrace = getStackTrace(cause.getCause());
		}
		return stackTrace;
	}

	public String getRequstURL(HttpServletRequest request) {
		if (request == null)
			return "";
		String path = request.getRequestURI();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		return basePath;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getRequestParams(HttpServletRequest request) {
		if (request == null)
			return "";
		if (StringUtils.contains(StringUtils.trimToEmpty(getRequstURL(request)).toLowerCase(), "password")) {
			return "[密码相关请求不记录参数]";
		}
		Map map = new HashMap();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (StringUtils.isNotBlank(paramValue)) {
					map.put(paramName, paramValue);
				}
			}
		}
		return map.toString();
	}

	// 获取客户端IP地址,考虑到代理、反向代理等
	public static String getClientIp(HttpServletRequest request) {
		if (request == null)
			return "";
		String ip = request.getHeader("x-forwarded-for");
		System.setProperty("java.net.preferIPv4Stack", "true");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ip = inet.getHostAddress();
			}
		}
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@SuppressWarnings("unchecked")
	public <X> X getData() {
		return (X) data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getContent() {
		logResult.colse(this.status, this.msg, this.errorMsg);
		this.content = logResult.getContent();
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@SuppressWarnings("unchecked")
	public <X> X getObj() {
		return (X) obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public ReturnDTO DTO() {
		return new ReturnDTO(this.getStatus(), this.getMsg(), this.getCode(), this.codeMsg, this.getData());
	}

	public void setDTO(int status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public void setDTO(int status, String msg, int code, String codeMsg, Object data) {
		this.status = status;
		this.msg = msg;
		this.code = code;
		this.codeMsg = codeMsg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public LogResult getLogResult() {
		return logResult;
	}

	public void setLogResult(LogResult logResult) {
		this.logResult = logResult;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getCodeMsg() {
		return codeMsg;
	}

	public void setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}