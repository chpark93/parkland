package com.park.web.user.util;

import java.util.Locale;

import org.springframework.context.support.MessageSourceAccessor;

public class MessageUtils {

	private static MessageSourceAccessor msa = null;
	
	public void setMessageSourceAccessor(MessageSourceAccessor msa) {
		MessageUtils.msa = msa;		
	}
	
	public static String getMessage(String code) {
		return msa.getMessage(code, Locale.getDefault());
	}
	
	public static String getMessage(String code, Object[] obj) {
		return msa.getMessage(code, obj ,Locale.getDefault());
	}
}
