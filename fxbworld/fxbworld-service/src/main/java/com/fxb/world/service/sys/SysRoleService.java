/*package com.fxb.world.service.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.tsh.usercentre.dao.SysRoleDao;
import com.tsh.usercentre.dao.SysRoleResourcesDao;
import com.tsh.usercentre.dao.SysUserRoleDao;
import com.tsh.usercentre.po.system.SysRolePo;
import com.tsh.usercentre.util.MessageResource;
import com.tsh.usercentre.util.StringTools;
import com.tsh.usercentre.vo.system.RoleVo;
*//**
 * 
 * @author ds
 *
 *//*
@Service
@SuppressWarnings("unchecked")
public class SysRoleService {

	@Resource
	SysRoleDao sysRoleDao;
	@Resource
	SysRoleResourcesDao sysRoleResourcesDao;
	@Resource
	SysUserRoleDao sysUserRoleDao;
	*//**
	 * 
	 * @param result
	 * @param sysRolePo
	 * @param roleId
	 * @param page
	 * @return
	 * @throws Exception
	 *//*
	public Result querySysRoleService(Result result, SysRolePo sysRolePo,  Page<SysRolePo> page) throws Exception {
		String msg = StringTools.checkEntityProperty(sysRolePo);
		if (!StringUtils.isBlank(msg)) {
			result.setMsg("查询信息，对象为" + sysRolePo);
			throw new FunctionException(result, "");
		}
		Pagination p = (Pagination) sysRoleDao.querySysRole(result, sysRolePo,page).getData();
		List<RoleVo> list = new ArrayList<RoleVo>();
		if (p.getTotal() > 0) {
			Map<String, Long> map = (Map<String, Long>) sysUserRoleDao.findSysUserNumByRoleId(result).getData();
			if (map.size() > 0) {
				List<SysRolePo> rows = (List<SysRolePo>) p.getRows();
				for (SysRolePo po : rows) {
					RoleVo v = new RoleVo();
					PropertyUtils.copyProperties(v, po);
					Long userNum = map.get(po.getId().toString());
					if (null != userNum) {
						v.setUserNum(userNum);
					} else {
						v.setUserNum(0L);
					}
					list.add(v);
				}
				p.setRows(list);
			}
		}
		result.setData(p);
		return result;
	}
	
	
	*//**
	 * 
	 * @param result
	 * @param sysRolePo
	 * @param roleId
	 * @param page
	 * @return
	 * @throws Exception
	 *//*
	public Result findSysRoleService(Result result, SysRolePo sysRolePo, Page<SysRolePo> page) throws Exception {
		String msg = StringTools.checkEntityProperty(sysRolePo);
		if (!StringUtils.isBlank(msg)) {
			result.setMsg("查询信息，对象为" + sysRolePo);
			throw new FunctionException(result, "");
		}
		Pagination p = (Pagination) sysRoleDao.querySysRole(result, sysRolePo, page).getData();
		result.setData(p);
		return result;
	}
	*//**
	 * 
	 * @param result
	 * @param sysRolePo
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result saveSysRoleService(Result result, SysRolePo sysRolePo, Long userId) throws Exception {
		// TODO Auto-generated method stub
		String msg = StringTools.checkEntityProperty(sysRolePo);
		if (!StringUtils.isBlank(msg)) {
			result.setMsg("添加角色信息，对象为" + sysRolePo);
			throw new FunctionException(result, "");
		}
		int count = checkSysRoleService(result, null, sysRolePo.getBizId(), sysRolePo.getRoleName(), 0).getData();
		if (count > 0) {
			result.setMsg("该角色名称已存在,请重新输入！");
			result.setStatus(500);
			return result;
		}
		sysRolePo.setStatus(1);
		sysRolePo.setCreateBy(userId);
		sysRolePo.setCreateDate(new Date());
		sysRolePo.setUpdateBy(userId);
		sysRolePo.setUpdateDate(new Date());
		return sysRoleDao.saveSysRole(result, sysRolePo);
	}
	*//**
	 * 
	 * @param result
	 * @param sysRolePo
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result updateSysRoleService(Result result, SysRolePo sysRolePo, Long userId) throws Exception {
		int count = checkSysRoleService(result, sysRolePo.getId(), sysRolePo.getBizId(), sysRolePo.getRoleName(), 1).getData();
		if (count > 0) {
			result.setMsg("角色名称存在,请重新输入！");
			result.setStatus(500);
			return result;
		}
		sysRolePo.setUpdateBy(userId);
		sysRolePo.setUpdateDate(new Date());
		return sysRoleDao.updateSysRole(result, sysRolePo);
	}
	*//**
	 * 
	 * @param result
	 * @param id
	 * @return
	 * @throws Exception
	 *//*
	public Result delSysRoleByIdService(Result result, Long id) throws Exception {
		if (null == id) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		result = sysRoleDao.delSysRoleById(result, id);
		int delCount = (Integer) result.getData();
		if (delCount > 0) {
			result = sysRoleResourcesDao.delSysRoleResources(result, id);
		}
		return result;
	}
	*//**
	 * 
	 * @param result
	 * @param id
	 * @param bizId
	 * @return
	 * @throws Exception
	 *//*
	public Result querySysRoleByIdService(Result result, Long id, Long bizId) throws Exception {
		if (bizId == null) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		if (id == null) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		return sysRoleDao.querySysRoleById(result, id, bizId);
	}
	*//**
	 * 
	 * @param result
	 * @param id
	 * @param status
	 * @param userId
	 * @param bizId
	 * @return
	 * @throws Exception
	 *//*
	public Result updateStatusService(Result result, Long id, String status, Long userId, Long bizId) throws Exception {
		if (id == 0 || StringUtils.isBlank(status)) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		SysRolePo sysRolePo = (SysRolePo) querySysRoleByIdService(result, id, bizId).getData();
		if (sysRolePo == null) {
			result.setMsg(MessageResource.ADD_FAIL);
			throw new FunctionException(result, "");
		}
		sysRolePo.setUpdateBy(userId);
		sysRolePo.setUpdateDate(new Date());
		sysRoleDao.updateSysRole(result, sysRolePo);

		result.setData(sysRolePo);
		return result;
	}
	*//**
	 * 
	 * @param result
	 * @param id
	 * @param bizId
	 * @param roleName
	 * @param type
	 * @return
	 * @throws Exception
	 *//*
	public Result checkSysRoleService(Result result, Long id, Long bizId, String roleName, int type) throws Exception {
		if (null == bizId || StringUtils.isBlank(roleName)) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		return sysRoleDao.checkSysRole(result, id, bizId, roleName, type);
	}
	*//**
	 * 
	 * @param result
	 * @param bizId
	 * @param roleId
	 * @return
	 * @throws Exception
	 *//*
	public Result querySysRoleVoService(Result result, Long bizId, Long roleId) throws Exception {
		return sysRoleDao.querySysRoleVo(result, bizId, roleId);
	}
	*//**
	 * 
	 * @param result
	 * @param roleIds
	 * @param bizId
	 * @return
	 * @throws Exception
	 *//*
	public Result querySysRoleVoByIdsService(Result result, List<Integer> roleIds, Long bizId) throws Exception {
		if (null == bizId) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		if (null == roleIds || roleIds.size() == 0) {
			result.setData(null);
			return result;
		}
		return sysRoleDao.querySysRoleVoByIds(result, roleIds, bizId);
	}
}
*/