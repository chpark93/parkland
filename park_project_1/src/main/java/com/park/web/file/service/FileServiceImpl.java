package com.park.web.file.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.web.file.db.FileDAO;

@Service
public class FileServiceImpl implements FileService {
	
	@Inject
	private FileDAO filemanager;

	@Override
	public List<String> getFiles(Integer bid) throws Exception {
		return filemanager.getFiles(bid);
	}
	
	@Override
	public String getUserProfile(String id) throws Exception {
		return filemanager.getUserProfile(id);
	}
	
	@Transactional
	@Override
	public void deleteFile(Integer bid, String fileName) throws Exception {		
		filemanager.deleteFile(fileName);
		filemanager.updateFileCnt(bid);
	}
	
}
