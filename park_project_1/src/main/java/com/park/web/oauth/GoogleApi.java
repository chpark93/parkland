package com.park.web.oauth;

import com.github.scribejava.apis.GoogleApi20;

public class GoogleApi extends GoogleApi20 implements SnsUrl{
	
	private GoogleApi() {
	}
	
	private static class InstanceHolder {
		private static final GoogleApi INSTANCE = new GoogleApi();
	}
	
	public static GoogleApi instance() {
		return InstanceHolder.INSTANCE;
	}

	@Override
    public String getAccessTokenEndpoint() {
        return GOOGLE_ACCESS_TOKEN;
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return GOOGLE_AUTH;
    }
    
    public static String getAuthorizationUrl() {
		String googleUrl = GOOGLE_AUTH + "?client_id=" + "********************************" + "&redirect_uri=" + "http://chparkland.com/park_project_1/login/auth/google/callback" + "&response_type=code";
	
		return googleUrl;
	}
    
    
	
}
