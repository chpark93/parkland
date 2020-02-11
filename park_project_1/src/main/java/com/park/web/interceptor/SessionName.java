package com.park.web.interceptor;

public interface SessionName {
	
	static final String LOGIN = "loginUser";
	static final String LOGIN_COOKIE = "loginCookie";
	static final String ATTEMP = "attempedLocation";
	
	static final int EXPIRE = 7 * 24 * 60 * 60;
}
