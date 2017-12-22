package com.as.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.as.model.SUser;
import com.as.service.SUserService;
import com.as.util.Result;

@Controller
public class SUserController {
	@Resource
	private SUserService sUserService;
	/**
	 * 登录
	 */
	@RequestMapping(value="/anon/login" ,method=RequestMethod.POST)
	@ResponseBody
	public Result<?> login(@RequestBody SUser sUser){
		try {
			Map<String, Object>	map = sUserService.login(sUser);
			return new Result<>(true,map);
		
		}catch (Exception e) {
			return new Result<>("login fail");
		}
		
	}
	/**
	 * 
	 * @param sUser
	 * @return
	 */
	@RequestMapping(value="/anon/regist" ,method=RequestMethod.POST)
	@ResponseBody
	public Result<?> regist(@RequestBody SUser sUser){
		try {
			sUserService.regist(sUser);
		} finally {
		}
		return new Result<>("regist fail");
		
	}
}
