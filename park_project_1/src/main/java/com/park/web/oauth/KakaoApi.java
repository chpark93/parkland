package com.park.web.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.api.DefaultApi20;

public class KakaoApi extends DefaultApi20 implements SnsUrl{
	
	private KakaoApi() {
	}
	
	private static class InstanceHolder {
		private static final KakaoApi INSTANCE = new KakaoApi();
	}
	
	public static KakaoApi instance() {
		return InstanceHolder.INSTANCE;
	}
	
	
	@Override
	public String getAccessTokenEndpoint() {
		
		return KAKAO_ACCESS_TOKEN;
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		
		return KAKAO_AUTH;
	}

	
	public static String getAuthorizationUrl() {
		String kakaoUrl = KAKAO_AUTH + "?client_id=" + "************************" + "&redirect_uri=" + "http://chparkland.com/park_project_1/login/auth/kakao/callback" + "&response_type=code";
	
		return kakaoUrl;
	}
	
	
	public static JsonNode getAccessToken(String code) {
		 
        final String requestUrl = "https://kauth.kakao.com/oauth/token";
        final List<NameValuePair> param = new ArrayList<NameValuePair>();
 
        param.add(new BasicNameValuePair("grant_type", "authorization_code"));
        param.add(new BasicNameValuePair("client_id", "***********************"));
        param.add(new BasicNameValuePair("redirect_uri", "http://chparkland.com/park_project_1/login/auth/kakao/callback"));
        param.add(new BasicNameValuePair("code", code));
 
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost post = new HttpPost(requestUrl);
 
        JsonNode returnNode = null;
 
        try {
            post.setEntity(new UrlEncodedFormEntity(param));
            
            final HttpResponse response = client.execute(post);
 
            ObjectMapper mapper = new ObjectMapper();
            returnNode = mapper.readTree(response.getEntity().getContent());
 
        } catch (UnsupportedEncodingException e) { 
            e.printStackTrace();
 
        } catch (ClientProtocolException e) {
            e.printStackTrace();
 
        } catch (IOException e) {
            e.printStackTrace();
 
        } finally {
 
        }
        return returnNode;
    }
	
	
	public static JsonNode getKakaoUserProfile(JsonNode accessToken) {
		final String requestUrl = KAKAO_PROFILE_URL;
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(requestUrl);
		
		post.addHeader("Authorization", "Bearer " + accessToken);
		
		JsonNode returnNode = null;
		
		try {
			final HttpResponse response = client.execute(post);
			
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());
		}
		catch(ClientProtocolException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			
		}
		
		return returnNode;
	}
	
    
}
