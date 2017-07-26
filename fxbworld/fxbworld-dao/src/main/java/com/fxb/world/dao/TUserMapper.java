package com.fxb.world.dao;

import com.fxb.world.entity.TUser;
import com.fxb.world.mapper.MyMapper;

public interface TUserMapper extends MyMapper<TUser> {

	public  int updatePhone(String phone, long id);
	public  int  updatePwd(String password, long id);
	TUser getUserByLoginNameToPhone(String loginName,String  phone);
	
	int  updateStatus(Integer status, long id);
	
	TUser getUserByLongName(String loginName);
}