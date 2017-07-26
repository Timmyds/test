package com.fxb.world.web;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fxb.world.entity.TUser;
import com.fxb.world.service.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootShiroApplicationTests {
	
	 @Resource
	   private UserServiceImpl userServiceImpl;
	@Test
	public void contextLoads() {
		try {
			TUser selectByUsername = userServiceImpl.selectByUsername(1L);
			System.out.println(selectByUsername);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}

}
