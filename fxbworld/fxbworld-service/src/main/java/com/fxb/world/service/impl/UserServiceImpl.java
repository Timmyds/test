package com.fxb.world.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fxb.world.dao.TUserMapper;
import com.fxb.world.entity.TUser;
import com.fxb.world.service.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

/**
 * Created by yangqj on 2017/4/21.
 */
@Service
public class UserServiceImpl  extends BaseService<TUser>{
    @Resource
    private TUserMapper tUserMapper;

    
    public PageInfo<TUser> selectByPage(TUser user, int start, int length) {
        int page = start/length+1;
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(user.getUserName())) {
            criteria.andLike("username", "%" + user.getUserName() + "%");
        }
        if (user.getId() != null) {
            criteria.andEqualTo("id", user.getId());
        }
    
        //分页查询
        PageHelper.startPage(page, length);
        List<TUser> userList = selectByExample(example);
        return new PageInfo<>(userList);
    }

    
    public TUser selectByUsername(Long id) {
    	TUser tUser=tUserMapper.selectByPrimaryKey(id);
      
          return tUser;
    }

    
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
    public void delUser(Integer userid) {
    /*    //删除用户表
        mapper.deleteByPrimaryKey(userid);
        //删除用户角色表
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userid",userid);
        userRoleMapper.deleteByExample(example);*/
    }

	
	
}
