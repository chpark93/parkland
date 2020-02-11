package com.park.web.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.park.mapper.UserMapper;
import com.park.web.user.db.UserVO;


public class LoginInterceptor extends HandlerInterceptorAdapter implements SessionName {

	@Inject
	private UserMapper userMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		System.out.println("loginInterceptor : preHandle");
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute(LOGIN) != null) {
			session.removeAttribute(LOGIN);
		}
		
		return true;
	}
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
		HttpSession session = request.getSession();
		
		UserVO user = (UserVO) mav.getModelMap().get("user");
		System.out.println("loginInterceptor : postHandle >> " + user);
		
		
		if(user != null) {
			session.setAttribute(LOGIN, user);
			userMapper.updateLogin(user.getId());
			
			if(StringUtils.isNotEmpty(request.getParameter("customCheck"))) {
				Cookie loginCookie = new Cookie(LOGIN_COOKIE, session.getId());
				loginCookie.setPath("/");
				loginCookie.setMaxAge(EXPIRE);
				
				response.addCookie(loginCookie);
			}
			
			String attemp = (String) session.getAttribute(ATTEMP);
			if(StringUtils.isNotEmpty(attemp)) {
				response.sendRedirect(attemp);
				
				session.removeAttribute(ATTEMP);
			}
			else {
				response.sendRedirect("../main/mainPage");
				
			}
			
			
		}
		
	
	}
	
	
	
	
	
	
	
	
}
