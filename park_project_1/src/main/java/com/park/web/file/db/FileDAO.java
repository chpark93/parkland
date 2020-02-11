package com.park.web.file.db;

import java.util.List;

import com.park.web.board.db.BoardVO;
import com.park.web.memberShip.db.MemberShipDetailVO;
import com.park.web.user.db.UserVO;

public interface FileDAO {
	
	public List<String> getFiles(Integer bid) throws Exception;
	
	public void insertFile(BoardVO boardVO, String fullName) throws Exception;

	public void deleteFile(String fileName) throws Exception;
	
	public void deleteFiles(Integer bid) throws Exception;

	public void deleteUserProfile(String id) throws Exception;

	public void modifyFile(BoardVO boardVO, String fileName) throws Exception;

	public void updateFileCnt(Integer bid) throws Exception;

	public void insertUserProfile(UserVO userVO, String fileName) throws Exception;

	public String getUserProfile(String id) throws Exception;



}