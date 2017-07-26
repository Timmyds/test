package com.fxb.world.dao;

import java.util.List;

import com.fxb.world.entity.TUserRole;
import com.fxb.world.mapper.MyMapper;

public interface TUserRoleMapper extends MyMapper<TUserRole> {
	
	List<TUserRole> getUserRoleByUserId(Long userId);
	
}