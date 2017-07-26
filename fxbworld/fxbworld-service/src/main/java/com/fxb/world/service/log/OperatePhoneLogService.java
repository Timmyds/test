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
import com.dtds.platform.util.security.UserInfo;
import com.tsh.usercentre.dao.UserDao;
import com.tsh.usercentre.dao.log.SysPhoneOperateLogDao;
import com.tsh.usercentre.po.log.SysPhoneOperateLogPo;
import com.tsh.usercentre.po.user.UserBusinessPo;
import com.tsh.usercentre.po.user.UserPo;
import com.tsh.usercentre.service.UserBusinessService;
import com.tsh.usercentre.util.FindIpUtil;
import com.tsh.usercentre.vo.log.SysPhoneOperateLogVo;
*//**
 * 
 * @author ds
 *
 *//*
@Service
public class OperatePhoneLogService {
	@Resource
	SysPhoneOperateLogDao sysPhoneOperateLogDao;
	@Resource
	UserDao userDao;
	@Resource 
	UserBusinessService userBusinessService;

	*//**
	 * 添加手机号操作记录
	 * @param result
	 * @param loginInfo
	 * @param userId
	 * @param operateMode
	 * @param phone
	 * @return
	 * @throws Exception
	 *//*
	public Result saveSysPhoneOperateLog(Result result, UserInfo loginInfo, Long userId, Integer  operateType,String operateMode, String phone) throws Exception {
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
		
		SysPhoneOperateLogPo sysPhoneOperateLogPo = new SysPhoneOperateLogPo();
		sysPhoneOperateLogPo.setOperateAddress("");
		sysPhoneOperateLogPo.setOperateIp(loginInfo.getLoginIp());
		sysPhoneOperateLogPo.setOperateMode(operateMode);
		sysPhoneOperateLogPo.setOperateDate(new Date());
		sysPhoneOperateLogPo.setOperateUserId(loginInfo.getUserId());
		sysPhoneOperateLogPo.setOperateUserName(loginInfo.getLoginName());
		if(userBusinessPo!=null){
			sysPhoneOperateLogPo.setBelongId(userBusinessPo.getBelongId());
			sysPhoneOperateLogPo.setBizId(userBusinessPo.getBizId());
		}
		sysPhoneOperateLogPo.setUserId(userPo.getId());
		sysPhoneOperateLogPo.setPhone(phone);
		sysPhoneOperateLogPo.setLoginName(userPo.getLoginName());
		result = sysPhoneOperateLogDao.findSysPhoneOperateLogId(result, userId);
		List<Long> list = result.getData();
		if (list != null && list.size() >98 ) {//记录保留100条，超出后删除
			for (int i = 98; i < list.size(); i++) {
			Long id = list.get(i);
			result = sysPhoneOperateLogDao.delSysPhoneOperateLog(result, id);
			}
			result.setStatus(200);
		}

		result = sysPhoneOperateLogDao.saveSysPhoneOperateLog(result, sysPhoneOperateLogPo);
		return result;
	}

	*//**
	 * 根据id查询list
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result findSysPhoneOperateLog(Result result, Long userId) throws Exception {
		if (userId == null) {
			String msg = "参数为空";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}
		result = sysPhoneOperateLogDao.findSysPhoneOperateLog(result, userId);
		List<SysPhoneOperateLogPo> list = result.getData();
		List<SysPhoneOperateLogVo> listVo = new ArrayList<SysPhoneOperateLogVo>();
		SysPhoneOperateLogVo sysPhoneOperateLogVo = null;
		for (SysPhoneOperateLogPo sysPhoneOperateLogPo : list) {
			sysPhoneOperateLogVo = new SysPhoneOperateLogVo();
			if(StringUtils.isBlank(sysPhoneOperateLogPo.getOperateAddress())){
				sysPhoneOperateLogPo.setOperateAddress(FindIpUtil.getIpAddress(sysPhoneOperateLogPo.getOperateIp()));
			}
			PropertyUtils.copyProperties(sysPhoneOperateLogVo, sysPhoneOperateLogPo);
			listVo.add(sysPhoneOperateLogVo);

		}
		result.setData(listVo);
		return result;
	}
	*//**
	 * 
	 * @param result
	 * @param sysPhoneOperateLogVo
	 * @param page
	 * @return
	 * @throws Exception
	 *//*
	@SuppressWarnings("unchecked")
	public Result findPhoneOperateLog(Result result,SysPhoneOperateLogVo sysPhoneOperateLogVo,Page page) throws Exception {
		if(sysPhoneOperateLogVo==null){
			result.setMsg("查询对象不能为空");
			result.setStatus(500);
			result.setData(null);
			throw new FunctionException(result, "查询不能为空");
		}
		SysPhoneOperateLogPo sysPhoneOperateLogPo=new SysPhoneOperateLogPo();
		
		PropertyUtils.copyProperties(sysPhoneOperateLogPo,sysPhoneOperateLogVo);
		result = sysPhoneOperateLogDao.findPhoneOperateLog(result, sysPhoneOperateLogPo, page);
		 Pagination findPagination=result.getData();
		 if(findPagination!=null){
			 List<SysPhoneOperateLogPo> list=(List<SysPhoneOperateLogPo>) findPagination.getRows();
			 for (SysPhoneOperateLogPo phoneOperateLogPo : list) {
				 if(StringUtils.isBlank(phoneOperateLogPo.getOperateAddress())){
					 phoneOperateLogPo.setOperateAddress(FindIpUtil.getIpAddress(phoneOperateLogPo.getOperateIp()));
					}
			}
		 }
		 
		return result;
	}
}
*/