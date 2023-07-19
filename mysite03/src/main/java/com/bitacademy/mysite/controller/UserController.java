package com.bitacademy.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitacademy.mysite.service.UserService;
import com.bitacademy.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "/user/join"; // /WEB-INF/views .....jsp 가 반복되므로 이걸 viewResolve에 전달해서 자동으로 설정하도록 함.
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserVo vo) { // post로 오는 parameter가 vo의 변수명과 같으면 맵핑해줌

		userService.addUser(vo);

		return "redirect:/joinsuccess.jsp";
	}

	@RequestMapping(value = "/joinsuccess")
	public String joinsuccess() {
		return "/user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/user/login"; // /WEB-INF/views .....jsp 가 반복되므로 이걸 viewResolve에 전달해서 자동으로 설정하도록 함.
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpSession session, UserVo vo, Model model) { // 여기까지 배운 지식으로는 기술 침투할수밖에 없다. 나중에 기술 분리해주어야한다.

		UserVo authUser = userService.getUser(vo);
		if (authUser == null) {

			model.addAttribute("result", "fail");
			return "user/login";
		}
		/* 로그인 처리 */
		session.setAttribute("authUser", authUser);

		return "redirect:/";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) { // 여기까지 배운 지식으로는 기술 침투할수밖에 없다. 나중에 기술 분리해주어야한다.

		// 접근 제어
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}
		/* 로그아웃 처리 */
		session.removeAttribute("authUser");
		session.invalidate();

		return "redirect:/";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(HttpSession session, Model model) {
		// 접근 제어
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}
		
		Long no = authUser.getNo();
		UserVo userVo = userService.getUser(no);
		model.addAttribute("userVo", userVo);
		return "/user/update";
	}

	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpSession session, UserVo vo) {
		// 접근 제어
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}
		vo.setNo(authUser.getNo());
		userService.updateUser(vo);
		
		authUser.setName(vo.getName());
		return "redirect:/user/update";
	}
	
//	@ExceptionHandler(Exception.class)
//	public String exceptionHandler(Exception ex) {
//		
//		//로그
//		System.out.println(ex);
//		
//		
//		return "error/exception";
//	}
}
