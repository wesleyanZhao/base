package com.lanou.cn.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * Created by admin on 28/6/17.
 */
public interface UserMapper {

	/**
	 * 查询用户个人信息
	 * @param username
	 * @return
	 */
	@Select("select ui.nickname,date_format(ui.birthday, '%Y-%m-%d') birthday,ui.sign,ui.avatar from users u ,user_info ui where u.id = ui.user_id and u.username=#{username}")
	Map<String,Object> getUserInfo(@Param("username")String username);
}
