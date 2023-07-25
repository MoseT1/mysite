package com.bitacademy.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.bitacademy.mysite.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 1. Handler 종류 확인 ( 캐스팅하기 위해) : HandlerMapping에서 url로 가져오면 HandlerMethod 객체인데, 이미지같은건 default여서 객체가 다르다.
		
		if(!(handler instanceof HandlerMethod) ) {
			// handler가 DefaultServletHandler 객체. (정적 자원, /assets/**) spring-servlet.xml에서 인터셉터 경로에서 빼긴했지만, 안전 상 적어줌.
			return true;
		}
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod) handler;   // 캐스팅해줘야 정보를 가져올 수 있다. 
		
		// 3. Handler의 @Auth 가져오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 4. @Auth가 없으면 인증이 필요없음.
		if(auth == null) {
			return true;
		}
		
		// 5. 인증(Authentication) 여부 확인
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		
		// 인증이 안된 경우
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login"); //servlet이므로 경로 다 적어줘야한다.
			return false;  													//handler 실행 못되도록 해줘야함
		}
		
		// 인증 된 경우
		return true;
		
		
		
	}

}
