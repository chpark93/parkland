package com.park.web.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.park.web.user.db.UserVO;

public class SnsLogin {
	
	private OAuth20Service oauthservice;
	private SnsValue snsValue;

	
	public SnsLogin(SnsValue snsValue) {
		this.oauthservice = new ServiceBuilder(snsValue.getClient_id())
							.apiSecret(snsValue.getClient_secret())
							.callback(snsValue.getRedirectUrl())
							.scope("profile")
							.build(snsValue.getApiInstance());
							
		this.snsValue = snsValue;
	}
	
	
	public String getNaverAuth() {
		return this.oauthservice.getAuthorizationUrl();
	}
	
	
	public String getKakaoAuth() {
		return KakaoApi.getAuthorizationUrl();
    }
	
	
	public UserVO getUserProfile(String code) throws Exception {
		
		OAuth2AccessToken accessToken = oauthservice.getAccessToken(code);
		
		OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, this.snsValue.getProfile());
		oauthservice.signRequest(accessToken, oauthRequest);
		
		Response response = oauthservice.execute(oauthRequest);
		
		return parseJson(response.getBody());
	
	}
	
	private UserVO parseJson(String body) throws Exception {

		UserVO userVO = new UserVO();
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode node = objectMapper.readTree(body);
		
		if(this.snsValue.isNaver()) { 
			//³×ÀÌ¹ö
			JsonNode responseNode = node.path("response");
			
			userVO.setId(responseNode.path("id").asText());
			userVO.setName(responseNode.path("name").asText());
			userVO.setEmail(responseNode.path("email").asText());
			
		}
				
		return userVO;
	}
	
}
