package com.bitacademy.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitacademy.mysite.dao.GuestbookDao;
import com.bitacademy.mysite.vo.GuestbookVo;
import com.bitacademy.mysite.vo.UserVo;


public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String actionName = request.getParameter("a");
		
		if ("add".equals(actionName)) {
			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			String name = null;
			if(authUser != null) {
				name = authUser.getName();
			} else {
				name = request.getParameter("name");
			}
			String password = request.getParameter("password");
			String message = request.getParameter("message");

			GuestbookVo vo = new GuestbookVo();
			
			vo.setName(name);
			vo.setPassword(password);
			vo.setMessage(message);

			GuestbookDao dao = new GuestbookDao();
			dao.insert(vo);

			response.sendRedirect(request.getContextPath() + "/guestbook");
		}else if ("delete".equals(actionName)) {
			String sno = request.getParameter("no");
			Long no = Long.parseLong(sno); 
			String password = request.getParameter("password");
			
			new GuestbookDao().deleteByPassword(no, password);
			
			
			response.sendRedirect(request.getContextPath() + "/guestbook");
		}else if("deleteform".equals(actionName)){
			String no = request.getParameter("no");
			request.setAttribute("no", no);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/deleteform.jsp");
			rd.forward(request, response);
		}else {
			GuestbookDao dao = new GuestbookDao();
			List<GuestbookVo> list = dao.findAll();
			
		
			request.setAttribute("list", list);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/list.jsp");
			rd.forward(request, response);
		}
		
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
