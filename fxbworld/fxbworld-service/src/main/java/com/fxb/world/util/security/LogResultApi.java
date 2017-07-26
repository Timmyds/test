package com.fxb.world.util.security;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fxb.world.util.bean.Result;


/**
 * 日志子类。
 * 
 * @author lichangwen
 * 
 */
public class LogResultApi implements Serializable {

	private static final long serialVersionUID = 1L;
	private String uuid;
	private String method;// 方法名称,如：save
	private String api;// 接口路径,如：AreaApi
	private String path;// com.tsh.dubbo.share.api.AreaApi
	private String params;// 参数
	private int count; // 第几步
	private String msg;
	private int status;
	private String logType; // 日志类型,info,warn,error
	private long longtimes = 0;// 用时
	private Date startDate;
	private Date endDate;
	private String errorMsg;

	/**
	 * 前置
	 * 
	 * @param uuid
	 * @param method
	 * @param api
	 * @param path
	 * @param params
	 * @param count
	 * @param msg
	 * @param status
	 * @param logType
	 * @param startDate
	 */
	public LogResultApi(String uuid, String method, String api, String path, String params, int count, String msg,
			int status, String logType, Date startDate) {
		this.uuid = uuid;
		this.method = method;
		this.api = api;
		this.path = path;
		this.params = params;
		this.count = count;
		this.msg = msg;
		this.status = status;
		this.logType = logType;
		this.startDate = startDate;
	}

	/**
	 * 异常
	 * 
	 * @param uuid
	 * @param path
	 * @param msg
	 * @param status
	 * @param logType
	 * @param errorMsg
	 */
	public LogResultApi(String uuid, String path, String msg, int status, String logType, Date endDate,
			String errorMsg) {
		this.uuid = uuid;
		this.path = path;
		this.msg = msg;
		this.status = status;
		this.logType = logType;
		this.endDate = endDate;
		this.errorMsg = errorMsg;
	}

	/**
	 * 最终
	 * 
	 * @param uuid
	 * @param path
	 * @param msg
	 * @param status
	 * @param endDate
	 */
	public LogResultApi(String uuid, String path, String msg, int status, Date endDate) {
		this.uuid = uuid;
		this.path = path;
		this.msg = msg;
		this.status = status;
		this.endDate = endDate;
		if(status!=Result.STATUS_OK){
			this.logType = "warn";
		}
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public long getLongtimes() {
		return longtimes;
	}

	public void setLongtimes(long longtimes) {
		this.longtimes = longtimes;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getContent() {
		String content = "\r\n第" + count + "步:接口方法=" + path + ",status=" + status + ",message=" + msg + ",日志类型="
				+ logType + ",耗时=" + longtimes;
		if (StringUtils.isNotBlank(params)) {
			content += "\r\n参数：" + params;
		}
		if (StringUtils.isNotBlank(errorMsg)) {
			content += "\r\n" + errorMsg;
		}
		return content;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
