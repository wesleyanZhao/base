package com.lanou.cn.service.impl;

import com.lanou.cn.mapper.UserMapper;
import com.lanou.cn.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by admin on 28/6/17.
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Override
	public Map<String, Object> getUserInfo(String username) {
		return userMapper.getUserInfo(username);
	}
}
