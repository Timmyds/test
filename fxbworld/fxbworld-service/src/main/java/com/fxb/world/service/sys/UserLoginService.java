package com.fxb.world.service.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.fxb.world.dao.TResourcesMapper;
import com.fxb.world.dao.TRoleMapper;
import com.fxb.world.dao.TRoleResourcesMapper;
import com.fxb.world.dao.TUserMapper;
import com.fxb.world.dao.TUserResourcesMapper;
import com.fxb.world.dao.TUserRoleMapper;
import com.fxb.world.entity.TUser;
import com.fxb.world.entity.TUserRole;
import com.fxb.world.service.BaseService;
import com.fxb.world.service.RedisService;
import com.fxb.world.util.SerializeUtil;
import com.fxb.world.util.SessionKey;
import com.fxb.world.util.bean.Result;
import com.fxb.world.util.security.DigestUtils;
import com.fxb.world.util.security.UserInfo;
import com.fxb.world.util.security.UserResMenu;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author ds
 *
 */
@Service
@SuppressWarnings("unchecked")
public class UserLoginService extends BaseService<TUser> {
	@Resource
	TUserMapper tUserMapper;
	@Resource
	TRoleMapper tRoleMapper;
	@Resource
	TUserRoleMapper tUserRoleMapper;
	@Resource
	TRoleResourcesMapper tRoleResourcesMapper;
	@Resource
	TUserResourcesMapper tUserResourcesMapper;
	@Resource
	TResourcesMapper tResourcesMapper;

	@Resource
	ResourcesService resourcesService;

	@Resource
	RoleResourcesService roleResourcesService;

	@Resource
	RedisService redisService;

