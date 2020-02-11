package com.park.web.oauth;

import org.apache.commons.lang3.StringUtils;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.api.DefaultApi20;

public class SnsValue implements SnsUrl{

	private String service;
	private String client_id;
	private String client_secret;
	private String redirectUrl;
	private String profile;
	
	private DefaultApi20 apiInstance;
	
	private boolean isNaver;
	private boolean isGoogle;
	private boolean isKakao;
	
	
	public SnsValue(String service, String client_id, String client_secret, String redirectUrl) {
		this.service = service;
		this.client_id = client_id;
		this.client_secret = client_secret;
		this.redirectUrl = redirectUrl;
		
		this.isNaver = StringUtils.equalsIgnoreCase("naver", this.service);
		this.isGoogle = StringUtils.equalsIgnoreCase("google", this.service);
		
		
		if(isNaver) {
			this.apiInstance = NaverApi20.instance();
			this.profile = NAVER_PROFILE_URL;
		}
		else if(isGoogle) {
			this.apiInstance = GoogleApi20.instance();
			this.profile = GOOGLE_PROFILE_URL;
		}
		
	}
	
	public SnsValue(String service, String client_id, String redirectUrl) {
		this.service = service;
		this.client_id = client_id;
		this.redirectUrl = redirectUrl;
		
		this.isKakao = StringUtils.equalsIgnoreCase("kakao", this.service);
		
		if(isKakao) {
			this.apiInstance = KakaoApi.instance();
			this.profile = KAKAO_PROFILE_URL;
		}
	}
	
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClient_secret() {
		return client_secret;
	}
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public DefaultApi20 getApiInstance() {
		return apiInstance;
	}
	public void setApiInstance(DefaultApi20 apiInstance) {
		this.apiInstance = apiInstance;
	}
	public boolean isNaver() {
		return isNaver;
	}
	public void setNaver(boolean isNaver) {
		this.isNaver = isNaver;
	}
	public boolean isGoogle() {
		return isGoogle;
	}
	public void setGoogle(boolean isGoogle) {
		this.isGoogle = isGoogle;
	}
	
	public boolean isKakao() {
		return isKakao;
	}
	public void setKakao(boolean isKakao) {
		this.isKakao = isKakao;
	}
	
}
