package com.bitacademy.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);
	
	
	@ExceptionHandler(Exception.class)
	public String handleException(Model model, Exception ex) {
		
		// 1. 404 Error 처리
		if(ex instanceof NoHandlerFoundException) {
			return "error/exception/404";
		}
		
		//2. 로깅
		StringWriter errors = new StringWriter();
		
		ex.printStackTrace(new PrintWriter(errors));
		
		logger.error(errors.toString());
	
		
		//3. 사과 페이지
		model.addAttribute("exception", errors.toString());
		return "error/exception";
		
		
	}
}
