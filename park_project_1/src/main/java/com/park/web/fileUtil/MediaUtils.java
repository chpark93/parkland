package com.park.web.fileUtil;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

public class MediaUtils {
	
	private static Map<String, Object> mediaTypeMap;
	
	static {
		mediaTypeMap = new HashMap<String, Object>();
		mediaTypeMap.put("JPG", MediaType.IMAGE_JPEG);
		mediaTypeMap.put("GIF", MediaType.IMAGE_GIF);
		mediaTypeMap.put("PNG", MediaType.IMAGE_PNG);
	}
	
	//���� Ÿ��
	static MediaType getMediaType(String fileName) {
		String formatName = getFormatName(fileName);
		
		return (MediaType) mediaTypeMap.get(formatName);
	}
	
	//���� Ȯ���� ����
	static String getFormatName(String fileName) {
		
		return fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
	}
	
	
}
