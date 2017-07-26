/*package com.fxb.world.service.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.dtds.platform.util.security.RoleTypeEnum;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.usercentre.dao.UserBusinessDao;
import com.tsh.usercentre.dao.UserDao;
import com.tsh.usercentre.dao.log.SysPwdOperateLogDao;
import com.tsh.usercentre.po.log.SysPwdOperateLogPo;
import com.tsh.usercentre.po.user.UserBusinessPo;
import com.tsh.usercentre.po.user.UserPo;
import com.tsh.usercentre.service.UserBusinessService;
import com.tsh.usercentre.util.FindIpUtil;
import com.tsh.usercentre.util.OperateTypeEnum;
import com.tsh.usercentre.vo.log.SysPwdOperateLogVo;
*//**
 * 
 * @author ds
 *
 *//*
@Service
public class OperatePwdLogService {
	@Resource
	SysPwdOperateLogDao sysPwdOperateLogDao;
	@Resource
	UserDao userDao;
	@Resource 
	UserBusinessService userBusinessService;

	*//**
	 * 添加密码修改记录
	 * @param result
	 * @param loginInfo
	 * @param userId
	 * @param operateMode
	 * @return
	 * @throws Exception
	 *//*
	public Result saveSysPwdOperateLog(Result result, UserInfo loginInfo, Long userId,Integer  operateType,String operateMode) throws Exception {
		if (loginInfo == null || userId == null) {
			result.setStatus(500);
			result.setMsg("参数为空！");
			result.setData(null);
			return result;
		}
		result = userDao.getUser(result, userId);
		UserPo userPo = result.getData();
		if (userPo == null) {
			result.setStatus(500);
			result.setMsg("修改用户为空");
			result.setData(null);
			return result;
		}
		result=userBusinessService.getUserBusinessByOperateType(result, userPo.getId(), operateType);
		UserBusinessPo userBusinessPo=result.getData();

		SysPwdOperateLogPo sysPwdOperateLogPo = new SysPwdOperateLogPo();
		sysPwdOperateLogPo.setOperateAddress("");
		sysPwdOperateLogPo.setOperateIp(loginInfo.getLoginIp());
		sysPwdOperateLogPo.setOperateMode(operateMode);
		sysPwdOperateLogPo.setOperateDate(new Date());
		if(loginInfo.getUserId()==null){
			sysPwdOperateLogPo.setOperateUserId(userPo.getId());
			sysPwdOperateLogPo.setOperateUserName(userPo.getLoginName());

		}else{
			sysPwdOperateLogPo.setOperateUserId(loginInfo.getUserId());
			sysPwdOperateLogPo.setOperateUserName(loginInfo.getLoginName());
		}
		if(userBusinessPo!=null){
			sysPwdOperateLogPo.setBelongId(userBusinessPo.getBelongId());
			sysPwdOperateLogPo.setBizId(userBusinessPo.getBizId());
		}
		sysPwdOperateLogPo.setUserId(userPo.getId());
		sysPwdOperateLogPo.setLoginName(userPo.getLoginName());
		result = sysPwdOperateLogDao.findSysPwdOperateLogId(result, userId);
		List<Long> list = result.getData();
		if (list != null && list.size() >98 ) {//记录保留100条，超出后删除
			for (int i = 98; i < list.size(); i++) {
				Long id = list.get(i);
				result = sysPwdOperateLogDao.delSysPwdOperateLog(result, id);
			}
			result.setStatus(200);
		}

		result = sysPwdOperateLogDao.saveSysPwdOperateLog(result, sysPwdOperateLogPo);
		result.setStatus(200);
		return result;
	}

	*//**
	 * 根据id查询
	 * 
	 * @param result
	 * @param SysPwdOperateLogPo
	 * @param userId
	 * @return
	 * @throws Exception
	 
	public Result updateSysPwdOperateLog(Result result, Long userId) throws Exception {
		if (userId == null) {
			String msg = "参数为空";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}
		result = sysPwdOperateLogDao.findSysPwdOperateLog(result, userId);
		List<SysPwdOperateLogPo> list = result.getData();
		List<SysLoginLogVo> listVo = new ArrayList<SysLoginLogVo>();
		SysLoginLogVo sysLoginLogVo = null;
		for (SysPwdOperateLogPo sysPwdOperateLogPo : list) {
			sysLoginLogVo = new SysLoginLogVo();
			PropertyUtils.copyProperties(sysLoginLogVo, sysPwdOperateLogPo);
			listVo.add(sysLoginLogVo);

		}
		result.setData(listVo);
		return result;
	}*//*

	*//**
	 * 根据id查询list
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result findSysPwdOperateLog(Result result, Long userId) throws Exception {
		if (userId == null) {
			String msg = "参数为空";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}
		result = sysPwdOperateLogDao.findSysPwdOperateLog(result, userId);
		List<SysPwdOperateLogPo> list = result.getData();
		List<SysPwdOperateLogVo> listVo = new ArrayList<SysPwdOperateLogVo>();
		SysPwdOperateLogVo sysPwdOperateLogVo = null;
		for (SysPwdOperateLogPo sysPwdOperateLogPo : list) {
			sysPwdOperateLogVo = new SysPwdOperateLogVo();
			if(StringUtils.isBlank(sysPwdOperateLogPo.getOperateAddress())){
				sysPwdOperateLogPo.setOperateAddress(FindIpUtil.getIpAddress(sysPwdOperateLogPo.getOperateIp()));
			}
			PropertyUtils.copyProperties(sysPwdOperateLogVo, sysPwdOperateLogPo);
			listVo.add(sysPwdOperateLogVo);

		}
		result.setData(listVo);
		return result;
	}
	
	*//**
	 * 
	 * @param result
	 * @param sysPwdOperateLogVo
	 * @param page
	 * @return
	 * @throws Exception
	 *//*
	public Result findPwdOperateLog(Result result,SysPwdOperateLogVo sysPwdOperateLogVo,Page<SysPwdOperateLogPo> page) throws Exception {
		if(sysPwdOperateLogVo==null){
			result.setMsg("查询对象不能为空");
			result.setStatus(500);
			result.setData(null);
			throw new FunctionException(result, "查询不能为空");
		}
		SysPwdOperateLogPo sysPwdOperateLogPo=new SysPwdOperateLogPo();
		PropertyUtils.copyProperties(sysPwdOperateLogPo,sysPwdOperateLogVo);
		result = sysPwdOperateLogDao.findPwdOperateLog(result, sysPwdOperateLogPo, page);
		 Pagination findPagination=result.getData();
		 if(findPagination!=null){
			 List<SysPwdOperateLogPo> list=(List<SysPwdOperateLogPo>) findPagination.getRows();
			 for (SysPwdOperateLogPo pwdOperateLogPo : list) {
				 if(StringUtils.isBlank(pwdOperateLogPo.getOperateAddress())){
					 pwdOperateLogPo.setOperateAddress(FindIpUtil.getIpAddress(pwdOperateLogPo.getOperateIp()));
					}
			}
		 }
		return result;
	}
	

	
}
*/