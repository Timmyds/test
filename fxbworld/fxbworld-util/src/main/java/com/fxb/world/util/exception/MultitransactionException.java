package com.fxb.world.util.exception;

import org.apache.commons.lang.StringUtils;

import com.fxb.world.util.bean.Result;

/**
 * 多事务异常
 * @author lichangwen
 *
 */
public class MultitransactionException extends Exception {
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

	public MultitransactionException(Result result,String message) {
		super(message);
		this.result = result;
		if (result != null && StringUtils.isNotBlank(message)) {
			this.result.setStatus(Result.STATUS_ERROR);
			this.result.setMsg(message);
		}
	}

	public MultitransactionException(Result result, Throwable e) {
		super(result.getStackTrace(e),e);
		this.result = result;
	}

}
