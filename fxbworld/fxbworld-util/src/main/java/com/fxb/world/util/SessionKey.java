package com.fxb.world.util;

public class SessionKey {

	public static final String SESSIONID_COOKIE_NAME = "jsessionid";
	public static final String USERNAME_SESSION_NAME = "sessUsername";
	public static final String USERID_SESSION_NAME = "sessUserId";
	public static final String USERINFO_SESSION_NAME = "sessUserInfo";

	public static final String SESSION_TOKEN = "token";
	public static final String SESSION_ORIGINALURL = "originalUrl";
	public static final String SESSION_SETCOOKIE = "setCookie";
	public static final String SESSION_LOGINED = "logined";
								
	/**
	 * 单位秒.
	 */
	public static final int TOKEN_EXPIRE_TIME = 60 * 30;     
	
	//redis常量
	public static final int COOKIE_EXPIRATIOIN_TIME = 60 * 30; // cookie过期时间
	
	
	public static final String REDIS_KEY_LEAF_MUEN_VALUE = "resource_muen_";
	public static final String REDIS_KEY_ALL_MUEN_VALUE = "resource_muen_all_";
	
	public static final String COOKIE_USER_NAME = "zhc_nk";
	public static final String ZHC_COOKIE_USER_NAME = "jsession";
	
	
	public static String  APP_TOKEN_PREFIX = "token_tsh_";
	 public static Integer APP_TOKEN_EXPIRE_TIME = 86400 * 7;		//一周

	public static final int REDIS_KEY_BASE_TIME = 60 * 60 * 24 * 3;
	
	
	public static final String CHARSET_UTF8 = "UTF-8";
	public static String PHONE_PREFIX = "phone_tsh_"; // 手机短信key
	
	public static String SECURITY_QUESTION_PREFIX = "security_question_tsh_"; // 密保key
	
	public static String AUC_IMAGE_TSH_CODE = "image_tsh_code"; // 手机短信key
	public static  final Integer PHONE_EXPIRE_TIME = 60 * 5; // 验证码时间2分钟

	public static final String REDIS_KEY_LEAF_LOGIN_ERROR_ID = "login_error_";
	public static final Integer LOGIN_CODE_EXPIRE_TIME = 60 * 60 * 24; // 错误密码时间间隔
	
	
	
	public static final String TSH_USER_LOGIN_PROTECTION = "user_login_protection_";//登录保护key
	
	public static final int PWD_ERROR_COUNT_MIX = 3;//密码错误次数
	public static final int PWD_ERROR_COUNT_MAX = 10;//密码错误
	
	public static  final Integer PHONE_EXPIRE_TIME_USER = 60 * 10; // 设置密保验证码时间5分钟
	
	public static final String REDIS_KEY_LEAF_LOGIN_TIME_STAMP = "login_time_stamp_";//登录是时间戳
	
	public static final Integer REDIS_KEY_LEAF_LOGIN_TIME_STAMP_VALID =60*60;//时间有效时间
	
	public static final String REDIS_KEY_LEAF_FORGETPWD_ERROR = "forgetPwd_error_";//密保找回密码错误次数
	public static final int FORGETPWD_ERROR_COUNT_MAX = 5;//密保找回密码错误次数超过5次该账户锁定
}
