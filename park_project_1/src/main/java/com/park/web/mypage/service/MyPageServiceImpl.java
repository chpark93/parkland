package com.park.web.mypage.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.web.board.db.BoardDAO;
import com.park.web.board.db.BoardVO;
import com.park.web.board.db.ReplyDAO;
import com.park.web.board.db.ReplyVO;
import com.park.web.common.SearchCriteria;
import com.park.web.mypage.db.AlarmVO;
import com.park.web.mypage.db.MyPageDAO;

@Service
public class MyPageServiceImpl implements MyPageService{

	@Inject
	private MyPageDAO mpmanager;
	
	@Inject
	private BoardDAO manager;
	
	@Inject
	private ReplyDAO remanager;
	
	@Override
	public List<BoardVO> getBoardListPagingFromId(SearchCriteria searchCriteria) throws Exception {
		return mpmanager.getBoardListPagingFromId(searchCriteria);
	}
	
	@Override
	public int getBoardListPagingFromIdCnt(String id, SearchCriteria searchCriteria) throws Exception {
		return mpmanager.getBoardListPagingFromIdCnt(id, searchCriteria);
	}
	
	@Override
	public void deleteMyPageBoard(BoardVO boardVO) throws Exception {
		mpmanager.deleteMyPageBoard(boardVO);
	}
	
	
	@Override  
	public List<ReplyVO> getReplyListPagingFromId(SearchCriteria searchCriteria) throws Exception {
		return mpmanager.getReplyListPagingFromId(searchCriteria);
	}
	
	@Override
	public int getReplyListPagingFromIdCnt(String id, SearchCriteria searchCriteria) throws Exception {
		return mpmanager.getReplyListPagingFromIdCnt(id, searchCriteria);
	}
	
	@Transactional
	@Override
	public void deleteMyPageReply(ReplyVO replyVO) throws Exception {
		int bid = remanager.getBoardNo(replyVO.getRid());
		replyVO.setRdepth(remanager.getReplyDepthFromRid(replyVO.getRid()));
		replyVO.setRparent_id(remanager.getReplyParentFromRid(replyVO.getRid()));;
		
		remanager.deleteReply(replyVO);
		manager.updateReplyViewCnt(bid, -1);
		
	}
	
	
	//Alarm
	@Transactional
	@Override
	public List<AlarmVO> getAlarmListPagingFromId(SearchCriteria searchCriteria) throws Exception {
		return mpmanager.getAlarmListPagingFromId(searchCriteria);
	}
	
	@Override
	public int getAlarmListPagingFromIdCnt(String id, SearchCriteria searchCriteria) throws Exception {
		return mpmanager.getAlarmListPagingFromIdCnt(id, searchCriteria);
	}
	
	@Override
	public void deleteMyPageAlarm(AlarmVO alarmVO) throws Exception {
		mpmanager.deleteMyPageAlarm(alarmVO);
	}
	
	@Override
	public void deleteMyPageAlarmExpired() throws Exception {
		
		mpmanager.deleteMyPageAlarmExpired();
	}
}
