package com.park.web.oauth;

public interface SnsUrl {
	//네이버
	static final String NAVER_ACCESS_TOKEN = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
	static final String NAVER_AUTH = "https://nid.naver.com/oauth2.0/authorize";
	static final String NAVER_PROFILE_URL = "https://openapi.naver.com/v1/nid/me";
		
	//구글
	static final String GOOGLE_ACCESS_TOKEN = "https://www.googleapis.com/oauth2/v4/token";
	static final String GOOGLE_AUTH = "https://accounts.google.com/o/oauth2/auth";	
	static final String GOOGLE_PROFILE_URL = "https://www.googleapis.com/plus/v1/people/me";
	
	//카카오
	static final String KAKAO_ACCESS_TOKEN = "https://kauth.kakao.com/oauth/token?grant_type=authorization_code";
	static final String KAKAO_AUTH = "https://kauth.kakao.com/oauth/authorize";
	static final String KAKAO_PROFILE_URL = "https://kapi.kakao.com/v2/user/me";

}
