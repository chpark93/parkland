package com.park.web.memberShip.util;

import java.util.Random;

public class TempKey {
		
	
	//ȸ������ ����Ű ����
	public String createKey() throws Exception {
		String key="";
		Random random = new Random();
		
		for(int i=0; i < 10; i++) {
			key += random.nextInt(10);
		}		
		return key;
	}
	
	//�ӽ� ��й�ȣ ����
	public String tempPw() throws Exception {
		String pw = "";
		
		for(int i = 0; i < 12; i++) {
			pw += (char) ((Math.random()*26) + 97);
		}
		return pw;
		
	}
}
