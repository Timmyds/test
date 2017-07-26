/*package com.fxb.world.service.sys;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.tsh.usercentre.dao.SysUserRoleDao;
import com.tsh.usercentre.po.system.SysUserRolePo;
import com.tsh.usercentre.util.MessageResource;
import com.tsh.usercentre.util.StringTools;
*//**
 * 
 * @author ds
 *
 *//*
@Service
public class SysUserRoleService {

	@Resource
	SysUserRoleDao sysUserRoleDao;
	*//**
	 * 
	 * @param result
	 * @param sysUserRolePo
	 * @return
	 * @throws Exception
	 *//*
	public Result saveSysUserRoleService(Result result, SysUserRolePo sysUserRolePo) throws Exception {
		String msg = StringTools.checkEntityProperty(sysUserRolePo);
		if (!StringUtils.isBlank(msg)) {
			result.setMsg("添加信息，对象为" + sysUserRolePo);
			throw new FunctionException(result, "");
		}
		return sysUserRoleDao.saveSysUserRole(result, sysUserRolePo);
	}
	*//**
	 * 
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result findSysUserRoleService(Result result, Long userId) throws Exception {
		if (null == userId) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		result = sysUserRoleDao.findSysUserRole(result, userId);
		List<SysUserRolePo> list = result.getData();
		if (null != list && list.size() > 0) {
			result.setData(list.get(0));
		}
		return result;
	}
	*//**
	 * 
	 * @param result
	 * @param sysUserRolePo
	 * @return
	 * @throws Exception
	 *//*
	public Result updateSysUserRole(Result result, SysUserRolePo sysUserRolePo) throws Exception {
		return sysUserRoleDao.updateSysUserRole(result, sysUserRolePo);
	}

	*//**
	 * 
	 * @param result
	 * @param roleId
	 * @return
	 * @throws Exception
	 *//*
	public Result findSysUserNumByRoleId(Result result, Long roleId) throws Exception {
		return sysUserRoleDao.findSysUserNumByRoleId(result, roleId);
	}
}
*/