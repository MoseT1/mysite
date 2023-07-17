 package com.bitacademy.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitacademy.mysite.dao.BoardDao;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.mysite.vo.UserVo;

public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String actionName = request.getParameter("a");
		if("writeform".equals(actionName)) {
			request
				.getRequestDispatcher("WEB-INF/views/board/write.jsp")
				.forward(request, response);
		} else if ("view".equals(actionName)) {
			
			//글의 no를 받아서 findByNO. 글을 DB에서 받아서 view.jsp로 전달
			String no = request.getParameter("no");
			BoardVo vo = new BoardDao().findByNO(no);
			if (vo == null) {
				response.sendRedirect(request.getContextPath()+"/board");
				return;
			}
			
			request.setAttribute("boardVo", vo);
			request
				.getRequestDispatcher("WEB-INF/views/board/view.jsp")
				.forward(request, response);
			
		} else if ("write".equals(actionName)) {
			//write.jsp에서 order 정보를 받아와야함. 유저 정보는 authUser에서
			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			if(authUser == null) {
				response.sendRedirect(request.getContextPath()+"/board");
				return;
			}
			
			String title = request.getParameter("title");
			String contents = request.getParameter("contents");
			
			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setUserNo(authUser.getNo());
			
			new BoardDao().insert(vo);
			
			response.sendRedirect(request.getContextPath()+"/board");
		}else if ("modifyform".equals(actionName)){
			
			//view.jsp에서 no를 받음. findByNo를 해서 데이터를 modify.jsp로 전달
			String no = request.getParameter("no");
			
			BoardVo vo = new BoardDao().findByNO(no);
			
			request.setAttribute("boardVo", vo);			
			request
				.getRequestDispatcher("WEB-INF/views/board/modify.jsp")
				.forward(request, response);
		}else if ("modify".equals(actionName)) {
			
			//modify.jsp에서 no, title, contents를 받음.  dao로 업데이트
			String no = request.getParameter("no");
			String title = request.getParameter("title");
			String contents = request.getParameter("contents");
			
			new BoardDao().updateTitleAndContentsByNO(no, title, contents);
			
			response.sendRedirect(request.getContextPath()+"/board?a=view&no="+no);
			
		} else if("replyform".equals(actionName)){
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			if(authUser == null) {
				response.sendRedirect(request.getContextPath()+"/board");
				return;
			}
			String no = request.getParameter("no");
			
			BoardVo vo = new BoardDao().findByNO(no);
			request.setAttribute("boardVo", vo);
			
			request
			.getRequestDispatcher("WEB-INF/views/board/reply.jsp")
			.forward(request, response);
			
		} else if("reply".equals(actionName)){
			
			String title = request.getParameter("title");
			String contents = request.getParameter("contents");
			String gNo = request.getParameter("gNo");
			String depth = request.getParameter("depth");
			String userNo = request.getParameter("userNo");
			
			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setgNo(Long.parseLong(gNo));
			vo.setDepth(Long.parseLong(depth)+1);
			vo.setUserNo(Long.parseLong(userNo));
			
			new BoardDao().insertToReply(vo);                                //질문 : insert도 종류에 따라 Dao를 따로 만들어야하는지. 아니면 좀 더 생각해서 하나의 코드로 구현하는게 좋은지
			
			response.sendRedirect(request.getContextPath()+"/board");
		
		}else if ("delete".equals(actionName)){
			String no = request.getParameter("no");
			
			new BoardDao().deleteByNo(no);
			response.sendRedirect(request.getContextPath()+"/board");
		}else {
			// default action
			BoardDao dao = new BoardDao();
			List<BoardVo> list = dao.findAll();
			
		
			request.setAttribute("list", list);
			request.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);
			
			
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
