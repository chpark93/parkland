package com.park.web.mypage.service;

import java.util.List;

import com.park.web.board.db.BoardVO;
import com.park.web.board.db.ReplyVO;
import com.park.web.common.SearchCriteria;
import com.park.web.mypage.db.AlarmVO;

public interface MyPageService {

	List<BoardVO> getBoardListPagingFromId(SearchCriteria searchCriteria) throws Exception;

	int getBoardListPagingFromIdCnt(String id, SearchCriteria searchCriteria) throws Exception;

	List<ReplyVO> getReplyListPagingFromId(SearchCriteria searchCriteria) throws Exception;

	int getReplyListPagingFromIdCnt(String id, SearchCriteria searchCriteria) throws Exception;

	
	void deleteMyPageBoard(BoardVO boardVO) throws Exception;

	void deleteMyPageReply(ReplyVO replyVO) throws Exception;

	
	
	List<AlarmVO> getAlarmListPagingFromId(SearchCriteria searchCriteria) throws Exception;

	int getAlarmListPagingFromIdCnt(String id, SearchCriteria searchCriteria) throws Exception;

	void deleteMyPageAlarm(AlarmVO alarmVO) throws Exception;

	void deleteMyPageAlarmExpired() throws Exception;
	


}
