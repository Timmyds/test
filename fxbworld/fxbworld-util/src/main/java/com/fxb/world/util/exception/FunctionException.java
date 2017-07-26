package com.fxb.world.util.exception;

import org.apache.commons.lang.StringUtils;

import com.fxb.world.util.bean.Result;

/**
 * 功能异常
 * 
 * @author lichangwen
 *
 */
public class FunctionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Result result;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public FunctionException(Result result, String message) {
		super(message);
		this.result = result;
		if (result != null && StringUtils.isNotBlank(message)) {
			this.result.setStatus(Result.STATUS_ERROR);
			this.result.setMsg(message);
		}
	}

	public FunctionException(Result result, Throwable e) {
		super(result.getStack(e), e);
		this.result = result;
	}

}
