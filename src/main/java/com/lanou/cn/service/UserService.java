package com.lanou.cn.service;

import java.util.Map;

/**
 * Created by admin on 28/6/17.
 */
public interface UserService {

	/**
	 * 获取
	 * @param username
	 * @return
	 */
	Map<String,Object> getUserInfo(String username);

}
