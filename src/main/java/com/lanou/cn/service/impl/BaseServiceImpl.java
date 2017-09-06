package com.lanou.cn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lanou.cn.mapper.BaseMapper;
import com.lanou.cn.service.BaseService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by admin on 28/6/17.
 */
@Service
public class BaseServiceImpl implements BaseService {

	@Resource
	private BaseMapper baseMapper;

	@Override
	public Map<String, Object> findUserAuthInfoByName(String username) {
		return baseMapper.findUserByUsername(username);
	}

	@Override
	public Map<String, String> loginValidate(String username, String password) {
		Map<String,String> result = new HashMap<>();
		String massage = "";

		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			if (subject.isAuthenticated()) {
				result.put("result","success");
				result.put("loginUser",username);
				return result;
			} else {
				result.put("result","invalid");
				return result;
			}
		} catch (IncorrectCredentialsException e) {
			massage = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";
			result.put("result","invalid");
			result.put("message",massage);
			return result;
		} catch (ExcessiveAttemptsException e) {
			massage = "登录失败次数过多";
			result.put("result","invalid");
			result.put("message",massage);
			return result;
		} catch (LockedAccountException e) {
			massage = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
			result.put("result","invalid");
			result.put("message",massage);
			return result;
		} catch (DisabledAccountException e) {
			massage = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
			result.put("result","invalid");
			result.put("message",massage);
			return result;
		} catch (ExpiredCredentialsException e) {
			massage = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
			result.put("result","invalid");
			result.put("message",massage);
			return result;
		} catch (UnknownAccountException e) {
			massage = "帐号不存在. There is no user with username of " + token.getPrincipal();
			result.put("result","invalid");
			result.put("message",massage);
			return result;
		} catch (UnauthorizedException e) {
			massage = "您没有得到相应的授权！" + e.getMessage();
			result.put("result","invalid");
			result.put("message",massage);
			return result;
		}
	}

	@Override
	public Set<String> findUserAuthList(int id) {
		return baseMapper.findUserAuthList(id);
	}

	@Override
	public List<LinkedHashMap<String,Object>> findAllMenuList() {
		return baseMapper.findAllMenuList();
	}


	@Override
	public PageInfo<Map<String, Object>> findAllUsersPageList(Map<String,Object> params) {
		Integer currentPage = params.get("currentPage") == null ? 1:Integer.parseInt((String)params.get("currentPage"));

		PageHelper.startPage(currentPage, 5);
		List<Map<String,Object>> list = baseMapper.findUsers(null);
		//用PageInfo对结果进行包装
		PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(list);
		//测试PageInfo全部属性
		return page;
	}

}
