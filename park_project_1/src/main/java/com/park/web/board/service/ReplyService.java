package com.park.web.board.service;

import java.util.List;

import com.park.web.board.db.ReplyVO;
import com.park.web.common.Criteria;
import com.park.web.common.SearchCriteria;

public interface ReplyService {

	
	List<ReplyVO> getReplyListPaging(Integer bid, Criteria criteria) throws Exception;

	int getReplyListPagingCnt(Integer bid, SearchCriteria searchCriteria) throws Exception;

	void insertReply(ReplyVO replyVO) throws Exception;

	void updateReply(ReplyVO replyVO) throws Exception;

	void deleteReply(ReplyVO replyVO) throws Exception;





}
