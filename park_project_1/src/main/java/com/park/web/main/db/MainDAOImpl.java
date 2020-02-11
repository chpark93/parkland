package com.park.web.main.db;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.park.web.board.db.BoardVO;

@Repository
public class MainDAOImpl implements MainDAO {

	@Inject
	private SqlSession sqlsession;
	
	
	//추천 게시글
	@Override
	public List<BoardVO> getRecommendBoard(BoardVO boardVO) throws Exception {
		return sqlsession.selectList("main.getRecommendBoard", boardVO);
	}
	
	//인기 게시글
	@Override
	public List<BoardVO> getViewBestBoard(BoardVO boardVO) throws Exception {
		return sqlsession.selectList("main.getViewBestBoard", boardVO);
	}
	
	//게시글
	@Override
	public List<BoardVO> getCommonBoard(String bg_no) throws Exception {
		return sqlsession.selectList("main.getCommonBoard", bg_no);
	}
	
	
}
