package com.park.web.memberShip.util;

import java.util.Random;

public class TempKey {
		
	
	//회원가입 인증키 생성
	public String createKey() throws Exception {
		String key="";
		Random random = new Random();
		
		for(int i=0; i < 10; i++) {
			key += random.nextInt(10);
		}		
		return key;
	}
	
	//임시 비밀번호 생성
	public String tempPw() throws Exception {
		String pw = "";
		
		for(int i = 0; i < 12; i++) {
			pw += (char) ((Math.random()*26) + 97);
		}
		return pw;
		
	}
}
