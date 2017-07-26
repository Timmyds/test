package com.fxb.world.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxb.world.entity.TUser;
import com.fxb.world.redis.UserUtil;
import com.fxb.world.service.RedisService;
import com.fxb.world.service.sys.UserService;
import com.fxb.world.service.vo.UserVo;
import com.fxb.world.util.StringUtil;
import com.fxb.world.util.bean.OperateTypeEnum;
import com.fxb.world.util.bean.Pagination;
import com.fxb.world.util.bean.Result;
import com.fxb.world.util.bean.ReturnDTO;
import com.fxb.world.util.exception.FunctionException;
import com.fxb.world.util.security.DigestUtils;
import com.fxb.world.util.security.UserInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author ds
 * @date 2017年1月5日
 * @version 3.1.0
 */
@SuppressWarnings("unchecked")
@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserController extends BaseController {

	@Resource
	UserService userService;
/*	@Resource
	SysUserRoleService sysUserRoleService;*/
	
	/*@Resource
	OperatePwdLogService operatePwdLogService;
	@Resource
	OperatePhoneLogService operatePhoneLogService;*/
/*	@Resource
	SysUserResourcesService sysUserResourcesService;
	@Resource
	SysResourcesService sysResourcesService;
	*/
	
	@Resource RedisService redisService;
	/**
	 * 分页查询所有的用户
	 * 
	 * @param request
	 * @param name
	 * @param status
	 * @param roleType
	 * @param userGroup
	 * @param rows
	 * @param page
	 * @return
	 * @author ds
	 * @date 2017年1月5日
	 * @version 3.1.0
	 */
	@RequestMapping(value = "/getPageForGUserInfo.do", method = RequestMethod.GET)
	@ResponseBody
	public Object getPageForGUserInfo(HttpServletRequest request, String name, Integer status, Integer roleType, Integer userGroup, int rows, int page) {
		Result result = this.getResult(request);
		Pagination pagination = Pagination.createPagination(0, null);
		UserInfo loginInfo = UserUtil.getUserInfo(redisService,request);
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return pagination;
		}
		String loginName = "";
		if (StringUtils.isNotBlank(name)) {
			loginName = StringUtil.unicodeToString(StringUtils.trim(name)).replace("%", "\\%");
		}
		try {
			/*Page<UserPo> pages = new Page<>();
			pages.setPageNo(page);
			pages.setPageSize(rows);*/
			UserVo userVo = new UserVo();
			userVo.setLoginName(loginName);
			userVo.setUserName(loginName);
			//userVo.setRoleType(loginInfo.getRoleType());
			//userVo.setRoleId(Long.valueOf(roleType));
			if (status >= 0) {
				userVo.setStatus(status);
			}


			result = userService.findUserVByUserVo(result, userVo,  rows,  page);
			if (result.getData() != null) {
				PageInfo<TUser> pageInfo = result.getData();
				pagination.setTotal(pageInfo.getTotal());
				pagination.setRows(pageInfo.getList());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return pagination;
	}

	/**
	 * 停用用户
	 * 
	 * @param request
	 * @param id
	 * @param status
	 * @return
	 * @author ds
	 * @date 2017年1月5日
	 * @version 3.1.0
	 *//*
	@RequestMapping(value = "/frozenGUser.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO frozenGUser(HttpServletRequest request, String id, Integer status) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return result.DTO();
		}
		try {
			result = userService.frozenUserById(result, id, status, loginInfo.getUserId());
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.DTO();
	}

	*//**
	 * 添加用户
	 * 
	 * @param request
	 * @param userVo
	 * @return
	 * @author ds
	 * @date 2017年1月5日
	 * @version 3.1.0
	 *//*
	@RequestMapping(value = "/addUser.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO addUser(HttpServletRequest request, UserVo userVo) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return result.DTO();
		}
		try {
			String randomPwd = StringTools.randomCode6();// 随机密码
			UserPo userPo = new UserPo();
			PropertyUtils.copyProperties(userPo, userVo);
			userPo.setCreateBy(loginInfo.getUserId());
			userPo.setCreateDate(new Date());
			userPo.setUpdateBy(loginInfo.getUserId());
			userPo.setUpdateDate(new Date());
			userPo.setLoginPwd(DigestUtils.sha1Hex(randomPwd));
			userPo.setMasteraccId(loginInfo.getMasteraccId());
			userPo.setBelongId(loginInfo.getBelongId());
			userPo.setBizId(loginInfo.getBizId());
			userPo.setManager(false);
			userPo.setIsCertificate(true);
			userPo.setNet(true);
			userPo.setAccountType(loginInfo.getRoleType());
			userPo.setIsProtection(0);
			// 添加用户
			result = userService.saveUserInfo(result, userPo, userVo, loginInfo, randomPwd);
			if (result.getStatus() == 200 && StringUtils.isNotBlank(userVo.getPhone())) {
				Map<String, Object> map = new HashMap<>();
				map.put("mobile", userVo.getPhone());
				map.put("content", "尊敬的用户，您的淘实惠后台账号的随机登录码为：" + randomPwd + "，请登录后尽快到安全中心修改登录码。详咨4000-558-518");
				String channelType = tshDiamondClient.getConfig("auc_sms_channelType");
				SmsResult smsResult = SmsSendFactory.createSms(channelType).sendSms(map);
				if (smsResult.getCode() != 1) {
					Date date = new Date();
					logger.info("(添加用户)手机号：" + userVo.getPhone() + ",发送时间：" + date + "," + smsResult);
				}
			}

		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.DTO();
	}

	*//**
	 * 编辑用户
	 * 
	 * @param request
	 * @param userVo
	 * @return
	 *//*
	@RequestMapping(value = "/updateUser.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO updateUser(HttpServletRequest request, UserVo userVo) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return result.DTO();
		}
		try {
			if (userVo == null || userVo.getId() == null) {
				return result.DTO();
			}
			// 修改用户信息
			result = userService.updateUser(result, userVo, loginInfo.getUserId());
			if (userVo.getPhone() != null) {// 手机号码被修改
				operatePhoneLogService.saveSysPhoneOperateLog(result, loginInfo, userVo.getId(), OperateTypeEnum.pc.getSign(), "管理员修改手机号码（修改用户）", userVo.getPhone());
			}
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.DTO();
	}

	*//**
	 * 查看用户
	 * 
	 * @param request
	 * @param userId
	 * @return
	 *//*
	@RequestMapping(value = "/findUser.do", method = RequestMethod.GET)
	@ResponseBody
	public ReturnDTO findUser(HttpServletRequest request, String userId) {
		Result result = this.getResult(request);
		try {
			if (StringUtils.isBlank(userId)) {
				result.setMsg(MessageResource.PARAMETERS_ERROR);
				throw new FunctionException(result, "");
			}
			UserVo userVo = (UserVo) userService.findUserByUserId(result, Long.valueOf(userId)).getData();
			if (null != userVo) {
				result.setData(userVo);
			}
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.DTO();
	}

	*//**
	 * 校验账号是否唯一
	 * 
	 * @param request
	 * @param loginName
	 * @return
	 *//*
	@RequestMapping(value = "/findUserIsRegister.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO findUserIsRegister(HttpServletRequest request, String loginName) {
		Result result = this.getResult(request);
		try {
			result = userService.findUserIsRegister(result, loginName);
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.DTO();
	}

	*//**
	 * 重置密码
	 * 
	 * @param request
	 * @param userId
	 * @return
	 *//*
	@RequestMapping(value = "/resetPwd.do", method = RequestMethod.POST)
	@ResponseBody
	public Object checkFunds(HttpServletRequest request, String userId) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return result;
		}
		try {
			if (StringUtils.isBlank(userId)) {
				result.setMsg(MessageResource.PARAMETERS_ERROR);
				throw new FunctionException(result, "");
			}
			String randomPwd = StringTools.randomCode6();// 随机密码
			result = userService.resetUserPwd(result, randomPwd, Long.valueOf(userId), loginInfo.getUserId(), loginInfo.getBizId());
			if (result.getStatus() == 200) {
				UserPo userPo = result.getData();
				result = operatePwdLogService.saveSysPwdOperateLog(result, loginInfo, Long.valueOf(userId), OperateTypeEnum.pc.getSign(), "系统管理员重置密码");
				Map<String, Object> map = new HashMap<>();
				map.put("mobile", userPo.getPhone());
				map.put("content", "尊敬的用户，您的淘实惠后台账号登陆码被重置为：" + randomPwd + "，请登录后尽快到安全中心修改登录码。详咨4000-558-518");
				String channelType = tshDiamondClient.getConfig("auc_sms_channelType");
				SmsResult smsResult = SmsSendFactory.createSms(channelType).sendSms(map);
				if (smsResult.getCode() != 1) {
					Date date = new Date();
					logger.info("(重置密码)手机号：" + userPo.getPhone() + ",发送时间：" + date + "," + smsResult);
				}
			}

		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result;
	}

	*//**
	 * 获取用户权限
	 * 
	 * @param request
	 * @param userId
	 * @return
	 * @author ds
	 * @date 2017年2月9日
	 * @version 3.1.0
	 *//*
	@RequestMapping(value = "/userAuthority.do", method = RequestMethod.POST)
	@ResponseBody
	public Object userAuthority(HttpServletRequest request, String userId) {
		Result result = this.getResult(request);
		try {
			if (StringUtils.isBlank(userId)) {
				result.setStatus(500);
				result.setMsg("参数错误！");
				return result.DTO();
			}
			// 权限id集合
			Integer status=0;//为限制权限
			List<Long> list = (List<Long>) sysUserResourcesService.querySysUserResourcesByuserIdService(result, Long.valueOf(userId),status).getData();
			result.setData(list);
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.getData();
	}

	*//**
	 * 获取用户角色权限
	 * 
	 * @param request
	 * @param userId
	 * @return
	 * @author ds
	 * @date 2017年2月9日
	 * @version 3.1.0
	 *//*
	@RequestMapping(value = "/roleAuthority.do", method = RequestMethod.POST)
	@ResponseBody
	public Object roleAuthority(HttpServletRequest request, String userId) {
		Result result = this.getResult(request);
		try {
			if (StringUtils.isBlank(userId)) {
				result.setStatus(500);
				result.setMsg("参数错误！");
				return result.DTO();
			}
			// 权限id集合
			List<Long> list = (List<Long>) userService.queryRoleAuthorityByUserId(result, Long.valueOf(userId)).getData();
			result.setData(list);
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.getData();
	}

	*//**
	 * 用户权限更新
	 * 
	 * @param request
	 * @param userId
	 * @param authority
	 * @return
	 * @author ds
	 * @date 2017年2月9日
	 * @version 3.1.0
	 *//*
	@RequestMapping(value = "/updateUserAuthority.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO updateUserAuthority(HttpServletRequest request, String userId, String status, String authority) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return result.DTO();
		}
		try {
			if (StringUtils.isBlank(userId) || StringUtils.isBlank(authority) ||StringUtils.isBlank(status)) {
				result.setStatus(500);
				result.setMsg("参数错误！");
				return result.DTO();
			}
			JSONArray parseArray = JSONArray.parseArray(authority);

			*//** 清空权限 **//*
			result = sysUserResourcesService.delSysUserResourcesService(result, Long.valueOf(userId),Integer.parseInt(status));
			*//** 保存新权限 **//*
			List<SysUserResourcesPo> poList = new ArrayList<>();
			for (int j = 0; j < parseArray.size(); j++) {
				JSONObject jsonObject = parseArray.getJSONObject(j);
				SysUserResourcesPo sysUserResourcesPo = new SysUserResourcesPo();
				sysUserResourcesPo.setUserId(Long.valueOf(userId));
				sysUserResourcesPo.setResourcesId(jsonObject.getLong("menuId"));
				sysUserResourcesPo.setStatus(Integer.parseInt(status));
				poList.add(sysUserResourcesPo);
			}

			if (!poList.isEmpty()) {
				result = sysUserResourcesService.saveSysUserResourcesListService(result, poList);
			}

		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.DTO();
	}

	*//**
	 * 分页查询所有的商家用户
	 * 
	 * @param request
	 * @param name
	 * @param roleType
	 * @param rows
	 * @param page
	 * @return
	 * @author ds
	 * @date 2017年2月23日
	 * @version 3.1.0
	 *//*
	@RequestMapping(value = "/getPageForBusinessUser.do", method = RequestMethod.GET)
	@ResponseBody
	public Object getPageForBusinessUser(HttpServletRequest request, String name, Integer roleType, int rows, int page) {
		Result result = this.getResult(request);
		Pagination pagination = Pagination.createPagination(0, null);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo == null || !RoleTypeEnum.platform.getSign().equals(loginInfo.getRoleType())) {
			result.setStatus(500);
			result.setMsg("获取数据失败！");
			return pagination;
		}
		String loginName = "";
		if (StringUtils.isNotBlank(name)) {
			loginName = StringTools.unicodeToString(StringUtils.trim(name)).replace("%", "\\%");
		}
		try {
			Page<UserPo> pages = new Page<>();
			pages.setPageNo(page);
			pages.setPageSize(rows);
			UserVo userVo = new UserVo();
			userVo.setLoginName(loginName);
			userVo.setUserName(loginName);
			userVo.setRoleType(roleType);

			result = userService.findUserByAccountType(result, userVo, pages);
			if (result.getData() != null) {
				pagination = result.getData();
			}
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return pagination;
	}

	*//**
	 * 获取菜单
	 * 
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/getUserMenuList.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray authorityList(HttpServletRequest request, String userId, String roleType) {
		Result result = this.getResult(request);
		JSONArray jsonArray = new JSONArray();
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return new JSONArray();
		}
		Integer rType = 0;
		Long uId = 0L;
		if (StringUtils.isNotBlank(roleType)) {
			rType = Integer.parseInt(roleType);
		}
		if (StringUtils.isNotBlank(roleType)) {
			uId = Long.valueOf(userId);
		}
		try {
			result=sysResourcesService.findResourcesByRoleTypeToUserId(result, rType, uId);
			jsonArray=result.getData();
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return jsonArray;
	}
	*//**
	 * 获取用户限制权限
	 * 
	 * @param request
	 * @param userId
	 * @return
	 * @author ds
	 * @date 2017年2月9日
	 * @version 3.1.0
	 *//*
	@RequestMapping(value = "/userLimitAuthority.do", method = RequestMethod.POST)
	@ResponseBody
	public Object userLimitAuthority(HttpServletRequest request, String userId) {
		Result result = this.getResult(request);
		try {
			if (StringUtils.isBlank(userId)) {
				result.setStatus(500);
				result.setMsg("参数错误！");
				return result.DTO();
			}
			// 权限id集合
			Integer status=1;//为限制权限
			List<Long> list = (List<Long>) sysUserResourcesService.querySysUserResourcesByuserIdService(result, Long.valueOf(userId),status).getData();
			result.setData(list);
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.getData();
	}*/
}
