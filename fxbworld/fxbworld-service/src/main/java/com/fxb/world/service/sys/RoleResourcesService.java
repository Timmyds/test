package com.fxb.world.service.sys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fxb.world.dao.TRoleResourcesMapper;
import com.fxb.world.dao.TUserResourcesMapper;
import com.fxb.world.entity.TRoleResources;
import com.fxb.world.entity.TUserResources;
import com.fxb.world.util.bean.Result;

/*import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.tsh.usercentre.dao.SysRoleResourcesDao;
import com.tsh.usercentre.dao.SysUserResourcesDao;
import com.tsh.usercentre.dao.UserBusinessDao;
import com.tsh.usercentre.po.system.SysRoleResourcesPo;
import com.tsh.usercentre.po.system.SysUserResourcesPo;
import com.tsh.usercentre.po.user.UserBusinessPo;
import com.tsh.usercentre.util.MessageResource;*/

/**
 * 
 * @author ds
 * 
 */
@Service
public class RoleResourcesService {

	@Resource
	TRoleResourcesMapper tRoleResourcesMapper;
	@Resource
	TUserResourcesMapper tUserResourcesMapper;


	/**
	 * 
	 * @param result
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Result queryRoleResourcesByRoleIdService(Result result, Long roleId, Long userId) throws Exception {
		if (roleId == null) {
			result.setMsg("角色ID为空！");
			result.setStatus(500);
			result.setData(null);
			return result;
		}
		List<Long> list = new ArrayList<Long>();
		List<TRoleResources> tsList= tRoleResourcesMapper.queryRoleResourcesByRoleId(roleId);// 获取角色权限
		for (TRoleResources tRoleResources : tsList) {
			list.add(tRoleResources.getResourcesId());
		}
	
		List<TUserResources> tuList= tUserResourcesMapper.queryUserResourcesByUserId(userId);// 获取用户权限
		for (TUserResources sysUserResourcesPo : tuList) {
			if (sysUserResourcesPo.getStatus() == null || sysUserResourcesPo.getStatus() == 0) {
				list.add(sysUserResourcesPo.getResourcesId());
			} else {
				list.remove(sysUserResourcesPo.getResourcesId());
			}
		}
		List<Long> newList = new ArrayList<Long>(new HashSet<Long>(list));
		result.setData(newList);
		return result;
	}
	
	
}
	/**
	 * 
	 * @param result
	 * @param roleIdIsManager
	 * @param roleId
	 * @return
	 * @throws Exception
	 *//*
	public Result querySysRoleResourcesByRoleIds(Result result, Long roleIdIsManager, Long roleId, Long userId, Long bizId, Integer roleType) throws Exception {
		if (roleIdIsManager == null || roleId == null) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		List<Long> list = new ArrayList<Long>();
		List<Long> listIsMan=new ArrayList<Long>();
		List<Long> roleList=new ArrayList<Long>();
		result = sysRoleResourcesDao.querySysRoleResourcesByRoleId(result, roleIdIsManager);// 获取管理员角色权限
		if(result.getData()!=null){
			 listIsMan = result.getData();
		}
		result = userBusinessDao.getUserBusinessByBizIdToRoleType(result, bizId, roleType);
		UserBusinessPo userBusinessPo = result.getData();
		if (userBusinessPo != null) {
			Long mgUserId = userBusinessPo.getUserId();
			result = sysUserResourcesDao.querySysUserResourcesByUserId(result, mgUserId, null);// 获取管理员用户权限
			List<SysUserResourcesPo> suList = result.getData();
			for (SysUserResourcesPo sysUserResourcesPo : suList) {
				if (sysUserResourcesPo.getStatus() == null || sysUserResourcesPo.getStatus() == 0) {
					listIsMan.add(sysUserResourcesPo.getResourcesId());
				} else {
					listIsMan.remove(sysUserResourcesPo.getResourcesId());
				}
			}
		}
		result = sysRoleResourcesDao.querySysRoleResourcesByRoleId(result, roleId);// 获取角色权限
		if(result.getData()!=null){
			roleList=result.getData();
		}
		result = sysUserResourcesDao.querySysUserResourcesByUserId(result, userId, null);// 获取用户权限
		List<SysUserResourcesPo> suUserList = result.getData();
		for (SysUserResourcesPo sysUserResourcesPo : suUserList) {
				if (sysUserResourcesPo.getStatus() == null || sysUserResourcesPo.getStatus() == 0) {
					roleList.add(sysUserResourcesPo.getResourcesId());
				} else {
					roleList.remove(sysUserResourcesPo.getResourcesId());
				}
		}

		for (Long str : listIsMan) {
			for (Long string : roleList) {
				if (string.equals(str)) {
					list.add(string);
				}
			}
		}
		List<Long> newList = new ArrayList<Long>(new HashSet<Long>(list));
		result.setData(newList);
		return result;
	}

	*//**
	 * 
	 * @param result
	 * @param poList
	 * @return
	 * @throws Exception
	 *//*
	public Result saveSysRoleResourcesListService(Result result, List<SysRoleResourcesPo> poList) throws Exception {
		return sysRoleResourcesDao.saveSysRoleResourcesList(result, poList);
	}

	*//**
	 * 
	 * @param result
	 * @param roleId
	 * @return
	 * @throws Exception
	 *//*
	public Result delSysRoleResourcesService(Result result, Long roleId) throws Exception {
		if (null == roleId) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		return sysRoleResourcesDao.delSysRoleResources(result, roleId);
	}

}
*/