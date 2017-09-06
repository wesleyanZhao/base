package com.lanou.cn.controller;

import com.lanou.cn.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 27/6/17.
 */
@Controller
@RequestMapping("/base")
public class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	private static final String LOGIN_INFO = "loginInfo";

	@Resource
	private BaseService baseService;

	@Resource
	private RedisTemplate<String,Object> redisTemplate;

	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping("login")
	public String login() {
		return "base/login";
	}

	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping("register")
	public String register() {
		return "base/register";
	}

	/**
	 * 没有权限页面
	 * @return
	 */
	@RequestMapping("noPermission")
	public String noPermission() {
		return "base/noPermission";
	}

	/**
	 * 登录提交、权限验证
	 * @param params
	 * @return
	 */
	@RequestMapping("loginForm")
	@ResponseBody
	public Map<String,String> loginForm(@RequestParam() Map<String,String> params, HttpServletRequest request) {
		Map<String,String> loginInfo = (Map<String,String>)redisTemplate.opsForValue().get("loginInfo");
		if(CollectionUtils.isEmpty(loginInfo)) {
			loginInfo = baseService.loginValidate(params.get("username"),params.get("password"));
			redisTemplate.opsForValue().set("loginInfo",loginInfo);
		}

		//登录成功，放入session中
		if("success".equals(loginInfo.get("result"))) {
			request.getSession().setAttribute(LOGIN_INFO,params.get("username"));
		}
		return loginInfo;
	}

	/**
	 * 新用户注册
	 * 学生完成
	 * @param params
	 * @return
	 */
	@RequestMapping("registerForm")
	@ResponseBody
	public Map<String,String> registerForm(@RequestParam() Map<String,Object> params) {
		Map<String,String> result = new HashMap<>();
		logger.info("registerForm 93" + params.toString());
		result.put("result","success");
		return result;
	}
}
