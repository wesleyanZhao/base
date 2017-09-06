package com.lanou.cn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lanou.cn.mapper.MenuMapper;
import com.lanou.cn.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 28/6/17.
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Resource
	private MenuMapper menuMapper;

	@Override
	public List<Map<String, Object>> getAllParentNodeInfo() {
		return menuMapper.getAllParentNodeInfo();
	}

	@Override
	public void addMenu(Map<String, Object> params) {
		menuMapper.addMenu(params);
	}

	@Override
	public PageInfo<Map<String, Object>> findMenusByPageList(Map<String, Object> params) {
		Integer currentPage = params.get("currentPage") == null ? 1:Integer.parseInt((String)params.get("currentPage"));
		PageHelper.startPage(currentPage, 3);
		List<Map<String,Object>> list = menuMapper.findMenusByPageList(params);
		return new PageInfo<Map<String,Object>>(list);
	}

	@Override
	public Map<String, Object> findMenuInfoById(Integer id) {
		Map<String,Object> menuInfo = menuMapper.findMenuById(id);
		Integer pId = (Integer)menuInfo.get("pId");
		// 如果不是一级菜单则说需要进行管理的是一个二级菜单，那么久将所有的一级菜单都查出来
		List<Map<String,Object>> list = null;
		if(null != pId && 0 != pId) {
			list = menuMapper.getAllParentNodeInfo();
		} else {
			list = new ArrayList<>();
		}
		menuInfo.put("list",list);
		return menuInfo;
	}

	@Override
	public void updateMenu(Map<String, Object> params) {
		menuMapper.updateMenu(params);
	}
}
