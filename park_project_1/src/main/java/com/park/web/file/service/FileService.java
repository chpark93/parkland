package com.park.web.file.service;

import java.util.List;

public interface FileService {

	public List<String> getFiles(Integer bid) throws Exception;

	public void deleteFile(Integer bid, String fileName) throws Exception;

	public String getUserProfile(String id) throws Exception;


}
