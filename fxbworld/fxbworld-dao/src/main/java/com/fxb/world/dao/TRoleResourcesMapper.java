package com.fxb.world.dao;

import java.util.List;

import com.fxb.world.entity.TRoleResources;
import tk.mybatis.mapper.common.Mapper;

public interface TRoleResourcesMapper extends Mapper<TRoleResources> {
	
	List<TRoleResources> queryRoleResourcesByRoleId(Long roleId);
}