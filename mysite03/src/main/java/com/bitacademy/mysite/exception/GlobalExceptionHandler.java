package com.bitacademy.mysite.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(Exception.class)
	public String handleException(Exception ex) {
		//1. 로깅
		ex.printStackTrace();
		
		//2. 사과 페이지
		return "error/exception";
		
		
	}
}
