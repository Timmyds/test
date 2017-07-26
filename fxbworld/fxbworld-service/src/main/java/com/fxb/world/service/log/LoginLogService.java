/*package com.fxb.world.service.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.usercentre.dao.UserDao;
import com.tsh.usercentre.dao.log.SysLoginLogDao;
import com.tsh.usercentre.dao.security.SysSecurityAnswerDao;
import com.tsh.usercentre.po.log.SysLoginLogPo;
import com.tsh.usercentre.po.security.SysSecurityAnswerPo;
import com.tsh.usercentre.po.user.UserPo;
import com.tsh.usercentre.util.FindIpUtil;
import com.tsh.usercentre.vo.log.LoginAddressVo;
import com.tsh.usercentre.vo.log.SysLoginLogVo;
import com.tsh.usercentre.vo.user.SecurityUserVo;
import com.tsh.usercentre.vo.user.UserVo;

*//**
 * 
 * @author ds
 * 
 *//*
@Service
public class LoginLogService {
	@Resource
	SysLoginLogDao sysLoginLogDao;
	@Resource
	SysSecurityAnswerDao sysSecurityAnswerDao;
	@Resource
	UserDao userDao;

	*//**
	 * 添加
	 * 
	 * @param result
	 * @param loginInfo
	 * @param loginMode
	 * @return
	 * @throws Exception
	 *//*
	@Async
	public Result saveSysLoginLog(Result result, UserInfo loginInfo, String loginMode) throws Exception {
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("参数为空！");
			result.setData(null);
			return result;
		}

		SysLoginLogPo sysLoginLogPo = new SysLoginLogPo();
		PropertyUtils.copyProperties(sysLoginLogPo, loginInfo);
		sysLoginLogPo.setLoginAddress("");
		sysLoginLogPo.setLoginMode(loginMode);
		sysLoginLogPo.setLoginDate(new Date());

		result = sysLoginLogDao.findSysLoginLogId(result, loginInfo.getUserId());
		List<Long> list = result.getData();

		if (list != null && list.size() > 98) {// 记录保留100条，超出后删除
			for (int i = 98; i < list.size(); i++) {
				Long id = list.get(i);
				result = sysLoginLogDao.delSysLoginLog(result, id);
			}
			result.setStatus(200);
		}
		result = sysLoginLogDao.saveSysLoginLog(result, sysLoginLogPo);
		return result;
	}

	*//**
	 * 根据id查询20条记录
	 * 
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result findSysLoginLog(Result result, Long userId) throws Exception {
		if (userId == null) {
			String msg = "参数为空";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}
		result = sysLoginLogDao.findSysLoginLog(result, userId);
		List<SysLoginLogPo> list = result.getData();
		List<SysLoginLogVo> listVo = new ArrayList<SysLoginLogVo>();
		SysLoginLogVo sysLoginLogVo = null;
		for (SysLoginLogPo sysLoginLogPo : list) {
			sysLoginLogVo = new SysLoginLogVo();
			if (StringUtils.isBlank(sysLoginLogPo.getLoginAddress())) {
				sysLoginLogPo.setLoginAddress(FindIpUtil.getIpAddress(sysLoginLogPo.getLoginIp()));
			}
			PropertyUtils.copyProperties(sysLoginLogVo, sysLoginLogPo);

			listVo.add(sysLoginLogVo);

		}
		result.setData(listVo);
		return result;
	}

	*//**
	 * 
	 * @param result
	 * @param id
	 * @return
	 * @throws Exception
	 *//*
	public Result getSecurityUser(Result result, Long id) throws Exception {
		UserPo user = (UserPo) userDao.getUser(result, id).getData();
		if (user == null) {
			result.setData(null);
			return result;
		}
		int securityGrade = 0;
		SecurityUserVo securityUserVo = new SecurityUserVo();
		PropertyUtils.copyProperties(securityUserVo, user);
		if ("e10adc3949ba59abbe56e057f20f883e".equals(user.getLoginPwd())) {// 密码判断安全等级
			securityGrade += 1;
		} else {
			securityGrade += 3;
		}
		if (StringUtils.isNotBlank(user.getPhone())) {// 手机号绑定
			securityGrade += 3;
			String phone = user.getPhone();
			String tmp = phone.substring(3, 7);
			phone = phone.replace(tmp, "****");
			securityUserVo.setPhone(phone);
		}
		if (user.getIsProtection() == 1) {// 开启登录保护
			securityGrade += 2;
		}
		result = sysSecurityAnswerDao.getSysSecurityAnswerList(result, id);
		List<SysSecurityAnswerPo> list = result.getData();
		if (list != null && list.size() > 0) {// 设置密保
			securityGrade += 2;
			securityUserVo.setIsSecurity(1);
		}

		securityUserVo.setSecurityGrade(securityGrade);
		*//**securityUserVo.setMail(user.getMail());*//*
		securityUserVo.setLoginAddress(FindIpUtil.getIpAddress(securityUserVo.getLoginIp()));
		result.setData(securityUserVo);
		return result;
	}

	*//**
	 * 
	 * @param result
	 * @param id
	 * @return
	 * @throws Exception
	 *//*
	public Result getUserVo(Result result, Long id) throws Exception {
		UserPo user = (UserPo) userDao.getUser(result, id).getData();
		if (user == null) {
			result.setData(null);
			return result;
		}

		UserVo userVo = new UserVo();
		PropertyUtils.copyProperties(userVo, user);
		userVo.setPassword(null);
		result.setData(userVo);
		return result;
	}

	*//**
	 * 
	 * @param result
	 * @param loginName
	 * @return
	 * @throws Exception
	 *//*
	public Result getUserVoByloginName(Result result, String loginName) throws Exception {
		UserPo user = (UserPo) userDao.getUserByLoginName(result, loginName).getData();
		if (user == null) {
			result.setData(null);
			return result;
		}

		UserVo userVo = new UserVo();
		PropertyUtils.copyProperties(userVo, user);
		userVo.setPassword(null);
		result.setData(userVo);
		return result;
	}

	*//**
	 * 
	 * @param result
	 * @param id
	 * @param isProtection
	 * @return
	 * @throws Exception
	 *//*
	public Result updateProtectionLogin(Result result, Long id, int isProtection) throws Exception {
		UserPo user = (UserPo) userDao.getUser(result, id).getData();
		if (user == null) {
			result.setMsg("用户不存在");
			result.setStatus(500);
			return result;
		}
		user.setIsProtection(isProtection);
		result.setData(null);
		return result;
	}

	*//**
	 * 
	 * @param result
	 * @param sysLoginLogVo
	 * @param page
	 * @return
	 * @throws Exception
	 *//*
	public Result findLoginLog(Result result, SysLoginLogVo sysLoginLogVo, Page<SysLoginLogPo> page) throws Exception {
		if (sysLoginLogVo == null) {
			result.setMsg("查询对象不能为空");
			result.setStatus(500);
			result.setData(null);
			throw new FunctionException(result, "查询不能为空");
		}
		SysLoginLogPo sysLoginLogPo = new SysLoginLogPo();
		PropertyUtils.copyProperties(sysLoginLogPo, sysLoginLogVo);
		result = sysLoginLogDao.findLoginLog(result, sysLoginLogPo, page);
		Pagination findPagination = result.getData();
		if (findPagination != null) {
			List<SysLoginLogPo> list = (List<SysLoginLogPo>) findPagination.getRows();
			for (SysLoginLogPo loginLogPo : list) {
				if (StringUtils.isBlank(loginLogPo.getLoginAddress())) {
					loginLogPo.setLoginAddress(FindIpUtil.getIpAddress(loginLogPo.getLoginIp()));
				}
			}
		}
		return result;
	}

	*//**
	 * 
	 * @param result
	 * @param loginName
	 * @return
	 * @throws Exception
	 *//*
	public Result checkLoginAddress(Result result, String loginName) throws Exception {
		if (StringUtils.isBlank(loginName)) {
			result.setMsg("查询对象不能为空");
			result.setStatus(500);
			result.setData(null);
			return result;
		}
		LoginAddressVo loginAddressVo = new LoginAddressVo();
		SysLoginLogPo sysLoginLogPo = new SysLoginLogPo();
		sysLoginLogPo.setLoginName(loginName);
		Page<SysLoginLogPo> page = new Page<SysLoginLogPo>();
		page.setPageNo(1);
		page.setPageSize(5);
	
		result = sysLoginLogDao.findLoginLog(result, sysLoginLogPo, page);
		Pagination findPagination = result.getData();
		if (findPagination == null || findPagination.getRows() == null) {
			loginAddressVo.setIsChange(0);
			result.setData(loginAddressVo);
			return result;
		}
		List<SysLoginLogPo> list = (List<SysLoginLogPo>) findPagination.getRows();
		SysLoginLogPo sysLoginLogPo1 = list.get(0);
		SysLoginLogPo sysLoginLogPo2 = list.get(1);
		if(sysLoginLogPo1==null||sysLoginLogPo2==null){
			loginAddressVo.setIsChange(LoginAddressVo.LoginStatus.Normal.getCode());
			result.setData(loginAddressVo);
			return result;
		}
		if (sysLoginLogPo1.getLoginIp()!=null&&sysLoginLogPo1.getLoginIp().equals(sysLoginLogPo2.getLoginIp())) {
			loginAddressVo.setIsChange(LoginAddressVo.LoginStatus.Normal.getCode());
			result.setData(loginAddressVo);
			return result;
		} 
		loginAddressVo.setLastLoginAddress(FindIpUtil.getAddressByIP(sysLoginLogPo2.getLoginIp()));
		loginAddressVo.setLastLoginDate(sysLoginLogPo2.getLoginDate());
		loginAddressVo.setLastLoginIp(sysLoginLogPo2.getLoginIp());
		loginAddressVo.setLastLoginMode(sysLoginLogPo2.getLoginMode());
		loginAddressVo.setLoginAddress(FindIpUtil.getAddressByIP(sysLoginLogPo1.getLoginIp()));
		loginAddressVo.setLoginDate(sysLoginLogPo1.getLoginDate());
		loginAddressVo.setLoginIp(sysLoginLogPo1.getLoginIp());
		loginAddressVo.setLoginMode(sysLoginLogPo1.getLoginMode());
		loginAddressVo.setLoginName(loginName);
		//loginAddressVo.setUserId(sysLoginLogPo1.getUserId());
		
		if("未知地址".equals(loginAddressVo.getLastLoginAddress())||"未知地址".equals(loginAddressVo.getLoginAddress())){
			loginAddressVo.setIsChange(LoginAddressVo.LoginStatus.Normal.getCode());
			result.setData(loginAddressVo);
			return result;
		}
	
		if(StringUtils.isNotBlank(loginAddressVo.getLastLoginAddress())&&!loginAddressVo.getLastLoginAddress().equals(loginAddressVo.getLoginAddress())){
			loginAddressVo.setIsChange(LoginAddressVo.LoginStatus.Abnormal.getCode());
			result.setData(loginAddressVo);
			return result;
		}
		result.setData(loginAddressVo);
		return result;
	}
}
*/