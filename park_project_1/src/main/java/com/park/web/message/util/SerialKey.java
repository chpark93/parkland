package com.park.web.message.util;

import java.util.Random;

public class SerialKey {
		
	
	//message serial »ý¼º
	public String serialKey() throws Exception {
		String key="";
		Random random = new Random();
		
		for(int i=0; i < 10; i++) {
			key += random.nextInt(10);
		}		
		
		return key;
	}
}
