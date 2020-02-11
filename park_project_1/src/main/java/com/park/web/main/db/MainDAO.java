package com.park.web.main.db;

import java.util.List;

import com.park.web.board.db.BoardVO;

public interface MainDAO {

	List<BoardVO> getRecommendBoard(BoardVO boardVO) throws Exception;

	List<BoardVO> getViewBestBoard(BoardVO boardVO) throws Exception;

	List<BoardVO> getCommonBoard(String bg_no) throws Exception;

}
