package com.park.web.board.service;

import java.util.List;

import com.park.web.board.db.BoardVO;
import com.park.web.common.SearchCriteria;
import com.park.web.menu.db.MenuVO;
import com.park.web.user.db.UserVO;

public interface BoardService {
	
	
	public void insertBoard(BoardVO board) throws Exception;
		
	public BoardVO getBoardContent(Integer bid) throws Exception;
	
	public void updateBoard(BoardVO board) throws Exception;
	
	public void deleteBoard(Integer bid) throws Exception;
	
	public List<BoardVO> searchBoardList(SearchCriteria searchCriteria) throws Exception;
	
	public int searchedBoardCnt(String bg_no, SearchCriteria searchCriteria) throws Exception;

	public BoardVO bgnoFrombid(Integer bid) throws Exception;
	
	public MenuVO boardGroupInfoFromBg_no(String bg_no) throws Exception;
	
	public int updateViewCnt(Integer bid) throws Exception;
	
	public void updateBoardRecommend(BoardVO boardVO) throws Exception;

	public void updateBoardNotice(BoardVO boardVO) throws Exception;
	
	public void updateBoardNoticeCancel(BoardVO boardVO) throws Exception;

	public void deleteBoardAdmin(Integer bid) throws Exception;

	
	//////////////////////////////////////////////////
	
	public List<BoardVO> getRecommendBoardList(SearchCriteria searchCriteria) throws Exception;
	
	public int getRecommendBoardCnt(Integer recommend_cnt, SearchCriteria searchCriteria) throws Exception;
	
	public List<BoardVO> getViewBestBoardList(SearchCriteria searchCriteria) throws Exception;
	
	public int getViewBestBoardCnt(Integer view_cnt, SearchCriteria searchCriteria) throws Exception;
	
	
	//////////////////////////////////////////////////
	
	public String getUserName(String id) throws Exception;

	public UserVO loginInfo(String id) throws Exception;


	
	









	





	
	
	
}
