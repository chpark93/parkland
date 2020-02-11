package com.park.web.board.db;

import java.util.List;

import com.park.web.common.SearchCriteria;
import com.park.web.menu.db.MenuVO;

public interface BoardDAO {
	
	
		
	public BoardVO getBoardContent(Integer bid) throws Exception;
	
	public int insertBoard(BoardVO board) throws Exception;
	
	public int updateBoard(BoardVO board) throws Exception;
	
	public int deleteBoard(Integer bid) throws Exception;
	
	public int updateViewCnt(Integer bid) throws Exception;
	
	public void updateReplyViewCnt(Integer bid, int amount) throws Exception;

	public void updateBoardRecommend(BoardVO boardVO) throws Exception;


	//게시글 리스트
	public List<BoardVO> searchBoardList(SearchCriteria searchCriteria) throws Exception;
	
	public int searchedBoardCnt(String bg_no, SearchCriteria searchCriteria) throws Exception;

	public BoardVO bgnoFrombid(Integer bid) throws Exception;

	public MenuVO boardGroupInfoFromBg_no(String bg_no) throws Exception;
	
	public int deleteBoardFromBgno(String bg_no) throws Exception;

	public void deleteBoardAdmin(Integer bid) throws Exception;

	
	
	
	///////////////////////////////////////////////////////
	
	//추천 게시글
	public List<BoardVO> getRecommendBoardList(SearchCriteria searchCriteria) throws Exception;

	public int getRecommendBoardCnt(Integer recommend_cnt, SearchCriteria searchCriteria) throws Exception;

	//인기 게시글
	public List<BoardVO> getViewBestBoardList(SearchCriteria searchCriteria) throws Exception;

	public int getViewBestBoardCnt(Integer view_cnt, SearchCriteria searchCriteria) throws Exception;

		






}
