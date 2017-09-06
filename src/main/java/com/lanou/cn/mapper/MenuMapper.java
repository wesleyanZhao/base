package com.lanou.cn.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 28/6/17.
 */
public interface MenuMapper {


	/**
	 * 查询所有一级node
	 * @return
	 */
	@Select("select id, name from menu where p_id=0")
	List<Map<String,Object>> getAllParentNodeInfo();

	/**
	 * 添加菜单
	 * @return
	 */
	@Insert("insert into menu(p_id,name,url,is_used) values(#{params.pId},#{params.name},#{params.url},#{params.isUsed})")
	void addMenu(@Param("params") Map<String,Object> params);

	/**
	 * 分页查询菜单列表
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> findMenusByPageList(@Param("params") Map<String,Object> params);


	/**
	 * 根据id获取菜单信息
	 * @param id
	 * @return
	 */
	@Select("select id, p_id as pId, name, url, is_used as isUsed from menu where id=#{id}")
	Map<String,Object> findMenuById(@Param("id") Integer id);


	/**
	 * 更新菜单
	 * @param params
	 */
	void updateMenu(@Param("params") Map<String,Object> params);
}
