package com.park.web.board.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.park.web.common.SearchCriteria;
import com.park.web.menu.db.MenuVO;

@Repository
public class BoardDAOImpl implements BoardDAO {
 
	@Inject
	private SqlSession sqlsession;

	
	
	@Override
	public BoardVO getBoardContent(Integer bid) throws Exception {
		return sqlsession.selectOne("board.getBoardContent", bid);
	}
	
	@Override
	public int insertBoard(BoardVO board) throws Exception {
		return sqlsession.insert("board.insertBoard", board);
	}
	
	@Override
	public int updateBoard(BoardVO board) throws Exception {
		return sqlsession.update("board.updateBoard", board);
	}
	
	@Override
	public int deleteBoard(Integer bid) throws Exception {
		return sqlsession.delete("board.deleteBoard", bid);
	}

	@Override
	public int updateViewCnt(Integer bid) throws Exception {
		return sqlsession.update("board.updateViewCnt", bid);
	}
	
	@Override
	public void updateBoardRecommend(BoardVO boardVO) throws Exception {
		sqlsession.update("board.updateBoardRecommend", boardVO);
	}
	
	//댓글 수
	@Override
	public void updateReplyViewCnt(Integer bid, int amount) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bid", bid);
		paramMap.put("amount", amount);
		
		sqlsession.update("board.updateReplyViewCnt", paramMap);
	}
	
	//공지글
	@Override
	public void updateBoardNotice(BoardVO boardVO) throws Exception {
		sqlsession.update("board.updateBoardNotice", boardVO);
	}
	
	//공지 내리기
	@Override
	public void updateBoardNoticeCancel(BoardVO boardVO) throws Exception {
		sqlsession.update("board.updateBoardNoticeCancel", boardVO);
	}
	
	
	//검색
	@Override
	public List<BoardVO> searchBoardList(SearchCriteria searchCriteria) throws Exception {
		return sqlsession.selectList("board.searchBoardList", searchCriteria);
	}
	
	@Override
	public int searchedBoardCnt(String bg_no, SearchCriteria searchCriteria) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bg_no", bg_no);
		paramMap.put("searchCriteria", searchCriteria);
		
		return sqlsession.selectOne("board.searchedBoardCnt", paramMap);
	}
	
	
	@Override
	public BoardVO bgnoFrombid(Integer bid) throws Exception {
		return sqlsession.selectOne("board.bgnoFrombid", bid);
	}
	
	@Override
	public MenuVO boardGroupInfoFromBg_no(String bg_no) throws Exception {
		return sqlsession.selectOne("board.boardGroupInfoFromBg_no", bg_no);
	}

	@Override
	public int deleteBoardFromBgno(String bg_no) throws Exception {
		return sqlsession.delete("board.deleteBoardFromBgno", bg_no);
	}

	@Override
	public void deleteBoardAdmin(Integer bid) throws Exception {
		sqlsession.delete("board.deleteBoardAdmin", bid);
	}
	
	
	
	
	////////////////////////////////////////////////////////
	
	//추천글 게시판
	@Override
	public List<BoardVO> getRecommendBoardList(SearchCriteria searchCriteria) throws Exception {
		
		return sqlsession.selectList("board.getRecommendBoardList", searchCriteria);
	}
	
	@Override
	public int getRecommendBoardCnt(Integer recommend_cnt, SearchCriteria searchCriteria) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("recommend_cnt", recommend_cnt);
		paramMap.put("searchCriteria", searchCriteria);
		
		return sqlsession.selectOne("board.getRecommendBoardCnt", paramMap);
	}
	
	//인기글 게시판
	@Override
	public List<BoardVO> getViewBestBoardList(SearchCriteria searchCriteria) throws Exception {
		
		return sqlsession.selectList("board.getViewBestBoardList", searchCriteria);
	}
	
	@Override
	public int getViewBestBoardCnt(Integer view_cnt, SearchCriteria searchCriteria) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("view_cnt", view_cnt);
		paramMap.put("searchCriteria", searchCriteria);
		
		return sqlsession.selectOne("board.getViewBestBoardCnt", paramMap);
	}
	

}
