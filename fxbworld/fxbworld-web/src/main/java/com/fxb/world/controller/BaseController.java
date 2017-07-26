package com.fxb.world.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.fxb.world.redis.UserUtil;
import com.fxb.world.service.RedisService;
import com.fxb.world.util.bean.Result;
import com.fxb.world.util.exception.AopException;
import com.fxb.world.util.exception.FunctionException;
import com.fxb.world.util.security.LogResult;
import com.fxb.world.util.security.UserInfo;


/**
 * 
 * @author ds
 *
 */
@Scope("prototype")
@SuppressWarnings("all")
public class BaseController  {
	protected Logger logger = Logger.getLogger(getClass());
	@Resource RedisService redisService;
	/**
	 * 
	 * @param request
	 * @return
	 */
	public Result getResult(HttpServletRequest request) {
		UserInfo userInfo = UserUtil.getUserInfo(redisService,request);
		String teamName = "auc";
		Result result = new Result(request, userInfo, teamName);
		return result;
	}

	public Result error(Result result, Throwable e) {
		if (e instanceof FunctionException) {
			result = ((FunctionException) e).getResult();
		} else if (e instanceof AopException) {
			result = ((AopException) e).getResult();
		} else if (e instanceof RuntimeException) {
			Throwable re = e.getCause();
			if (re != null && re instanceof AopException) {
				result = ((AopException) re).getResult();
			} else {
				result.exception(result.getStackTrace(e));
			}
		} else {
			result.exception(result.getStackTrace(e));
		}
		return result;
	}
	public void send(Result result) {
		logger.info(result.getContent());
		try {
			if (result.getStatus() == 500 || result.getStatus() == 503) {
				LogResult logResult = result.getLogResult();
				logResult.setCode(result.getCode());
				
			}
		} catch (Exception e) {
			logger.error(e);
		} 
	}
}
