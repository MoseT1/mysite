package com.bitacademy.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitacademy.mysite.vo.UserVo;

@Controller
public class MainController {
	
	
	@RequestMapping({"/", "/main"})
	public String index() {
		return "/main/index";
	}
	
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		return "<h1>Hello World<h1>";
	}
	
	@ResponseBody
	@RequestMapping("/msg02")
	public String message02(String name) {
		return "<h1>안녕" + name + " <h1>";    //encoding 설정 안하면 한글은 깨진다. UTF-8로 설정해줘야한다. 
	}
	
	
	
	//객체를 보내는게 API다. Java의 객체를 JS의 객체로 변환
	
	@ResponseBody
	@RequestMapping("/msg03")
	public UserVo message03() {
		UserVo vo = new UserVo();
		vo.setNo(1L);
		vo.setName("홍길동");
		vo.setEmail("gildong@naver.com");
		
		return vo;
	}
}
