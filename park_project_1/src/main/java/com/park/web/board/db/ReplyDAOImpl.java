package com.park.web.board.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.park.web.common.Criteria;
import com.park.web.common.SearchCriteria;

@Repository
public class ReplyDAOImpl implements ReplyDAO {
	
	@Inject
	private SqlSession sqlsession;
	
	
	@Override
	public int getBoardNo(int rid) throws Exception {
		return sqlsession.selectOne("reply.getBoardNo", rid);
	}
	
	@Override
	public int getReplyDepthFromRid(Integer rid) throws Exception {
		return sqlsession.selectOne("reply.getReplyDepthFromRid", rid);
	}
	
	@Override
	public int getReplyParentFromRid(Integer rid) throws Exception {
		return sqlsession.selectOne("reply.getReplyParentFromRid", rid);
	}
	
	
	//댓글 페이징
	@Override
	public List<ReplyVO> getReplyListPaging(Integer bid, Criteria criteria) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bid", bid);		
		paramMap.put("pagination", criteria);		
		
		return sqlsession.selectList("reply.getReplyListPaging", paramMap);
	}
	
	@Override 
	public int getReplyListPagingCnt(Integer bid, SearchCriteria searchCriteria) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bid", bid);
		paramMap.put("searchCriteria", searchCriteria);
		
		return sqlsession.selectOne("reply.getReplyListPagingCnt", paramMap);
	}
	
	//댓글 인서트
	@Transactional
	@Override
	public void insertReply(ReplyVO replyVO) throws Exception {
		
		//대댓글
		if(replyVO.getRparent_id() != null) {
			ReplyVO replyInfo = sqlsession.selectOne("reply.getReplyParent", replyVO.getRparent_id());
			
			replyVO.setRgroup_id(replyInfo.getRgroup_id());
			replyVO.setRparent_id(replyInfo.getRparent_id());
			replyVO.setRdepth(replyInfo.getRdepth());
			replyVO.setRorder(replyInfo.getRorder() + 1);
		
			sqlsession.selectOne("reply.updateReplyOrder", replyInfo);
		}
		else {
			Integer rorder = sqlsession.selectOne("reply.getReplyMaxOrder", replyVO.getBid());
			Integer rgroup = sqlsession.selectOne("reply.getReplyGroupId", replyVO.getBid());
			
			replyVO.setRgroup_id(rgroup);
			replyVO.setRorder(rorder);
		}
		
		sqlsession.insert("reply.insertReply", replyVO);
	}
	
	//댓글 수정
	@Override
	public void updateReply(ReplyVO replyVO) throws Exception {
		sqlsession.update("reply.updateReply", replyVO);
	}
	
	//댓글 삭제
	@Transactional
	@Override
	public void deleteReply(ReplyVO replyVO) throws Exception {
		Integer cnt = sqlsession.selectOne("reply.getReplyChild", replyVO);
		Integer parentCnt = sqlsession.selectOne("reply.getReplyParentCnt", replyVO);
		ReplyVO replyDepth = sqlsession.selectOne("reply.getReplyDepth", replyVO);
		
		//자식 댓글 존재 시
		if(cnt > 0) {
			//자식 댓글이 있는 부모(depth = 0) 삭제
			if(replyDepth.getRdepth() == 0) {
				sqlsession.update("reply.deleteReplyHaveChild", replyVO);
			}
			else {
				//삭제된 부모(depth = 0) 만 남는경우 댓글 삭제
				if(parentCnt == 2) {
					sqlsession.delete("reply.deleteReplyParent", replyVO);		
				}
				//자식 댓글 삭제
				sqlsession.update("reply.updateReplyOrderDelete", replyVO);
				sqlsession.delete("reply.deleteReply", replyVO);
				
			}
			
		}
		else {
			//댓글 삭제
			sqlsession.update("reply.updateReplyOrderDelete", replyVO);
			sqlsession.delete("reply.deleteReply", replyVO);
		}
	}
	
	
}
