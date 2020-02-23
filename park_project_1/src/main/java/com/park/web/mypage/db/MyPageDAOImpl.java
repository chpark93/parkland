package com.park.web.mypage.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.park.web.board.db.BoardVO;
import com.park.web.board.db.ReplyVO;
import com.park.web.common.SearchCriteria;
import com.park.web.message.db.MessageVO;

@Repository
public class MyPageDAOImpl implements MyPageDAO{
	
	@Inject
	private SqlSession sqlsession;
	
	
	//전체 게시글 List Paging From Id
	@Override
	public List<BoardVO> getBoardListPagingFromId(SearchCriteria searchCriteria) throws Exception {
				
		return sqlsession.selectList("mypage.getBoardListPagingFromId", searchCriteria);
	}
	
	@Override
	public int getBoardListPagingFromIdCnt(String id, SearchCriteria searchCriteria) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("pagination", searchCriteria);
		
		return sqlsession.selectOne("mypage.getBoardListPagingFromIdCnt", paramMap);
	}
	
	//board delete
	@Override
	public void deleteMyPageBoard(BoardVO boardVO) throws Exception {
		sqlsession.delete("mypage.deleteMyPageBoard", boardVO);
	}
	
	//전체 댓글 List Paging From Id
	@Override
	public List<ReplyVO> getReplyListPagingFromId(SearchCriteria searchCriteria) throws Exception {
		
		return sqlsession.selectList("mypage.getReplyListPagingFromId", searchCriteria);
	}
	
	@Override
	public int getReplyListPagingFromIdCnt(String id, SearchCriteria searchCriteria) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("pagination", searchCriteria);
		
		return sqlsession.selectOne("mypage.getReplyListPagingFromIdCnt", paramMap);
	}

	//reply delete
	@Override
	public void deleteMyPageReply(ReplyVO replyVO) throws Exception {
		
		sqlsession.delete("mypage.deleteMyPageReply", replyVO);
	}
	
	
	
	//Alarm List
	@Override
	public List<AlarmVO> getAlarmListPagingFromId(SearchCriteria searchCriteria) throws Exception {
				
		return sqlsession.selectList("mypage.getAlarmListPagingFromId", searchCriteria);
	}
	
	@Override
	public int getAlarmListPagingFromIdCnt(String id, SearchCriteria searchCriteria) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("pagination", searchCriteria);
		
		return sqlsession.selectOne("mypage.getAlarmListPagingFromIdCnt", paramMap);
	}
	
	@Override
	public int insertAlarm(AlarmVO alarmVO) throws Exception {
		
		return sqlsession.insert("mypage.insertAlarm", alarmVO);
	}
	
	@Override
	public String getBoardRegIdFromBid(Integer bid) throws Exception {
		return sqlsession.selectOne("mypage.getBoardRegIdFromBid", bid);
	}
	
	@Override
	public ReplyVO getBoardAlarm(ReplyVO replyVO) throws Exception {
		return sqlsession.selectOne("mypage.getBoardAlarm", replyVO);
	}
	
	@Override
	public MessageVO getMessageAlarm(MessageVO messageVO) throws Exception {
		return sqlsession.selectOne("mypage.getMessageAlarm", messageVO);
	}
	
	@Override
	public String getAlarmAllCnt(String id) throws Exception {
		return sqlsession.selectOne("mypage.getAlarmAllCnt", id);
	}

	
	//alarm delete
	@Override
	public void deleteMyPageAlarm(AlarmVO alarmVO) throws Exception {
		sqlsession.delete("mypage.deleteMyPageAlarm", alarmVO);
	}
	
	@Override
	public void deleteMyPageAlarmExpired() throws Exception {
		sqlsession.delete("mypage.deleteMyPageAlarmExpired");
	}
}
