package com.bitacademy.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitacademy.mysite.service.UserService;
import com.bitacademy.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value ="/join", method=RequestMethod.GET)
	public String join() {
		return "/user/join";								//   /WEB-INF/views .....jsp 가 반복되므로 이걸 viewResolve에 전달해서 자동으로 설정하도록 함.
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(UserVo vo) {  				//post로 오는 parameter가 vo의 변수명과 같으면 맵핑해줌
		
		userService.addUser(vo);
		
		return "redirect:/joinsuccess.jsp";
	}
	
	
	@RequestMapping(value ="/joinsuccess")
	public String joinsuccess() {
		return "/user/joinsuccess";
	}
	
}
