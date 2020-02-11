package com.park.web.mypage.db;

import java.util.List;

import com.park.web.board.db.BoardVO;
import com.park.web.board.db.ReplyVO;
import com.park.web.common.SearchCriteria;
import com.park.web.message.db.MessageVO;

public interface MyPageDAO {

	List<BoardVO> getBoardListPagingFromId(SearchCriteria searchCriteria) throws Exception;

	int getBoardListPagingFromIdCnt(String id, SearchCriteria searchCriteria) throws Exception;

	List<ReplyVO> getReplyListPagingFromId(SearchCriteria searchCriteria) throws Exception;

	int getReplyListPagingFromIdCnt(String id, SearchCriteria searchCriteria) throws Exception;

	
	void deleteMyPageBoard(BoardVO boardVO) throws Exception;

	void deleteMyPageReply(ReplyVO replyVO) throws Exception;

	
	List<AlarmVO> getAlarmListPagingFromId(SearchCriteria searchCriteria) throws Exception;
	
	int getAlarmListPagingFromIdCnt(String id, SearchCriteria searchCriteria) throws Exception;

	int insertAlarm(AlarmVO alarmVO) throws Exception;

	ReplyVO getBoardAlarm(ReplyVO replyVO) throws Exception;

	MessageVO getMessageAlarm(MessageVO messageVO) throws Exception;

	String getBoardRegIdFromBid(Integer bid) throws Exception;

	void deleteMyPageAlarm(AlarmVO alarmVO) throws Exception;

	void deleteMyPageAlarmExpired() throws Exception;

	void getAlarmAllCnt(String id) throws Exception;

	





	
	

}
