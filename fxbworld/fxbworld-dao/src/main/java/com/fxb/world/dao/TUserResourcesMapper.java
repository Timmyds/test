package com.fxb.world.dao;

import java.util.List;

import com.fxb.world.entity.TUserResources;
import com.fxb.world.mapper.MyMapper;

public interface TUserResourcesMapper extends MyMapper<TUserResources> {
	List<TUserResources> queryUserResourcesByUserId(Long userId);
}