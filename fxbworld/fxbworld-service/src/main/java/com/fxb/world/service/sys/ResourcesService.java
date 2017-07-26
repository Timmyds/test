package com.fxb.world.service.sys;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.fxb.world.dao.TResourcesMapper;
import com.fxb.world.entity.TResources;
import com.fxb.world.service.RedisService;
import com.fxb.world.util.SerializeUtil;
import com.fxb.world.util.SessionKey;
import com.fxb.world.util.bean.Result;
import com.fxb.world.util.security.UserResMenu;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 */
@Service()
public class ResourcesService  {
   @Resource
    private TResourcesMapper resourcesMapper;
   
   @Resource RedisService redisService;
   /**
	 * 根据资源菜单信息
	 * 
	 * @param result
	 * @param proId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Result findResListByProSerivce(Result result, String proId) throws Exception {
		List<TResources> srpList = (List<TResources>) SerializeUtil.unserialize(redisService.get(SessionKey.REDIS_KEY_ALL_MUEN_VALUE));
		if (srpList == null || srpList.size() == 0) {
			 srpList = resourcesMapper.selectAll();
			
			redisService.setObject(SessionKey.REDIS_KEY_ALL_MUEN_VALUE, SerializeUtil.serialize(srpList), SessionKey.REDIS_KEY_BASE_TIME);
		} else {
			redisService.expire(SessionKey.REDIS_KEY_ALL_MUEN_VALUE, SessionKey.REDIS_KEY_BASE_TIME);

		}
		UserResMenu loginRm = null;
		List<UserResMenu> list = new ArrayList<UserResMenu>();
		for (TResources sysResourcesPo : srpList) {
			loginRm = new UserResMenu();
			BeanUtils.copyProperties(sysResourcesPo,loginRm);
			loginRm.setMenuPath(sysResourcesPo.getDomain() + sysResourcesPo.getMenuPath());
			loginRm.setMenulink(sysResourcesPo.getMenuPath());
			list.add(loginRm);
		}
		result.setData(list);
		return result;
	}

	
    public PageInfo<TResources> selectByPage(TResources resources, int start, int length) {
        int page = start/length+1;
        Example example = new Example(TResources.class);
        //分页查询
        PageHelper.startPage(page, length);
        List<TResources> userList = resourcesMapper.selectByExample(example);
        return new PageInfo<>(userList);
    }

    public List<TResources> queryAll(){
        return resourcesMapper.selectAll();
    }

  /*  @Cacheable(cacheNames="resources",key="#map['userid'].toString()+#map['type']")
    public List<TResources> loadUserResources(Map<String, Object> map) {
        return resourcesMapper.loadUserResources(map);
    }

    public List<TResources> queryResourcesListWithSelected(Integer rid) {
        return resourcesMapper.queryResourcesListWithSelected(rid);
    }*/
}
/*package com.fxb.world.service.sys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.data.redis.RedisSlave;
import com.dtds.platform.util.SerializeUtil;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.RoleTypeEnum;
import com.dtds.platform.util.security.UserResMenu;
import com.dtds.platform.web.userinfo.util.SessionKey;
import com.tsh.usercentre.dao.SysProjectDao;
import com.tsh.usercentre.dao.SysResourcesDao;
import com.tsh.usercentre.dao.SysRoleResourcesDao;
import com.tsh.usercentre.dao.SysUserResourcesDao;
import com.tsh.usercentre.dao.UserBusinessDao;
import com.tsh.usercentre.dao.UserDao;
import com.tsh.usercentre.po.system.SysProjectPo;
import com.tsh.usercentre.po.system.SysResourcesPo;
import com.tsh.usercentre.po.system.SysUserResourcesPo;
import com.tsh.usercentre.po.user.UserBusinessPo;
import com.tsh.usercentre.vo.system.SysProjectVo;

*//**
 * 
 * @author ds
 * 
 *//*
@Service
@SuppressWarnings("unchecked")
public class SysResourcesService {
	@Resource
	SysResourcesDao sysResourcesDao;
	@Resource
	SysProjectDao sysProjectDao;
	@Resource
	UserDao userDao;
	@Resource
	UserBusinessDao userBusinessDao;
	@Resource
	SysRoleResourcesService sysRoleResourcesService;
	@Resource
	SystemService systemService;
	@Resource
	SysResourcesService sysResourcesService;
	@Resource
	UserService userService;
	@Resource
	SysRoleResourcesDao sysRoleResourcesDao;
	@Resource
	SysUserResourcesDao sysUserResourcesDao;

	*//**
	 * 
	 * @param result
	 * @param sysResourcesPo
	 * @return
	 * @throws Exception
	 *//*
	public Result saveSysResourcesSerivce(Result result, SysResourcesPo sysResourcesPo) throws Exception {
		return sysResourcesDao.saveSysResources(result, sysResourcesPo);
	}

	*//**
	 * 
	 * @param result
	 * @param id
	 * @return
	 * @throws Exception
	 *//*
	public Result getSysResourcesSerivce(Result result, Long id) throws Exception {
		return sysResourcesDao.getSysResources(result, id);
	}

	*//**
	 * 删除资源菜单
	 * 
	 * @param result
	 * @param id
	 * @return
	 * @throws Exception
	 *//*
	public Result delSysResourcesSerivce(Result result, Integer id) throws Exception {
		return sysResourcesDao.delSysResources(result, id);
	}

	*//**
	 * 删除资源菜单
	 * 
	 * @param result
	 * @param status
	 * @param id
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result updateSysResourcesStatusSerivce(Result result, Integer status, Long id, Long userId) throws Exception {
		return sysResourcesDao.updateSysResourcesStatus(result, status, id, userId);
	}

	*//**
	 * 根据资源菜单信息
	 * 
	 * @param result
	 * @param SysResourcesPo
	 * @return
	 * @throws Exception
	 *//*
	public Result updateResourcesSerivce(Result result, SysResourcesPo sysResourcesPo) throws Exception {
		return sysResourcesDao.updateResources(result, sysResourcesPo);
	}

	*//**
	 * 获取菜单分配权限
	 * 
	 * @param result
	 * @param proId
	 * @return
	 * @throws Exception
	 *//*
	public Result findResListByRole(Result result, String proId) throws Exception {
		result = sysResourcesDao.findResourcesListByPrjId(result, proId);
		List<SysResourcesPo> srpList = result.getData();

		result.setData(srpList);
		return result;
	}

	*//**
	 * 根据角色id,用户id获取资源菜单
	 * 
	 * @param result
	 * @param SysProjectPo
	 * @return
	 * @throws Exception
	 *//*
	public Result findResourcesByRoleIdSerivce(Result result, Integer roleType, Long userId) throws Exception {
		return sysResourcesDao.findResourcesByRoleId(result, roleType, userId);
	}

	*//**
	 * 获取商家权限（包含限权限）
	 * @param result
	 * @param roleType
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author ds
	 * @date 2017年2月23日
	 * @version 3.1.0
	 *//*
	public Result findResourcesByRoleTypeToUserId(Result result, Integer roleType, Long userId) throws Exception {

		List<SysResourcesPo> list = new ArrayList<SysResourcesPo>();
		List<SysProjectPo> listProject = new ArrayList<SysProjectPo>();
		List<SysResourcesPo> allList = (List<SysResourcesPo>) sysResourcesService.findResListByRole(result, null).getData();
		result = systemService.findProject(result, new SysProjectVo(), new Page<SysProjectPo>(1, 10000));
		if (result.getData() != null) {
			Pagination findPagination = result.getData();
			listProject = (List<SysProjectPo>) findPagination.getRows();
		}
		Long roleId = 0L;
		if (roleType.equals(RoleTypeEnum.plfsupplier.getSign())) {// 供应商账号
			UserBusinessPo userBusinessPo = userBusinessDao.getUserBusinessByRoleTypeToUserId(result, roleType, userId).getData();
			roleId = userService.getSupplierByBizId(result, userBusinessPo.getBizId());
		} else {
			roleId = Long.valueOf(roleType);
		}
		List<Long> resourcesList=new ArrayList<Long>();
		result = sysRoleResourcesDao.querySysRoleResourcesByRoleId(result, roleId);// 获取角色权限
		if(result.getData()!=null){
			resourcesList = result.getData();
		}
		result = sysUserResourcesDao.querySysUserResourcesByUserId(result, userId, null);// 获取用户权限
		List<SysUserResourcesPo> suList = result.getData();
		for (SysUserResourcesPo sysUserResourcesPo : suList) {
			if (sysUserResourcesPo.getStatus() == null || sysUserResourcesPo.getStatus() == 0) {
				resourcesList.add(sysUserResourcesPo.getResourcesId());
			}
		}
		List<Long> resList = new ArrayList<Long>(new HashSet<Long>(resourcesList));
		for (SysResourcesPo sysResourcesPo : allList) {
			for (Long long1 : resList) {
				if (long1.equals(sysResourcesPo.getId())) {
					sysResourcesPo.setAliases(null);
					list.add(sysResourcesPo);
				}
			}
		}
		JSONArray jsonArray = new JSONArray();
		for (SysResourcesPo sysResourcesPo : list) {
			if (sysResourcesPo.getParentId() < 1) {
				sysResourcesPo.setParentId(Long.valueOf(-1));
				JSONArray treeMenu = new JSONArray();
				String pId = sysResourcesPo.getParentId().toString();

				String menuId = sysResourcesPo.getId().toString();
				JSONObject jsonMenu = new JSONObject();
				jsonMenu.put("id", menuId);
				jsonMenu.put("parentMenuId", pId);
				jsonMenu.put("menuName", sysResourcesPo.getMenuName());
				jsonMenu.put("proName", sysResourcesPo.getMenuName());
				jsonMenu.put("sysId", sysResourcesPo.getPrjId());
				jsonMenu.put("aliases", sysResourcesPo.getAliases());

				JSONArray c_node = setTreeJSONArray(list, listProject, menuId);
				if (c_node.size() > 0) {
					jsonMenu.put("childNode", c_node);
				}
				treeMenu.add(jsonMenu);
				jsonArray.addAll(treeMenu);
			}
		}
		result.setData(jsonArray);
		return result;
	}

	*//**
	 * 将数组集合转换为树形集合并排除掉已关闭菜单
	 * 
	 * @param list
	 * @param listProject
	 * @param parentId
	 * @param roletype
	 * @return
	 *//*
	private JSONArray setTreeJSONArray(List<SysResourcesPo> list, List<SysProjectPo> listProject, String parentId) {
		JSONArray treeMenu = new JSONArray();
		for (SysResourcesPo po : list) {
			String pId = po.getParentId().toString();
			String menuStatus = po.getStatus().toString();
			if (parentId.equals(pId) && "1".equals(menuStatus)) {
				String menuId = po.getId().toString();
				JSONObject jsonMenu = new JSONObject();
				jsonMenu.put("id", menuId);
				jsonMenu.put("parentMenuId", pId);
				jsonMenu.put("menuName", po.getMenuName());
				jsonMenu.put("proName", po.getMenuName());
				jsonMenu.put("sysId", po.getPrjId());
				jsonMenu.put("aliases", po.getAliases());
				JSONArray c_node = setTreeJSONArray(list, listProject, menuId);
				if (c_node.size() > 0) {
					jsonMenu.put("childNode", c_node);
				}
				treeMenu.add(jsonMenu);

			}
		}
		return treeMenu;
	}
}
*/