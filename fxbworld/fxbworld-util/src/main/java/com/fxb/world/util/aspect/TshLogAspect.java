package com.fxb.world.util.aspect;

import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.alibaba.fastjson.JSON;
import com.fxb.world.util.StringUtil;
import com.fxb.world.util.bean.Result;
import com.fxb.world.util.exception.AopException;
import com.fxb.world.util.exception.FunctionException;
import com.fxb.world.util.exception.MultitransactionException;
import com.fxb.world.util.security.LogResult;
import com.fxb.world.util.security.LogResultApi;

@Aspect
public class TshLogAspect {
	int count = 0;

	@Pointcut("execution(* *..service*..*(..)) || execution(* *..api*..*(..)) || execution(* *..dao*..*(..))")
	public void pointcutExpression() {
	}

	/**
	 * 前置通知，在目标方法开始之前执行。
	 * 
	 * @param joinpoint
	 */
	@Before("pointcutExpression()")
	public void beforeMethod(JoinPoint joinpoint) {
		Date startDate = new Date();
		String method = joinpoint.getSignature().getName();
		String cls = joinpoint.getSignature().getDeclaringTypeName();
		String classMethod = cls + "." + method;
		Object[] paramArr = joinpoint.getArgs();
		String api = cls.substring(cls.lastIndexOf(".") + 1, cls.length());
		if (ArrayUtils.isEmpty(paramArr))
			return;
		Result result = null;
		if (paramArr[0] instanceof Result) {
			result = (Result) paramArr[0];
		} else {
			return;
		}
		result.setData(null);//重置data
		String params = JSON.toJSONString(ArrayUtils.subarray(paramArr, 1, paramArr.length));
		params = StringUtil.cleanSpecialChar(params);
		if (StringUtils.contains(classMethod.toLowerCase(), "password") || StringUtils.contains(classMethod.toLowerCase(), "pwd") || StringUtils.contains(classMethod.toLowerCase(), "addUser")) {
			params = "[密码相关接口不记录参数]";
		}
		int status = result.getStatus();
		String message = result.getMsg();
		String logType = "info";
		if(status!=Result.STATUS_OK){
			logType = "warn";
		}
		LogResult logResult = result.getLogResult();
		LogResultApi logApi = new LogResultApi(logResult.getUuid(), method, api, classMethod, params,
				logResult.getCount(), message, status, logType, startDate);
		result.beforePutApi(logApi);
//		 count++;
//		 System.out.println("beforeMethod=" + classMethod + "," + count);
	}

	/**
	 * 异常通知。目标方法出现异常的时候执行，可以访问到异常对象。
	 * 
	 * @param joinpoint
	 * @param e
	 */
	@AfterThrowing(value = "pointcutExpression()", throwing = "e")
	public void afterThrowing(JoinPoint pjd, Exception e) {
		Date endDate = new Date();
		String method = pjd.getSignature().getName();
		String cls = pjd.getSignature().getDeclaringTypeName();
		String classMethod = cls + "." + method;
		Object[] paramArr = pjd.getArgs();
		if (ArrayUtils.isEmpty(paramArr))
			return;
		Result result = null;
		if (paramArr[0] instanceof Result) {
			result = (Result) paramArr[0];
		} else {
			return;
		}
		int status = result.getStatus();
		String message = result.getMsg();
		String error = "";
		String logType = "error";
		Throwable ex1 = getFunctionException(e);
		if (ex1 instanceof FunctionException) {
			result = ((FunctionException) ex1).getResult();
			status = result.getStatus();
			message = result.getMsg();
			if (StringUtils.isBlank(result.getMsg()) || message.equals(Result.SUCCESS_MSG)) {
				message = ex1.getMessage();
			}
			error = result.getStack(ex1);
			logType = "warn";
		} else {
			Throwable ex2 = getMultitransactionException(e);
			if (ex2 instanceof MultitransactionException) {
				result = ((MultitransactionException) ex2).getResult();
				status = result.getStatus();
				message = result.getMsg();
				if (StringUtils.isBlank(result.getMsg()) || message.equals(Result.SUCCESS_MSG)) {
					message = ex2.getMessage();
				}
				error = result.getStackTrace(ex2);
				logType = "error";
			} else {
				error = result.getStackTrace(e);
				status = Result.STATUS_ERROR;
				logType = "error";
				if(StringUtils.isBlank(e.getMessage())){
					message = e.getMessage();
				}
			}
		}
		status = status == Result.STATUS_OK ? Result.STATUS_ERROR : status;
		if (StringUtils.isBlank(message) || message.equals(Result.SUCCESS_MSG)) {
			message = Result.FAIL_MSG;
		}
		result.setStatus(status);
		result.setMsg(message);
		LogResult logResult = result.getLogResult();
		LogResultApi logApi = new LogResultApi(logResult.getUuid(), classMethod, message, status, logType, endDate,
				error);
		result.throwingPutApi(logApi);
//		 count++;
//		 System.out.println("afterThrowing=" + classMethod + "," + count);
		throw new RuntimeException(new AopException(result, e));
	}

	public Throwable getFunctionException(Throwable e) {
		Throwable cause = e;
		if (e instanceof FunctionException) {
			return e;
		} else if (e.getCause() != null) {
			cause = getFunctionException(e.getCause());
		}
		return cause;
	}

	public Throwable getMultitransactionException(Throwable e) {
		Throwable cause = e;
		if (e instanceof MultitransactionException) {
			return e;
		} else if (e.getCause() != null) {
			cause = getMultitransactionException(e.getCause());
		}
		return cause;
	}

	/**
	 * 最终置通知，在目标方法执行之后开始执行，无论目标方法是否抛出异常。 在最终通知中不能访问目标方法执行的结果。
	 * 
	 * @param joinpoint
	 */
	@After("pointcutExpression()")
	public void afterMethod(JoinPoint joinpoint) {
		Date endDate = new Date();
		String method = joinpoint.getSignature().getName();
		String cls = joinpoint.getSignature().getDeclaringTypeName();
		String classMethod = cls + "." + method;
		Object[] paramArr = joinpoint.getArgs();
		if (ArrayUtils.isEmpty(paramArr))
			return;
		Result result = null;
		if (paramArr[0] instanceof Result) {
			result = (Result) paramArr[0];
		} else {
			return;
		}
		int status = result.getStatus();
		String message = result.getMsg();
		LogResult logResult = result.getLogResult();
		LogResultApi logApi = new LogResultApi(logResult.getUuid(), classMethod, message, status, endDate);
		result.afterPutApi(logApi);
//		 count++;
//		 System.out.println("afterMethod=" + classMethod + "," + count);
	}

}
