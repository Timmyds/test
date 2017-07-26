package com.fxb.world.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fxb.world.redis.UserUtil;
import com.fxb.world.service.RedisService;
import com.fxb.world.service.sys.ResourcesService;
import com.fxb.world.service.sys.RoleResourcesService;
import com.fxb.world.service.sys.UserLoginService;
import com.fxb.world.service.vo.LoginVo;
import com.fxb.world.util.Base64;
import com.fxb.world.util.SerializeUtil;
import com.fxb.world.util.SessionKey;
import com.fxb.world.util.bean.Result;
import com.fxb.world.util.bean.ReturnDTO;
import com.fxb.world.util.security.DigestUtils;
import com.fxb.world.util.security.LoginUtil;
import com.fxb.world.util.security.UserInfo;
import com.fxb.world.util.security.UserResMenu;


/**
 * 登录
 * 
 * @author ds
 * 
 */
@Controller
@RequestMapping("/usercentre/")
@SuppressWarnings("unchecked")
public class UserLoginController extends BaseController {//

	@Resource
	UserLoginService userLoginService;
	@Resource
	ResourcesService resourcesService;
	@Resource
	RoleResourcesService roleResourcesService;
	@Resource RedisService redisService;
	
/*	@Resource
	LoginLogService loginLogService;
	@Resource
	OperatePwdLogService operatePwdLogService;*/

	

