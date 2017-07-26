/*package com.fxb.world.service.sys;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.tsh.usercentre.dao.SysUserResourcesDao;
import com.tsh.usercentre.po.system.SysUserResourcesPo;
import com.tsh.usercentre.util.MessageResource;
*//**
 * 
 * @author ds
 *
 *//*
@Service
public class SysUserResourcesService {

	@Resource
	SysUserResourcesDao sysUserResourcesDao;
	*//**
	 * 
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author ds
	 * @date 2017年2月8日
	 * @version 3.1.0
	 *//*
	public Result querySysUserResourcesByuserIdService(Result result, Long userId,Integer status) throws Exception {
		if (userId == null) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		List<Long> list=new ArrayList<Long>();
		result=sysUserResourcesDao.querySysUserResourcesByUserId(result, userId,status);
		List<SysUserResourcesPo> suList = result.getData();
		for (SysUserResourcesPo sysUserResourcesPo : suList) {
				list.add(sysUserResourcesPo.getResourcesId());
		}
		result.setData(list);
		return result;
	}


	*//**
	 * 
	 * @param result
	 * @param poList
	 * @return
	 * @throws Exception
	 *//*
	public Result saveSysUserResourcesListService(Result result, List<SysUserResourcesPo> poList) throws Exception {
		return sysUserResourcesDao.saveSysUserResourcesList(result, poList);
	}
	*//**
	 * 
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result delSysUserResourcesService(Result result, Long userId,Integer status) throws Exception {
		if (null == userId|| status==null) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		return sysUserResourcesDao.delSysUserResources(result, userId,status);
	}

}
*/