package com.park.web.board.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.web.board.db.BoardDAO;
import com.park.web.board.db.ReplyDAO;
import com.park.web.board.db.ReplyVO;
import com.park.web.common.Criteria;
import com.park.web.common.SearchCriteria;
import com.park.web.mypage.db.AlarmVO;
import com.park.web.mypage.db.MyPageDAO;

@Service
public class ReplyServiceimpl implements ReplyService{

	@Inject
	private BoardDAO manager;
	
	@Inject
	private ReplyDAO remanager; 
	
	@Inject
	private MyPageDAO mpmanager;

	//¥Ò±€ ∆‰¿Ã¬°
	@Override
	public List<ReplyVO> getReplyListPaging(Integer bid, Criteria criteria) throws Exception {
		return remanager.getReplyListPaging(bid, criteria);
	}
	
	
	@Override
	public int getReplyListPagingCnt(Integer bid, SearchCriteria searchCriteria) throws Exception {
		return remanager.getReplyListPagingCnt(bid, searchCriteria);
	}
	
	
	@Transactional
	@Override
	public void insertReply(ReplyVO replyVO) throws Exception {
		remanager.insertReply(replyVO);
		manager.updateReplyViewCnt(replyVO.getBid(), 1);
		
		
		//Alarm  
		int bid = mpmanager.getBoardAlarm(replyVO).getBid();
		String target_id = mpmanager.getBoardRegIdFromBid(bid);
		
		if(!replyVO.getReg_id().equals(target_id)) {			
			String msg = replyVO.getReg_nickname() + "¥‘¿Ã " + "<a href='../board/getBoardContent?bid=" + bid + " '>" + bid + "</a>" + "π¯ ±€ø° ¥Ò±€¿ª ≥≤∞ÂΩ¿¥œ¥Ÿ.";
			
			AlarmVO alarmVO = new AlarmVO();
			alarmVO.setNickname(replyVO.getReg_nickname());
			alarmVO.setTarget_id(target_id);
			alarmVO.setMsg(msg);
			
			mpmanager.insertAlarm(alarmVO);
			mpmanager.getBoardAlarm(replyVO);
		}
	}
	
	@Override
	public void updateReply(ReplyVO replyVO) throws Exception {
		remanager.updateReply(replyVO);
	}
	
	@Transactional 
	@Override
	public void deleteReply(ReplyVO replyVO) throws Exception {
		int bid = remanager.getBoardNo(replyVO.getRid());
		
		remanager.deleteReply(replyVO);
		manager.updateReplyViewCnt(bid, -1);
	}
	
	
	
}
