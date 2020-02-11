package com.park.web.userpage.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.park.web.board.db.BoardVO;
import com.park.web.board.db.ReplyVO;
import com.park.web.common.SearchCriteria;
import com.park.web.user.db.UserVO;

@Repository
public class UserPageDAOImpl implements UserPageDAO{
	
	@Inject
	private SqlSession sqlsession;
	
	
	@Override
	public UserVO getUserInfo(String nickname) throws Exception {
		return sqlsession.selectOne("userpage.getUserInfo", nickname);
	}
	
	//User Board
	@Override
	public List<BoardVO> getBoardListPagingUser(SearchCriteria searchCriteria) throws Exception {
				
		return sqlsession.selectList("userpage.getBoardListPagingUser", searchCriteria);
	}
	
	@Override
	public int getBoardListPagingUserCnt(String nickname, SearchCriteria searchCriteria) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("nickname", nickname);
		paramMap.put("pagination", searchCriteria);
		
		return sqlsession.selectOne("userpage.getBoardListPagingUserCnt", paramMap);
	}
	
	
	//User Reply
	@Override
	public List<ReplyVO> getReplyListPagingUser(SearchCriteria searchCriteria) throws Exception {
				
		return sqlsession.selectList("userpage.getReplyListPagingUser", searchCriteria);
	}
	
	@Override
	public int getReplyListPagingUserCnt(String nickname, SearchCriteria searchCriteria) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("nickname", nickname);
		paramMap.put("pagination", searchCriteria);
		
		return sqlsession.selectOne("userpage.getReplyListPagingUserCnt", paramMap);
	}
}
