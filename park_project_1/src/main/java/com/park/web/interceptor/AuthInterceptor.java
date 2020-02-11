package com.park.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.park.web.user.db.UserVO;
import com.park.web.user.service.UserService;

public class AuthInterceptor extends HandlerInterceptorAdapter implements SessionName {
	
	@Autowired
	private UserService userservice;
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("AuthInterceptor : preHandle");
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute(LOGIN) == null) {
			Cookie loginCookie = WebUtils.getCookie(request, SessionName.LOGIN_COOKIE);
			if(loginCookie != null) {
				UserVO loginUser = userservice.loginCheck(loginCookie.getValue());
				
				if(loginUser != null) {
					session.setAttribute(LOGIN, loginUser);
					return true;
				}
			}
			
			String uri = request.getRequestURI();
			String httpMethod = request.getMethod();
			
			if(StringUtils.contains(uri, "/replyBoard/") && !StringUtils.equalsIgnoreCase(httpMethod, "GET")) {
				response.sendError(401, "Need Login");
				return false;
			}
			
			saveLocation(request, session);
	
			response.sendRedirect("../login/login");
			
		}
		
		return true;
	}
	
	//요청 페이지 저장-로그인
	private void saveLocation(HttpServletRequest request, HttpSession session) {
		String uri = request.getRequestURI();
		String query = request.getQueryString();
		
		if(StringUtils.isNotEmpty(query)) {
			uri += "?" + query;
		}
		
		session.setAttribute(ATTEMP, uri);
	}
	
	
}
