package com.as.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	@RequestMapping("/anon/test")
	@ResponseBody
	public String test(){
		return "test";
		
	}
	@RequestMapping("/test/test")
	@ResponseBody
	public String test2(){
		return "/test/test";
		
	}
}
