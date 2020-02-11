package com.park.web.file.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.park.web.board.db.BoardVO;
import com.park.web.memberShip.db.MemberShipDetailVO;
import com.park.web.user.db.UserVO;

@Repository
public class FileDAOImpl implements FileDAO  {
	
	@Inject
	private SqlSession sqlsession;
	
	//���� ���
	@Override
	public List<String> getFiles(Integer bid) throws Exception {
		return sqlsession.selectList("file.getFiles", bid);
	}
	
	//���� ÷��
	@Override
	public void insertFile(BoardVO boardVO, String fileName) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boardVO", boardVO);
		paramMap.put("fileName", fileName);
		
		sqlsession.insert("file.insertFile", paramMap);
	}
	
	 
	//���� ����
	@Override
	public void deleteFile(String fileName) throws Exception {
		sqlsession.delete("file.deleteFile", fileName);
	}
	@Override
	public void deleteFiles(Integer bid) throws Exception {
		sqlsession.delete("file.deleteFiles", bid);
	}
	
	@Override
	public void deleteUserProfile(String id) throws Exception {
		sqlsession.delete("file.deleteUserProfile", id);
	}
	
	
	//���� ����
	@Override
	public void modifyFile(BoardVO boardVO, String fileName) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boardVO", boardVO);
		paramMap.put("fileName", fileName);
		
		sqlsession.insert("file.modifyFile", paramMap);
	}
	
	//���� ����
	@Override
	public void updateFileCnt(Integer bid) throws Exception {
		sqlsession.update("file.updateFileCnt", bid);
	}
	
	

	//���� ������ �̹���
	@Override
	public void insertUserProfile(UserVO userVO, String fileName) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userVO", userVO);
		paramMap.put("fileName", fileName);
		
		sqlsession.insert("file.insertUserProfile", paramMap);
	}
	
	@Override
	public String getUserProfile(String id) throws Exception {
		
		return sqlsession.selectOne("file.getUserProfile", id);
	}
	
	
	
}
