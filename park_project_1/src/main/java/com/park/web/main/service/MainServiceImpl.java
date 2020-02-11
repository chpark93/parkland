package com.park.web.main.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.park.web.board.db.BoardVO;
import com.park.web.main.db.MainDAO;

@Service
public class MainServiceImpl implements MainService{

	@Inject
	private MainDAO mainmanager;
	
	//추천 게시글
	@Override
	public List<BoardVO> getRecommendBoard(BoardVO boardVO) throws Exception {
		return mainmanager.getRecommendBoard(boardVO);
	}
	
	//인기 게시글
	@Override
	public List<BoardVO> getViewBestBoard(BoardVO boardVO) throws Exception {
		return mainmanager.getViewBestBoard(boardVO);
	}
	
	//게시글
	@Override
	public List<BoardVO> getCommonBoard(String bg_no) throws Exception {
		return mainmanager.getCommonBoard(bg_no);
	}
	
	
	
}
