package com.lanou.cn.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 28/6/17.
 */
public interface MenuService {

	/**
	 * 获取所有一级菜单
	 * @return
	 */
	List<Map<String,Object>> getAllParentNodeInfo();

	/**
	 * 添加菜单
	 * @param params
	 */
	void addMenu(Map<String,Object> params);

	/**
	 * 分页查询菜单列表
	 * @param params
	 * @return
	 */
	PageInfo<Map<String,Object>> findMenusByPageList(Map<String, Object> params);

	/**
	 * 根据id获取菜单信息
	 * @param id
	 * @return
	 */
	Map<String,Object> findMenuInfoById(Integer id);

	/**
	 * 更新菜单信息
	 * @param params
	 */
	void updateMenu(Map<String,Object> params);
}
