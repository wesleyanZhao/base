package com.lanou.cn.controller;

/**
 * Created by admin on 13/7/17.
 */

import com.lanou.cn.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-context.xml" })
public class ControllerTest{

	@Resource
	UserMapper userMapper;

	@Test
	public void menuTest() {
		System.out.println(userMapper.getUserInfo("zhangsan"));
		String s ="";
		Object o = new Object();
	}
}