	/**
	 * 
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO login(LoginVo vo, HttpServletRequest request, HttpServletResponse response) {
		Result result = this.getResult(request);
		String homeUrl = "/views/home.html";
		try {
			if (StringUtils.isEmpty(vo.getLoginName()) || StringUtils.isEmpty(vo.getPassword())) {
				return result.NO(500, "登录账号或密码不能为空").DTO();
			}
			String password = new String(Base64.decode(vo.getPassword()));
			String loginName = new String(Base64.decode(vo.getLoginName().trim()));
			int i = password.lastIndexOf('_');
			if (i >= 0) {
				String randomDate = password.substring(i + 1, password.length());
				password = password.substring(0, i);
				// 时间戳
			/*	Long randomNum = (Long) SerializeUtil.unserialize(redisService.get(SessionKey.REDIS_KEY_LEAF_LOGIN_TIME_STAMP + loginName));
				if (randomNum == null) {
					randomNum = 0L;
				}
				if (StringUtils.isNotBlank(randomDate) && !randomDate.equals(randomNum.toString())) {
					return result.NO(500, "验证失败，请重试！", null).DTO();
				}*/
			}

			result = loginCheck(request, vo, loginName);
			if (result.getStatus() == 500) {
				return result.DTO();
			}

			String loginIp = LoginUtil.getClientIpAddr(request);
			result = userLoginService.validatePassword(result, loginName, password, loginIp);

			if (result.getData() == null || result.getStatus() == 500) {
				return result.NO(500, result.getMsg(), result.getData()).DTO();
			}
			UserInfo userInfo = (UserInfo) result.getData();
			String token = "jsession_" + DigestUtils.sha1Hex(userInfo.getLoginName() + new Date().toString());
			userInfo.setToken(token);
			userInfo.setSessionId(token);
			
				if (!VerifiedCodeController.checkPwd(password)) {// 简单密码验证，设置为第一次登陆时需修改密码
					userInfo.setLoginCount(Long.valueOf(1));
				}
			
			System.err.println(userInfo.toString());
			// 生成token
			LoginUtil.saveUserInfo(userInfo, token, request, response);
			redisService.setObject(token, SerializeUtil.serialize(userInfo), SessionKey.COOKIE_EXPIRATIOIN_TIME);

			// 记录登录日志
			//loginLogService.saveSysLoginLog(result, userInfo, "浏览器登录");

		} catch (Exception e) {
			e.printStackTrace();
			result = this.error(result, e);
			return result.NO(500, "程序异常").DTO();
		}finally {
			send(result);
		}
		return result.OK(homeUrl).DTO();
	}


	/**
	 * 登录检查
	 * 
	 * @param request
	 * @param vo
	 * @param loginName
	 * @return
	 * @throws Exception
	 */
	private Result loginCheck(HttpServletRequest request, LoginVo vo, String loginName) throws Exception {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(redisService,request);
		if (loginInfo != null && !loginName.equals(loginInfo.getLoginName())) {
			return result.NO(500, "你已经登录了账号：" + loginInfo.getLoginName() + ",如需切换账号,请关闭浏览器重新登录！", vo);
		}
		Integer loginerror = (Integer) SerializeUtil.unserialize(redisService.get(SessionKey.REDIS_KEY_LEAF_LOGIN_ERROR_ID + loginName));
		if (loginerror == null) {
			loginerror = 0;
		}
		if (loginerror >= SessionKey.PWD_ERROR_COUNT_MAX) {
			vo.setErrorCount(loginerror);
			result.setData(vo);
			return result.NO(500, "登录错误超过" + SessionKey.PWD_ERROR_COUNT_MAX + "次，为保证账号安全，账号已被锁定,24小时后自动解锁或者通过找回密码方式解锁！", vo);
		} else if (loginerror >= SessionKey.PWD_ERROR_COUNT_MIX && loginerror < SessionKey.PWD_ERROR_COUNT_MAX) {
			if (StringUtils.isBlank(vo.getVerifiedCode())) {
				vo.setErrorCount(loginerror);
				return result.NO(500, "登录错误超过" + SessionKey.PWD_ERROR_COUNT_MIX + "次，请输入验证码", vo);
			}
			String verifiedCode = (String) SerializeUtil.unserialize(redisService.get(SessionKey.AUC_IMAGE_TSH_CODE + loginName));
			if (StringUtils.isBlank(verifiedCode) || !vo.getVerifiedCode().equals(verifiedCode)) {
				vo.setErrorCount(loginerror);
				result.setData(vo);
				return result.NO(500, "验证码错误");
			}
		}
		return result.OK(null);
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatePassWord.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO updatePwd(HttpServletRequest request) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(redisService,request);
		if (loginInfo == null || loginInfo.getUserId() == null) {// 用户验证
			return result.NO(500, "登录超时！").DTO();
		}
		try {
			String oldPassword = request.getParameter("oldPassword");
			String newPassword = request.getParameter("newPassword");
			if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
				return result.NO(500, "用户密码为空").DTO();
			}

			String oldPassword1 = new String(Base64.decode(oldPassword));
			String newPassword1 = new String(Base64.decode(newPassword));
			if (!VerifiedCodeController.checkPwd(newPassword1)) {
				return result.NO(500, "密码过于简单,").DTO();
			}
			Long userId = loginInfo.getUserId();

			result = userLoginService.resetPassword(result, newPassword1, oldPassword1, userId);
			if (result.getStatus() == 200) {
				//operatePwdLogService.saveSysPwdOperateLog(result, loginInfo, userId, OperateTypeEnum.pc.getSign(), "用户修改密码");
				result.setData("/views/login.html");
			}
		} catch (Exception e) {
			result = this.error(result, e);
		} 

		return result.DTO();
	}

	/**
	 * 修改密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/recoverPassword.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO recoverPassword(HttpServletRequest request) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(redisService,request);
		if (loginInfo == null || loginInfo.getUserId() == null) {// 用户验证
			return result.NO(500, "登录超时！").DTO();
		}
		try {
			String newPassword = request.getParameter("newPassword");
			if (StringUtils.isBlank(newPassword)) {
				return result.NO(500, "用户密码为空").DTO();
			}

			String password = new String(Base64.decode(newPassword));
			if (!VerifiedCodeController.checkPwd(password)) {
				return result.NO(500, "密码过于简单").DTO();
			}
			Long userId = loginInfo.getUserId();
			result = userLoginService.UpdatePassword(result, password, userId);
			if (result.getStatus() == 200) {
				//operatePwdLogService.saveSysPwdOperateLog(result, loginInfo, userId, OperateTypeEnum.pc.getSign(), "用户设置密码（简单密码）");
				result.setData("/views/login.html");
			}
		} catch (Exception e) {
			result = this.error(result, e);
		}

		return result.DTO();
	}

	/**
	 * 退出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/outLogin.do")
	public String outLogin(HttpServletRequest request, HttpServletResponse response) {
		Result result = this.getResult(request);
		String sessionId = UserUtil.getSessionId(request);
		try {
			if (StringUtils.isBlank(sessionId)) {
				return "redirect:/views/login.html";
			}
			redisService.del(sessionId);
			LoginUtil.clearLoginInfo(request, response);
		} catch (Exception e) {
			result = this.error(result, e);
		}
		return "redirect:/views/login.html";
	}


	

	

	/**
	 * 获取用户菜单权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getWebMenu.do", method = RequestMethod.GET)
	@ResponseBody
	public ReturnDTO getWebMenu(HttpServletRequest request) {
		Result result = this.getResult(request);
		try {
			
			List<UserResMenu>  list= UserUtil.getUserResMenu(redisService, request);
			
			if(null == list||list.size()<=0){
				return result.NO(500, "").DTO();
			}
			
			
			result.setData(list);
		} catch (Exception e) {
			result = this.error(result, e);
		} 
		return result.DTO();
	}

	/**
	 * 
	 * @param account
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/randomNum.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO randomNum(String account, HttpServletRequest request) {
		Result result = this.getResult(request);
		try {
			Date date = new Date();
			result.setData(date.getTime());
			redisService.setObject(SessionKey.REDIS_KEY_LEAF_LOGIN_TIME_STAMP + account, SerializeUtil.serialize(date.getTime()), SessionKey.REDIS_KEY_LEAF_LOGIN_TIME_STAMP_VALID);
		} catch (Exception e) {
			result = this.error(result, e);
		} 
		return result.DTO();
	}

	/**
	 * 获取用户
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo.do", method = RequestMethod.GET)
	@ResponseBody
	public UserInfo getUserInfo(HttpServletRequest request) {
		Result result = this.getResult(request);
		UserInfo userVo = new UserInfo();
		try {
			UserInfo loginInfo = UserUtil.getUserInfo(redisService,request);
			if (loginInfo == null || loginInfo.getUserId() <= 0) {// 用户验证
				return null;
			}
			userVo.setLoginName(loginInfo.getLoginName());
			userVo.setLoginCount(loginInfo.getLoginCount());
			userVo.setErrorCount(loginInfo.getErrorCount());
			userVo.setRoleType(loginInfo.getRoleType());
		} catch (Exception e) {
			result = this.error(result, e);
		} 
		return userVo;
	}

	/**
	 * 检查是否登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checklogin.do")
	@ResponseBody
	public boolean checklogin(HttpServletRequest request) {
		Result result = this.getResult(request);
		boolean bl = true;
		try {
			UserInfo loginInfo = UserUtil.getUserInfo(redisService,request);
			if (loginInfo == null || loginInfo.getUserId() == null) {// 用户验证
				return false;
			}
			List<UserResMenu> allList = (List<UserResMenu>) SerializeUtil.unserialize(redisService.get(SessionKey.REDIS_KEY_ALL_MUEN_VALUE + loginInfo.getUserId()));
			if (allList == null || allList.isEmpty()) {
				return false;
			}

		} catch (Exception e) {
			result = this.error(result, e);
		}
		return bl;
	}

	/**
	 * 检查登录是否异常
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/abnormalLoginCheck.do")
	@ResponseBody
	public ReturnDTO abnormalLoginCheck(HttpServletRequest request) {
		Result result = this.getResult(request);
		try {
			UserInfo loginInfo = UserUtil.getUserInfo(redisService,request);
			if (loginInfo == null || loginInfo.getUserId() == null) {// 用户验证
				return result.DTO();
			}
			//result = loginLogService.checkLoginAddress(result, loginInfo.getLoginName());

		} catch (Exception e) {
			result = this.error(result, e);
		} 
		return result.DTO();
	}

	
	/**
	 * 登录错误10次，账号锁定发送短信
	 * 
	 * @param request
	 * @param loginName
	 * @throws Exception
	 * @author ds
	 * @date 2017年1月9日
	 * @version 3.1.0
	 */
/*	public void sendUserMes(HttpServletRequest request, String loginName) throws Exception {
		Result result = this.getResult(request);
		if (StringUtils.isBlank(loginName)) {
			return;
		}
		result = loginLogService.getUserVoByloginName(result, loginName);
		UserVo userVo = result.getData();
		if (userVo != null) {
			String phone = userVo.getPhone();
			Map<String, Object> map = new HashMap<>();
			map.put("mobile", phone);
			map.put("content", "尊敬的淘实惠用户，您的账号：" + loginName + "由于登录时连续输错10次密码，已经被系统自动冻结，如果非您本人操作，请尽快联系淘实惠客服，防止资料信息外泄。");
			String channelType = tshDiamondClient.getConfig("auc_sms_channelType");
			SmsResult smsResult = SmsSendFactory.createSms(channelType).sendSms(map);
			if (smsResult.getCode() != 1) {
				Date date = new Date();
				logger.info("(找回密码)手机号：" + phone + ",发送时间：" + date + "," + smsResult);
			}

		}
	}*/

}
