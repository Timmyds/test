package com.fxb.world.util.bean;

public class RegexpConstants {

	public static String[] REGEXP_WORD = { "^[\\w\\-]+$", "大小写英文字母、数字、下划线" };// 英文字母+数字+下划线
	public static String[] REGEXP_LETTER = { "^[A-Za-z]+$", "大小写英文字母" };// 大小写英文字母
	public static String[] REGEXP_CHINESE = { "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$", "中文" };// 中文
	public static String[] REGEXP_CHINESE_AND_WORD = { "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w\\-]+$", "中文、大小写字母、数字、下划线" };// 中文+字母+数字+下划线
	public static String[] REGEXP_INTEGER = { "^-?[1-9]\\d*$", "正负整数" };// 整形
	public static String[] REGEXP_NUM = { "^([+-]?)\\d*\\.?\\d+$","数字格式：可以是正负数、小数" };// 数字
	public static String[] REGEXP_EMAIL = { "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$","电子邮箱格式：只允许大小写字母、数字、下划线" };// 电子邮箱
	public static String[] REGEXP_MOBILE = { "^1[34578]\\d{9}$","手机号码格式：13、14、15、17、18开头的11位号码" };// 手机号码
	public static String[] REGEXP_TEL = {"^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$","固定电话格式：区号-电话号码" };// 固话
	public static String[] REGEXP_POSTCODE = { "^\\d{6}$", "邮政编码格式：6位整数" };// 邮政编码
	public static String[] REGEXP_DATE = { "^\\d{4}\\-\\d{2}\\-\\d{2}$","日期格式：YYYY-MM-DD" };// 日期:2014-09-12

	public static String[] PRIMARY_KEY_ID = { "操作对象", "1", "19",REGEXP_INTEGER[0], REGEXP_INTEGER[1] };
	public static String[] VALIDATE_ENTITY_ID = { "操作对象", "1", "19",REGEXP_INTEGER[0], REGEXP_INTEGER[1] };
	public static String[] VALIDATE_USER_LOGIN_ACCOUNT = { "登录账号", "6", "64",REGEXP_WORD[0], REGEXP_WORD[1] };
	public static String[] VALIDATE_PHONE_LOGIN_ACCOUNT = { "登录账号", "11", "11",REGEXP_MOBILE[0], REGEXP_MOBILE[1] };
	public static String[] VALIDATE_USER_LOGIN_PASSWORD = { "登录密码", "6", "16",null, null };
	//public static String[] VALIDATE_REGISTER_ACCOUNT = { "注册账号", "6", "64",REGEXP_EMAIL[0], REGEXP_EMAIL[1] };
	public static String[] VALIDATE_BIND_ACCOUNT = { "绑定账号", "6", "64",REGEXP_WORD[0], REGEXP_WORD[1] };
	public static String[] VALIDATE_REGISTER_PASSWORD = { "注册密码", "6", "16",null, null };
	public static String[] VALIDATE_PASSWORD_OLD = { "旧密码", "6", "16", null,null };
	public static String[] VALIDATE_PASSWORD_NEW = { "新密码", "6", "16", null,null };
	public static String[] VALIDATE_EMAIL = { "电子邮箱", "6", "64",REGEXP_EMAIL[0], REGEXP_EMAIL[1] };
	//public static String[] VALIDATE_THIRD_PIC = { "用户头像地址", "0", "255", null,null };
	//public static String[] VALIDATE_THIRD_NICKNAME = { "昵称", "0", "64", null,null };

	public static String[] VALIDATE_ADDRESS_PHONE = { "手机号码", "11", "11",REGEXP_MOBILE[0], REGEXP_MOBILE[1] };
	public static String[] VALIDATE_ADDRESS_POSTCODE = { "邮政编码", "6", "6",REGEXP_POSTCODE[0], REGEXP_POSTCODE[1] };
	public static String[] VALIDATE_ADDRESS_DETAIL = { "详细街道地址", "1", "64",null, null };
	public static String[] VALIDATE_ADDRESS_PROVINCE = { "省份", "2", "6",REGEXP_CHINESE[0], REGEXP_CHINESE[1] };
	public static String[] VALIDATE_ADDRESS_CITY = { "城市", "2", "6",REGEXP_CHINESE[0], REGEXP_CHINESE[1] };
	public static String[] VALIDATE_ADDRESS_DISTRICT = { "区县", "2", "16",REGEXP_CHINESE[0], REGEXP_CHINESE[1] };
	//public static String[] VALIDATE_VERIFY_CODE = { "验证码", "4", "4",REGEXP_INTEGER[0], REGEXP_INTEGER[1] };
	public static String[] VALIDATE_USER_ID = { "用户ID", "1", "19",REGEXP_INTEGER[0], REGEXP_INTEGER[1] };
	
	public static String[] VALIDATE_FEEBACK_CONTENT = { "反馈内容", "1", "500",null, null };
	public static String[] VALIDATE_FEEBACK_CONTACT = { "联系方式", "0", "32",null, null };
	
	public static String[] VALIDATE_VERSION_CODE = { "版本号", "1", "19",REGEXP_INTEGER[0], REGEXP_INTEGER[1] };
	public static String[] VALIDATE_VERSION_TYPE = { "版本类型", "1", "1",REGEXP_INTEGER[0], REGEXP_INTEGER[1] };

	public static String[] VALIDATE_UPLOAD_FILE_SIZE = { "上传文件大小", "1", "19",REGEXP_INTEGER[0], REGEXP_INTEGER[1] };
	public static String[] VALIDATE_UPLOAD_FILE_CATEGORY = { "上传文件分类", "1","19", REGEXP_INTEGER[0], REGEXP_INTEGER[1] };
	public static String[] VALIDATE_UPLOAD_FILE_NAME = { "上传文件名称", "1", "64",null, null };



}
