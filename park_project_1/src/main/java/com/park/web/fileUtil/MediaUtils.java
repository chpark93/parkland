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
	
	//파일 타입
	static MediaType getMediaType(String fileName) {
		String formatName = getFormatName(fileName);
		
		return (MediaType) mediaTypeMap.get(formatName);
	}
	
	//파일 확장자 추출
	static String getFormatName(String fileName) {
		
		return fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
	}
	
	
}
