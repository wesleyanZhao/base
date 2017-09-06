package com.lanou.cn.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.lanou.cn.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 27/6/17.
 */
@Controller
@RequestMapping("/index")
public class IndexController {

	public static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Resource
	private BaseService baseService;

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("home")
	public ModelAndView home(String loginUser) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("base/home");
		List<LinkedHashMap<String,Object>> list = baseService.findAllMenuList();

		String result = JSONArray.toJSONString(list);
		modelAndView.addObject("result",result);
		modelAndView.addObject("loginUser",null == loginUser ? "未登录" : loginUser);
		return modelAndView;
	}

	/**
	 * 分页例子
	 * @return
	 */
	@RequestMapping("mainPage")
	public ModelAndView mainPage(@RequestParam Map<String,Object> params){
		ModelAndView modelAndView = new ModelAndView();
		// 后期需要优化
		PageInfo<Map<String, Object>> pageInfo = baseService.findAllUsersPageList(params);
		modelAndView.addObject("page",pageInfo);
		modelAndView.addObject("list",pageInfo.getList());
		modelAndView.setViewName("/base/mainPage");
		return modelAndView;
	}
}
