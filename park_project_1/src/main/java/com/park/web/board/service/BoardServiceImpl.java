package com.park.web.board.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.mapper.UserMapper;
import com.park.web.board.db.BoardDAO;
import com.park.web.board.db.BoardVO;
import com.park.web.common.SearchCriteria;
import com.park.web.file.db.FileDAO;
import com.park.web.menu.db.MenuVO;
import com.park.web.mypage.db.MyPageDAO;
import com.park.web.user.db.UserVO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO manager;
	
	@Inject
	private UserMapper userMapper;
	
	@Inject
	private FileDAO filemanager;
	
	
	//�Խñ� �μ�Ʈ
	@Transactional
	@Override
	public void insertBoard(BoardVO boardVO) throws Exception {
		manager.insertBoard(boardVO);
		
		//÷�� ����
		String[] files = boardVO.getFiles();
		if(files == null) {
			return;
		}
		
		for(String fileName : files) {
			filemanager.insertFile(boardVO, fileName);
			filemanager.updateFileCnt(boardVO.getBid());
		}
		
	}
	
	//�Խñ� ��
	@Transactional
	@Override
	public BoardVO getBoardContent(Integer bid) throws Exception {
		manager.bgnoFrombid(bid);
		return manager.getBoardContent(bid);
	}
	
	//�Խñ� ��ȸ��
	@Override
	public int updateViewCnt(Integer bid) throws Exception {
		return manager.updateViewCnt(bid);
	}
	
	//�Խñ� ��õ
	@Override
	public void updateBoardRecommend(BoardVO boardVO) throws Exception {
		manager.updateBoardRecommend(boardVO);
	}
	
	//�Խñ� ����
	@Override
	public void updateBoardNotice(BoardVO boardVO) throws Exception {
		manager.updateBoardNotice(boardVO);
	}
	
	//���� ���
	@Override
	public void updateBoardNoticeCancel(BoardVO boardVO) throws Exception {
		manager.updateBoardNoticeCancel(boardVO);
	}
	

	//�Խñ� ����
	//���� ÷�� ���� ��ü ����
	//�� ÷�� ���� ���̺� ����
	@Transactional
	@Override
	public void updateBoard(BoardVO boardVO) throws Exception {
		Integer bid = boardVO.getBid();
		String[] files = boardVO.getFiles();
		
		manager.updateBoard(boardVO);
		filemanager.deleteFiles(bid);
		
		if(files == null) {
			return;
		}
		for(String fileName : files) {
			filemanager.modifyFile(boardVO, fileName);
			filemanager.updateFileCnt(boardVO.getBid());
		}
	}
	
	//����
	@Transactional
	@Override
	public void deleteBoard(Integer bid) throws Exception {
		filemanager.deleteFiles(bid);
		manager.deleteBoard(bid);
	}

	
	//�Խ��� ����Ʈ 
	@Override
	public List<BoardVO> searchBoardList(SearchCriteria searchCriteria) throws Exception {
		return manager.searchBoardList(searchCriteria);
	}
	
	@Override
	public int searchedBoardCnt(String bg_no, SearchCriteria searchCriteria) throws Exception {
		return manager.searchedBoardCnt(bg_no, searchCriteria);
	}
	
	@Override
	public BoardVO bgnoFrombid(Integer bid) throws Exception {
		return manager.bgnoFrombid(bid);
	}
	
	@Override
	public MenuVO boardGroupInfoFromBg_no(String bg_no) throws Exception {
		return manager.boardGroupInfoFromBg_no(bg_no);
	}
	
	//�Խñ� ����(������ ����)
	@Transactional
	@Override
	public void deleteBoardAdmin(Integer bid) throws Exception {
		filemanager.deleteFiles(bid);
		manager.deleteBoardAdmin(bid);
	}
	
	
	
	///////////////////////////////////////////////////////
	
	//��õ �Խñ�
	@Override
	public List<BoardVO> getRecommendBoardList(SearchCriteria searchCriteria) throws Exception {
		return manager.getRecommendBoardList(searchCriteria);
	}
	
	@Override
	public int getRecommendBoardCnt(Integer recommend_cnt, SearchCriteria searchCriteria) throws Exception {
		return manager.getRecommendBoardCnt(recommend_cnt, searchCriteria);
	}
	
	//�α� �Խñ�
	@Override
	public List<BoardVO> getViewBestBoardList(SearchCriteria searchCriteria) throws Exception {
		return manager.getViewBestBoardList(searchCriteria);
	}
	
	@Override
	public int getViewBestBoardCnt(Integer view_cnt, SearchCriteria searchCriteria) throws Exception {
		return manager.getViewBestBoardCnt(view_cnt, searchCriteria);
	}
	
	
	
	
	///////////////////////////////////////////////////////
	@Override
	public String getUserName(String id) throws Exception {
		return userMapper.getName(id);
	}
	
	@Override
	public UserVO loginInfo(String id) throws Exception {
		return userMapper.loginInfo(id);
	}

	
	
	
	
}
