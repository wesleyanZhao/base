package com.lanou.cn.controller;

import com.lanou.cn.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource
	private UserService userService;

	@RequestMapping("getUserInfo")
	public ModelAndView getUserInfo(String loginUser) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user/userInfo");
		Map<String,Object> result = userService.getUserInfo(loginUser);
		// 判断是否为空，不为空的话， 说明之前设置过，需要将之前设置的信息展示出来
		if(CollectionUtils.isEmpty(result)) {
			result = new HashMap<>();
		}
		modelAndView.addObject("result",result);
		return modelAndView;
	}

	@RequestMapping("userInfoForm")
	@ResponseBody
	public Map<String,Object> userInfoForm(@RequestParam Map<String,Object> params) {
		System.out.println(params);
		Map<String,Object> result = new HashMap<>();
		result.put("result","success");
		return result;
	}

	@RequestMapping("upload")
	@ResponseBody
	public Map<String,Object> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		Map<String,Object> result = new HashMap<>();
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				// 文件保存路径
				String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/avatar/" + file.getOriginalFilename();
				// 转存文件
				file.transferTo(new File(filePath));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		result.put("result","success");
		return result;
	}
}
