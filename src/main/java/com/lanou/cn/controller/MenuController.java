package com.lanou.cn.controller;

import com.github.pagehelper.PageInfo;
import com.lanou.cn.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

	public static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	private static final String HOST_URL = "http://47.94.85.134:8080";

	@Resource
	private MenuService menuService;

	@RequestMapping("addMenu")
	public String addMenu() {
		return "/menu/addMenu";
	}

	@RequestMapping("getAllParentNodeInfo")
	@ResponseBody
	public List<Map<String,Object>> getAllParentNodeInfo() {
		return menuService.getAllParentNodeInfo();
		/*RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
		return restTemplate.postForObject(HOST_URL + "/menu/getAllParentNodeInfo.do",bodyMap, List.class);*/
	}

	@RequestMapping("addMenuForm")
	@ResponseBody
	public Map<String,Object> addMenuForm(@RequestParam Map<String,Object> params) {
		Map<String,Object> result = new HashMap<>();
		try {
			menuService.addMenu(params);
			result.put("result","success");
		} catch (Exception e) {
			result.put("result","failuer");
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("getMenuListByPage")
	public ModelAndView getMenuListByPage(@RequestParam Map<String,Object> params) {
		ModelAndView modelAndView = new ModelAndView("/menu/menuList");
		PageInfo<Map<String, Object>> pageInfo = menuService.findMenusByPageList(params);
		modelAndView.addObject("page",pageInfo);
		modelAndView.addObject("list",pageInfo.getList());
		modelAndView.addObject("params",params);

		return modelAndView;
	}

	@RequestMapping("updateMenu")
	public ModelAndView updateMenu(String id) {
		ModelAndView modelAndView = new ModelAndView("/menu/updateMenu");
		Map<String,Object> result = menuService.findMenuInfoById(Integer.parseInt(id));
		modelAndView.addObject("result",result);
		return modelAndView;
	}

	@RequestMapping("updateMenuForm")
	@ResponseBody
	public Map<String,Object> updateMenuForm(@RequestParam Map<String,Object> params) {
		Map<String,Object> result = new HashMap<>();
		if(StringUtils.isEmpty(params.get("pId"))) {
			params.put("pId",0);
		}
		try {
			menuService.updateMenu(params);
			result.put("result","success");
		} catch (Exception e) {
			result.put("result","failuer");
			e.printStackTrace();
		}
		return result;
	}
}
