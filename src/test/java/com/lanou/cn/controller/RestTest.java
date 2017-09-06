package com.lanou.cn.controller;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * 测试rest接口
 *
 * Created by admin on 15/7/17.
 */
public class RestTest {

	private static final String HOST_URL = "http://192.168.2.39:8080";
	//private static final String HOST_URL = "http://47.94.85.134:8080";

	public static void main(String[] args) throws Throwable {

		//getAllParentNodeInfo();

		login();
	}

	public static void login() {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
		bodyMap.add("username","zhangsan");
		bodyMap.add("password","1234");
		Map<String,Object> result = restTemplate.postForObject(HOST_URL + "/rest/getDetailedPrdForPlan.do",bodyMap, Map.class);
		System.out.println(result);
	}

	public static void getAllParentNodeInfo() {
		RestTemplate restTemplate = new RestTemplate();
		//MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
		List<Map<String,Object>> result = restTemplate.getForObject(HOST_URL + "/menu/getAllParentNodeInfo.do",List.class);
		for(Map<String,Object> m : result) {
			System.out.println(m);
		}
		//System.out.println(result);
	}
}