	/**
	 * 验证用户名和密码是否正确
	 * 
	 * @param result
	 * @param loginName
	 * @param password
	 * @param loginIp
	 * @return
	 * @throws Exception
	 */
	public Result validatePassword(Result result, String loginName, String password, String loginIp) throws Exception {
		UserInfo userVo = new UserInfo();
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {// 防止密码处理后产生空值
			result.setStatus(500);
			result.setMsg("账号不存在或密码错误！");
			result.setData(userVo);
			return result;
		}
		Example example = new Example(TUser.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("loginName", loginName);
		List<TUser> userList = selectByExample(example);
		if (userList == null || userList.size() <= 0) {
			// 记录登录错误次数
			Integer loginCode = (Integer) SerializeUtil.unserialize(
					redisService.get(SessionKey.REDIS_KEY_LEAF_LOGIN_ERROR_ID + loginName));
			if (loginCode == null) {
				loginCode = 0;
			}
			userVo.setErrorCount(Long.valueOf(loginCode + 1));
			redisService.setObject(SessionKey.REDIS_KEY_LEAF_LOGIN_ERROR_ID + loginName,
					SerializeUtil.serialize(loginCode + 1), SessionKey.LOGIN_CODE_EXPIRE_TIME);
			result.setStatus(500);
			result.setMsg("账号不存在或密码错误");
			result.setData(userVo);
			return result;
		}

		TUser user = (TUser) userList.get(0);
		if (user.getStatus() == 0) {
			result.setStatus(500);
			result.setMsg("账号已停用");
			result.setData(userVo);
			return result;
		}

		String digestPassword = DigestUtils.sha1Hex(password + user.getId());
		if (digestPassword != null && digestPassword.equals(user.getLoginPwd())) {// 密码验证
			
			// 登录成功清除redis缓存
			redisService.del(SessionKey.REDIS_KEY_LEAF_LOGIN_ERROR_ID + user.getLoginName());
			result = getUserInfo(result, userVo, user, loginIp);
			return result;
		}

		Long errorCount = user.getErrorCount();
		if (errorCount == null) {
			errorCount = (long) 0;
		}
		user.setErrorCount(errorCount + 1);
		// 记录登录错误次数
		Integer loginCode = (Integer) SerializeUtil.unserialize(
				redisService.get(SessionKey.REDIS_KEY_LEAF_LOGIN_ERROR_ID + loginName));
		if (loginCode == null) {
			loginCode = 0;
		}
		userVo.setErrorCount(Long.valueOf(loginCode + 1));
		redisService.setObject(SessionKey.REDIS_KEY_LEAF_LOGIN_ERROR_ID + loginName,
				SerializeUtil.serialize(loginCode + 1), SessionKey.LOGIN_CODE_EXPIRE_TIME);

		result.setStatus(500);
		result.setMsg("账号不存在或密码错误");
		result.setData(userVo);
		return result;
	}

	/**
	 * 登录成功获取信息
	 * 
	 * @param result
	 * @param userVo
	 * @param userPo
	 * @param loginIp
	 * @return
	 * @throws Exception
	 */
	private Result getUserInfo(Result result, UserInfo userVo, TUser user, String loginIp) throws Exception {// 获取管理员名称
		BeanUtils.copyProperties(user,userVo);
		userVo.setRoleType(user.getAccountType());
		userVo.setUserId(user.getId());

		List<TUserRole> queryForLis = tUserRoleMapper.getUserRoleByUserId(userVo.getUserId());
		if (queryForLis == null || queryForLis.isEmpty()) {
			result.setStatus(500);
			result.setMsg("无效账号！");
			result.setData(userVo);
			return result;
		}
		TUserRole sysUserRolePo = queryForLis.get(0);// 获取用户角色关系
		userVo.setRoleId(sysUserRolePo.getRoleId());
		result = getUserResourcesMenu(result, userVo, user);

		Long loginCount = user.getLoginCount();
		if (loginCount == null) {
			loginCount = (long) 0;
		}
		Date date = new Date();
		Date gmtdate = user.getLoginTime();// 上次登录时间
		if (gmtdate == null) {
			gmtdate = new Date();
		}
		userVo.setGmtLast(gmtdate);
		userVo.setLoginTime(date);
		userVo.setLastIp(user.getLoginIp());
		userVo.setLoginIp(loginIp);
		userVo.setLoginCount(loginCount + 1);
		user.setLoginCount(loginCount + 1);
		user.setLoginIp(loginIp);
		user.setLoginTime(date);
		user.setGmtLast(gmtdate);
		tUserMapper.selectCount(user);
		result.setData(userVo);
		return result;

	}

	/**
	 * 获取获取菜单权限
	 * 
	 * @param result
	 * @param userVo
	 * @param userPo
	 * @return
	 * @throws Exception
	 */
	private Result getUserResourcesMenu(Result result, UserInfo userVo, TUser user) throws Exception {// 获取管理员名称
		result = resourcesService.findResListByProSerivce(result, null);// 获取所有资源
		List<UserResMenu> allList = (List<UserResMenu>) result.getData();
		// 缓存用户登录的总菜单，再次登录之前不受影响
		redisService.setObject(SessionKey.REDIS_KEY_ALL_MUEN_VALUE + userVo.getUserId(),
				SerializeUtil.serialize(allList), SessionKey.REDIS_KEY_BASE_TIME);
		List<UserResMenu> loginList = new ArrayList<UserResMenu>();
		Integer roleType = userVo.getRoleType();
		userVo.setRoleType(roleType);

		result = roleResourcesService.queryRoleResourcesByRoleIdService(result, userVo.getRoleId(), userVo.getUserId());// 通过角色id获取资源id
		List<Long> resList = (List<Long>) result.getData();
		for (UserResMenu userResMenu : allList) {
			for (Long str : resList) {
				if (str.equals(userResMenu.getId())) {
					loginList.add(userResMenu);
				}
			}
		}

		userVo.setResourcesMenu(loginList);
		return result;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param result
	 * @param newPassword
	 * @param oldPassword
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Result resetPassword(Result result, String newPassword, String oldPassword, Long id) throws Exception {
		if (id == null) {
			String msg = "参数为空";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}
		TUser user = tUserMapper.selectByPrimaryKey(id);
		if (user == null) {
			String msg = "用户不存在";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}

		String digestPassword = DigestUtils.sha1Hex(newPassword + user.getId());
		if (digestPassword != null && digestPassword.equals(user.getLoginPwd())) {// 密码验证
			String newPassword1 = DigestUtils.sha1Hex(newPassword + user.getId());
			user.setLoginPwd(newPassword1);
			tUserMapper.updateByPrimaryKey(user);
			result.setData(1);
			return result;
		}

		String msg = "旧密码密码错误";
		result.setStatus(500);
		result.setMsg(msg);
		result.setData(null);
		return result;
	}

	/**
	 * 修改网点手机号
	 * 
	 * @param result
	 * @param phone
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Result updateShopUserPhone(Result result, String phone, Long id) throws Exception {

		int i = tUserMapper.updatePhone(phone, id);
		if (i > 0) {
			result.setStatus(200);
		} else {
			result.setStatus(500);
		}
		return result;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param result
	 * @param newPassword
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Result UpdatePassword(Result result, String newPassword, Long id) throws Exception {

		// 新密码加
		String digestPassword = DigestUtils.sha1Hex(newPassword + id);
		int i = tUserMapper.updatePwd(digestPassword, id);
		if (i > 0) {
			result.setStatus(200);
		} else {
			result.setStatus(500);
		}
		return result;
	}

	/**
	 * 根据账号手机号查询用户信息
	 * 
	 * @param result
	 * @param loginName
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public Result findUserByLoginNmaeToPhone(Result result, String loginName, String phone) throws Exception {
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(phone)) {
			result.setStatus(500);
			result.setMsg("参数为空！");
			return result;
		}
		TUser tuser = tUserMapper.getUserByLoginNameToPhone(loginName, phone);
		UserInfo userVo = new UserInfo();
		if (tuser == null) {
			String msg = "账号手机号不匹配！";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(userVo);
			return result;
		}
		userVo.setUserId(tuser.getId());
		userVo.setLoginName(tuser.getLoginName());
		result.setData(userVo);
		return result;
	}

}
