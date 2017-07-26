package com.fxb.world.util.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 普通返回对象.
 * 
 * @author PJW
 * 
 */
public class ReturnDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 返回状态类型.200：成功 .
	 */
	protected int status;

	/**
	 * 返回描述信息内容..
	 */
	protected String msg = null;

	/**
	 * 日志编码
	 */
	private int code;

	/**
	 * 日志编码消息
	 */
	private String codeMsg;

	/**
	 * 返回的对像，正确时返回的结果.
	 */
	protected Object data;

	/**
	 * 构造函数.
	 */
	public ReturnDTO() {
	}

	/**
	 * 构造函数.
	 * 
	 * @param status
	 *            返回状态类型编号.
	 */
	public ReturnDTO(int status) {
		this.status = status;
	}

	public ReturnDTO(int status, String msg, int code,String codeMsg, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.code = code;
		this.codeMsg = codeMsg;
		this.data = data;
	}

	/**
	 * 成功返回值用简洁方式..
	 * 
	 * @return 返回值数据传输对象.
	 */
	public static ReturnDTO OK() {
		ReturnDTO dto = new ReturnDTO(ReturnStatusEnum.OK.getValue());
		return dto;
	}

	/**
	 * 成功返回值用简洁方式..
	 * 
	 * @param data
	 *            返回的数据对像.
	 * @return 返回值数据传输对象.
	 */
	public static ReturnDTO OK(Object data) {
		ReturnDTO dto = new ReturnDTO(ReturnStatusEnum.OK.getValue());
		dto.setData(data);
		return dto;
	}

	/**
	 * 成功返回值用简洁方式..
	 * 
	 * @param data
	 *            返回的数据对像.
	 * @param msg
	 *            返回的数据信息.
	 * @return 返回值数据传输对象.
	 */
	public static ReturnDTO OK(Object data, String msg) {
		ReturnDTO dto = new ReturnDTO(ReturnStatusEnum.OK.getValue());
		dto.setMsg(msg);
		dto.setData(data);
		return dto;
	}

	/**
	 * 失败返回值用简洁方式..
	 * 
	 * @param status
	 *            返回状态类型编号.
	 * @param msg
	 *            返回的数据异常信息.
	 * @return 返回值数据传输对象.
	 */
	public static ReturnDTO NO(int status, String msg) {
		ReturnDTO dto = new ReturnDTO(status);
		dto.setMsg(msg);
		return dto;
	}

	public void success(Object data) {
		this.status = ReturnStatusEnum.OK.getValue();
		this.data = data;
	}

	public void success(String msg, Object data) {
		this.status = ReturnStatusEnum.OK.getValue();
		this.msg = msg;
		this.data = data;
	}

	public void error(int status, String msg) {
		this.status = status;
		this.msg = msg;
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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
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