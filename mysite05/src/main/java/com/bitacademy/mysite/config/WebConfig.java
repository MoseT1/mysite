package com.bitacademy.mysite.config;

import java.util.List;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bitacademy.mysite.security.AuthInterceptor;
import com.bitacademy.mysite.security.AuthUserHandlerMethodArgumentResolver;
import com.bitacademy.mysite.security.LoginInterceptor;
import com.bitacademy.mysite.security.LogoutInterceptor;

@SpringBootConfiguration
public class WebConfig implements WebMvcConfigurer {
	
	
	//Argument Resolver
	@Bean
	public  HandlerMethodArgumentResolver handlerMethodArgumentResolver() {
		return new AuthUserHandlerMethodArgumentResolver();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(handlerMethodArgumentResolver());
	}

	
	
	//Interceptors
	
	
	public HandlerInterceptor authInterceptor() {
		return new AuthInterceptor();
	}
	
	public HandlerInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}
	


	public HandlerInterceptor logoutInterceptor() {
		return new LogoutInterceptor();
	}
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor())
			.addPathPatterns("/user/auth");						//xml은 handler 없어도 되는데, java는 있어야한다.
		
		registry.addInterceptor(logoutInterceptor())
		.addPathPatterns("/user/logout");
		
		registry.addInterceptor(logoutInterceptor())
		.addPathPatterns("/user/**")
		.excludePathPatterns("/user/auth","/user/logout","/assets/**");
		
	}
}
