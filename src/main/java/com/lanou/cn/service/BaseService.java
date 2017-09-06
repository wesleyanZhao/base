package com.lanou.cn.service;

import com.github.pagehelper.PageInfo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 28/6/17.
 */
public interface BaseService {
	int a =0;

	/**
	 * 根据登录username查找登录用户
	 * @param username
	 * @return
	 */
	Map<String,Object> findUserAuthInfoByName(String username);

	/**
	 * 验证登录信息
	 * @param username
	 * @param password
	 * @return
	 */
	Map<String,String> loginValidate(String username, String password);

	/**
	 * 获取用户权限url信息
	 * @param id
	 * @return
	 */
	Set<String> findUserAuthList(int id);

	/**
	 * 获取全部菜单
	 * @return
	 */
	List<LinkedHashMap<String,Object>> findAllMenuList();


	/**
	 * 分页demo
	 * @param params
	 * @return
	 */
	PageInfo<Map<String,Object>> findAllUsersPageList(Map<String, Object> params);
}
