/*package com.fxb.world.service.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.tsh.usercentre.dao.SysProjectDao;
import com.tsh.usercentre.dao.SysResourcesDao;
import com.tsh.usercentre.po.system.SysProjectPo;
import com.tsh.usercentre.po.system.SysResourcesPo;
import com.tsh.usercentre.vo.system.SysProjectVo;
import com.tsh.usercentre.vo.system.SysResourcesVo;

*//**
 * 
 * @author ds
 *
 *//*
@Service
@SuppressWarnings("unchecked")
public class SystemService {
	@Resource SysProjectDao sysProjectDao;
	@Resource SysResourcesDao sysResourcesDao;
	
	
	*//**
	 * 添加项目
	 * @param result
	 * @param SysProjectPo
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result saveSysProject(Result result, SysProjectVo sysProjectVo,Long userId) throws Exception{
		if(sysProjectVo==null){
			String msg = "添加的项目信息为空";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}
		if(StringUtils.isBlank(sysProjectVo.getSysCode())){
			result.setStatus(500);
			result.setMsg("系统编号为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isBlank(sysProjectVo.getSysName())){
			result.setStatus(500);
			result.setMsg("系统名称为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isBlank(sysProjectVo.getDomain())){
			result.setStatus(500);
			result.setMsg("系统域名为空");
			result.setData(null);
			return result;
		}
		SysProjectPo sysProjectPo=new SysProjectPo();
		PropertyUtils.copyProperties(sysProjectPo,sysProjectVo);
		int count = (Integer) sysProjectDao.checkSysProject(result, sysProjectPo,0).getData();
		if(count>0){
			result.setStatus(500);
			result.setMsg("数据已存在，请重新添加！");
			result.setData(null);
			return result;
		}
		result = sysProjectDao.saveSysProject(result, sysProjectPo,userId);
		return result;
	}
	
	*//**
	 * 根据id修改对象
	 * @param result
	 * @param SysProjectPo
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result updateProject(Result result,SysProjectVo sysProjectVo,Long userId) throws Exception{
		if(sysProjectVo==null){
			String msg = "添加的项目信息为空";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}
		if(null==sysProjectVo.getSysCode()){
			result.setStatus(500);
			result.setMsg("系统编号为空");
			result.setData(null);
			return result;
		}
		if(null==sysProjectVo.getSysName()){
			result.setStatus(500);
			result.setMsg("系统名称为空");
			result.setData(null);
			return result;
		}
		if(null==sysProjectVo.getSysCode()){
			result.setStatus(500);
			result.setMsg("系统域名为空空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isNotBlank(sysProjectVo.getDomain())){
			result=sysResourcesDao.updateResourcesDomainByprjId(result,sysProjectVo.getDomain(),sysProjectVo.getId());
		}
		
		SysProjectPo sysProjectPo=new SysProjectPo();
		PropertyUtils.copyProperties(sysProjectPo,sysProjectVo);
		int count = (Integer) sysProjectDao.checkSysProject(result, sysProjectPo,1).getData();
		if(count>0){
			result.setStatus(500);
			result.setMsg("数据已存在，请重新添加！");
			result.setData(null);
			return result;
		}
		
		result = sysProjectDao.updateProjectPo(result, sysProjectPo,userId);
		return result;
	}
	
	*//**
	 * 删除项目
	 * @param result
	 * @param id
	 * @param status
	 * @return
	 * @throws Exception
	 *//*
	public Result deleteProject(Result result,Integer id) throws Exception{
		
		result =sysResourcesDao.findResourcesListByPrjId(result,String.valueOf(id));
		if(result.getData()!=null){
			List<SysResourcesPo> list=result.getData();
			if(list.size()>0){
				String msg = "该系统已有菜单不可删除！";
				result.setStatus(500);
				result.setMsg(msg);
				result.setData(null);
				return result;
			}
			
		}
		result = sysProjectDao.delSysProject(result, id);
		return result;
	}
	

	
	*//**
	 * 
	 * @param result
	 * @param sysProjectVo
	 * @param page
	 * @return
	 * @throws Exception
	 *//*
	public Result findProject(Result result,SysProjectVo sysProjectVo,Page<SysProjectPo> page) throws Exception {
		if(sysProjectVo==null){
			result.setMsg("查询对象不能为空");
			result.setStatus(500);
			result.setData(null);
			throw new FunctionException(result, "查询不能为空");
		}
		SysProjectPo sysProjectPo=new SysProjectPo();
		PropertyUtils.copyProperties(sysProjectPo,sysProjectVo);
		result = sysProjectDao.findProjectPoList(result, sysProjectPo, page);
		return result;
	}
	
	
	
	*//**
	 * 添加项目
	 * @param result
	 * @param SysProjectPo
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result saveSysResources(Result result, SysResourcesVo sysResourcesVo,Long userId) throws Exception{
		if(sysResourcesVo==null){
			String msg = "添加的项目信息为空";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			throw new FunctionException(result, "添加的项目信息为空");
		}
		SysResourcesPo sysResourcesPo=new SysResourcesPo();
		
		
		PropertyUtils.copyProperties(sysResourcesPo,sysResourcesVo);
		sysResourcesPo.setFunCode(sysResourcesPo.getFunCode().toLowerCase());
		sysResourcesPo.setCreateBy(userId);
		sysResourcesPo.setCreateDate(new Date());
		sysResourcesPo.setUpdateBy(userId);
		sysResourcesPo.setUpdateDate(new Date());
		result = sysResourcesDao.saveSysResources(result, sysResourcesPo);
		return result;
	}
	
	*//**
	 * 根据id修改对象
	 * @param result
	 * @param SysProjectPo
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result updateSysResources(Result result,SysResourcesVo sysResourcesVo,Long userId) throws Exception{
		if(sysResourcesVo==null){
			String msg = "添加的项目信息为空";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}
		SysResourcesPo sysResourcesPo=new SysResourcesPo();
		PropertyUtils.copyProperties(sysResourcesPo,sysResourcesVo);
		sysResourcesPo.setUpdateBy(userId);
		sysResourcesPo.setUpdateDate(new Date());
		result = sysResourcesDao.updateResources(result, sysResourcesPo);
		return result;
	}
	
	*//**
	 * 删除项目
	 * @param result
	 * @param id
	 * @param status
	 * @return
	 * @throws Exception
	 *//*
	public Result deleteSysResourcesPo(Result result,Integer id) throws Exception{
		List<Long> idslist=new ArrayList<Long>();
		idslist.add(Long.valueOf(id));
		result=sysResourcesDao.getSysResourcesByParId(result,idslist);
		List<SysResourcesPo> list=result.getData();
		if(list!=null&&list.size()>0){
			String msg = "该菜单存在下级菜单不可删除！";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}
		result = sysResourcesDao.delSysResources(result, id);
		return result;
	}
	
	*//**
	 * 删除项目
	 * @param result
	 * @param id
	 * @param status
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result updateSysRessStatus(Result result,Long id,Integer status,Long userId) throws Exception{
		
		if(status==0){
			result=sysResourcesDao.getSysResources(result,id);
			SysResourcesPo sysResourcesPo=result.getData();
			if(sysResourcesPo!=null&&sysResourcesPo.getLevel()<3){
				if(sysResourcesPo.getLevel()==2){//二级菜单则关闭对应的功能菜单
					List<Long> idslist=new ArrayList<Long>();
					idslist.add(Long.valueOf(id));
					result=sysResourcesDao.getSysResourcesByParId(result,idslist);
					List<SysResourcesPo> list=result.getData();
					for (SysResourcesPo sysResourcesPo2 : list) {
						sysResourcesPo2.setUpdateBy(userId);
						sysResourcesPo2.setUpdateDate(new Date());
						sysResourcesPo2.setStatus(status);
					}
				}
				if(sysResourcesPo.getLevel()==1){//关闭二级，三级菜单
					List<Long> idslist=new ArrayList<Long>();
					idslist.add(Long.valueOf(id));
					result=sysResourcesDao.getSysResourcesByParId(result,idslist);
					List<SysResourcesPo> list=result.getData();
					List<Long> parIdslist=new ArrayList<Long>();
					for (SysResourcesPo sysResourcesPo2 : list) {
						sysResourcesPo2.setUpdateBy(userId);
						sysResourcesPo2.setUpdateDate(new Date());
						sysResourcesPo2.setStatus(status);
						parIdslist.add(sysResourcesPo2.getId());
					}
					if(parIdslist.size()>0){
						result=sysResourcesDao.getSysResourcesByParId(result,parIdslist);
						List<SysResourcesPo> list1=result.getData();
						for (SysResourcesPo sysResourcesPo2 : list1) {
							sysResourcesPo2.setUpdateBy(userId);
							sysResourcesPo2.setUpdateDate(new Date());
							sysResourcesPo2.setStatus(status);
						}
					}
					
				}
			}
		}
		result = sysResourcesDao.updateSysResourcesStatus(result, status, id,userId);
		return result;
	}
	

	*//**
	 * 
	 * @param result
	 * @param menuId
	 * @param funCode
	 * @return
	 * @throws Exception
	 *//*
	public Result checkSysRessFunCode(Result result,Long menuId,String funCode) throws Exception {
		result=sysResourcesDao.getSysRessFunCode(result,menuId,funCode);
		if(result.getData()!=null){
			Long count =result.getData();
			if(count>0){
				result.setData(false);
			}else{
				result.setData(true);
			}
		}
		return result;
	}
	
	*//**
	 * 获取
	 * @param result
	 * @param sysResourcesVo
	 * @param page
	 * @return
	 * @throws Exception
	 *//*
	public Result findSysResources(Result result,SysResourcesVo sysResourcesVo,Page<SysResourcesPo> page) throws Exception {
		
		SysResourcesPo sysResourcesPo=new SysResourcesPo();
		
		
		PropertyUtils.copyProperties(sysResourcesPo,sysResourcesVo);
		result = sysResourcesDao.findResourcesList(result, sysResourcesPo, page);
		Pagination pagination =(Pagination) result.getData();
		List<SysResourcesPo> list=(List<SysResourcesPo>) pagination.getRows();
		List<SysResourcesVo> listVo=new ArrayList<SysResourcesVo>();
		
		result=sysProjectDao.findProjectPoList(result,null,new Page<SysProjectPo>(1,100));
		Pagination findPagination=new Pagination();
		if(result.getData()!=null){
			findPagination=(Pagination) result.getData();
			
		}
		List<SysProjectPo> spList = (List<SysProjectPo>) findPagination.getRows();
		SysResourcesVo sysResourcesVo1;
		for (SysResourcesPo sysResourcesPo1 : list) {
			sysResourcesVo1=new SysResourcesVo();
			PropertyUtils.copyProperties(sysResourcesVo1, sysResourcesPo1);
			for (SysProjectPo sysProjectPo : spList) {
				if(sysProjectPo.getId().toString().equals(sysResourcesVo1.getPrjId().toString())){
						//sysProjectPo.getId()==sysResourcesVo1.getPrjId()){
					sysResourcesVo1.setPrjName(sysProjectPo.getSysName());
				}
			}
			listVo.add(sysResourcesVo1);
		}

		pagination.setTotal(listVo.size());
		pagination.setRows(listVo);
		result.setData(pagination);
		return result;
	}
	
	*//**
	 * 
	 * @param result
	 * @param sysResourcesVo
	 * @param page
	 * @return
	 * @throws Exception
	 *//*
	public Result findSysRes(Result result,SysResourcesVo sysResourcesVo,Page<SysResourcesPo> page) throws Exception {
		
		SysResourcesPo sysResourcesPo=new SysResourcesPo();
		
		
		PropertyUtils.copyProperties(sysResourcesPo,sysResourcesVo);
		result = sysResourcesDao.findResourcesList(result, sysResourcesPo, page);
		Pagination pagination =(Pagination) result.getData();
		List<SysResourcesPo> list=(List<SysResourcesPo>) pagination.getRows();
		List<SysResourcesVo> listVo=new ArrayList<SysResourcesVo>();
		
		result=sysProjectDao.findProjectPoList(result,null,new Page<SysProjectPo>(1,100));
		Pagination findPagination=new Pagination();
		if(result.getData()!=null){
			findPagination=(Pagination) result.getData();
			
		}
		List<SysProjectPo> spList=(List<SysProjectPo>) findPagination.getRows();
		SysResourcesVo sysResourcesVo1;
		for (SysResourcesPo sysResourcesPo1 : list) {
			sysResourcesVo1=new SysResourcesVo();
			PropertyUtils.copyProperties(sysResourcesVo1, sysResourcesPo1);
			for (SysProjectPo sysProjectPo : spList) {
				if(sysProjectPo.getId().equals(sysResourcesVo1.getPrjId())){
					sysResourcesVo1.setPrjName(sysProjectPo.getSysName());
				}
			}
			listVo.add(sysResourcesVo1);
		}
		
		List<SysResourcesVo> listVo1=new ArrayList<SysResourcesVo>();
		
		for (SysResourcesVo sysResourcesVo2 : listVo) {
			if(sysResourcesVo2.getLevel()==1){
				listVo1.add(sysResourcesVo2);
			}
		}
		
		for (SysResourcesVo sysResourcesVo3 : listVo) {
			sysResourcesVo1=new SysResourcesVo();
			if(sysResourcesVo3.getLevel()==2){
				for (SysResourcesVo sysResourcesVo4 : listVo1) {
					if(sysResourcesVo3.getParentId().equals(sysResourcesVo4.getId())){
						if(sysResourcesVo4.getChildren()==null){
							sysResourcesVo4.setChildren(new ArrayList<SysResourcesVo>());
						}
						sysResourcesVo4.getChildren().add(sysResourcesVo3);
					}
				}
			}
		}
		for (SysResourcesVo sysResourcesVo3 : listVo) {
			sysResourcesVo1=new SysResourcesVo();
			if(sysResourcesVo3.getLevel()==3){
				for (SysResourcesVo sysResourcesVo2 : listVo1) {
						if(sysResourcesVo2.getChildren()==null){
							sysResourcesVo2.setChildren(new ArrayList<SysResourcesVo>());
						}
						List<SysResourcesVo> listVo2=sysResourcesVo2.getChildren();
						for (SysResourcesVo sysResourcesVo4 : listVo2) {
							if(sysResourcesVo3.getParentId().equals(sysResourcesVo4.getId())){
								if(sysResourcesVo4.getChildren()==null){
									sysResourcesVo4.setChildren(new ArrayList<SysResourcesVo>());
								}
								sysResourcesVo4.getChildren().add(sysResourcesVo3);
						}
					}
				}
			}
		}
		result.setData(listVo1);
		return result;
	}
}
*/