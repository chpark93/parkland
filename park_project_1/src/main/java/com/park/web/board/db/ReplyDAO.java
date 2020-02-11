package com.park.web.board.db;

import java.util.List;

import com.park.web.common.Criteria;
import com.park.web.common.SearchCriteria;

public interface ReplyDAO {


	
	List<ReplyVO> getReplyListPaging(Integer bid, Criteria criteria) throws Exception;
	
	int getReplyListPagingCnt(Integer bid, SearchCriteria searchCriteria) throws Exception;
	
	int getBoardNo(int rid) throws Exception;

	void insertReply(ReplyVO replyVO) throws Exception;

	void updateReply(ReplyVO replyVO) throws Exception;

	void deleteReply(ReplyVO replyVO) throws Exception;

	int getReplyDepthFromRid(Integer rid) throws Exception;

	int getReplyParentFromRid(Integer rid) throws Exception;




}
