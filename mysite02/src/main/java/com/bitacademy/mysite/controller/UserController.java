package com.bitacademy.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitacademy.mysite.dao.UserDao;
import com.bitacademy.mysite.vo.UserVo;

public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String actionName = request.getParameter("a");
		if("joinform".equals(actionName)) {
			request
				.getRequestDispatcher("/WEB-INF/views/user/joinform.jsp")
				.forward(request, response);
		} else if("joinsuccess".equals(actionName)) {
			request
				.getRequestDispatcher("/WEB-INF/views/user/joinsuccess.jsp")
				.forward(request, response);
		} else if("join".equals(actionName)) {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			UserVo vo = new UserVo();
			vo.setName(name);
			vo.setEmail(email);
			vo.setPassword(password);
			vo.setGender(gender);
			
			new UserDao().insert(vo);
			
			response.sendRedirect(request.getContextPath() + "/user?a=joinsuccess");
		} else if("loginform".equals(actionName)) {
			request
				.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp")
				.forward(request, response);
		} else if("login".equals(actionName)) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			UserVo authUser = new UserDao().findByEmailAndPassword(email, password);
			if(authUser == null) {
				// 인증 실패
				// response.sendRedirect(request.getContextPath() + "/user?a=loginform&result=fail");
				request.setAttribute("result", "fail");
				request
					.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp")
					.forward(request, response);				
				return;
			}
			
			// 인증 성공
			HttpSession session = request.getSession(true);
			session.setAttribute("authUser", authUser);
			
			response.sendRedirect(request.getContextPath()+"/main");
		}else if ("logout".equals(actionName)) {
			HttpSession session = request.getSession();
			
			session.removeAttribute("authUser");
			session.invalidate();
			response.sendRedirect(request.getContextPath()+"/main");
		}else if("updateform".equals(actionName)) {
			// Access Control
			///////////////////////////////////////////////////////////
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			if(authUser == null) {
				response.sendRedirect(request.getContextPath());
				return;
			}
			
			
			UserVo userVo = new UserDao().findByNo(authUser.getNo());
			request.setAttribute("userVo", userVo);
			
			request
				.getRequestDispatcher("/WEB-INF/views/user/updateform.jsp")
				.forward(request, response);
		} else if ("update".equals(actionName)) {
			
			//개인정보 수정은 거의 하지 않으므로 메모리에 담아놓지 않고, db에 때리는게 낫다.
			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			//보안을 위해서 반드시 해줘야한다. (Access Control)////////////////////////////
			//authUser가 필요한 부분에서는 항상 해줘야 한다.
			if(authUser == null) {
				response.sendRedirect(request.getContextPath()+"/main");
				return;
			}
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			UserVo vo = new UserVo();
			vo.setName(name);
			vo.setEmail(email);
			vo.setPassword(password);
			vo.setGender(gender);
			vo.setNo(authUser.getNo());
	
			new UserDao().update(vo);
			authUser.setName(name);
			
			request.setAttribute("userVo", vo);
			response.sendRedirect(request.getContextPath()+"/user?a=updateform");
		}else {
			response.sendRedirect(request.getContextPath() + "/main");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}