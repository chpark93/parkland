package com.park.web.userpage.db;

import java.util.List;

import com.park.web.board.db.BoardVO;
import com.park.web.board.db.ReplyVO;
import com.park.web.common.SearchCriteria;
import com.park.web.user.db.UserVO;

public interface UserPageDAO {

	List<BoardVO> getBoardListPagingUser(SearchCriteria searchCriteria) throws Exception;

	int getBoardListPagingUserCnt(String nickname, SearchCriteria searchCriteria) throws Exception;

	UserVO getUserInfo(String nickname) throws Exception;

	
	List<ReplyVO> getReplyListPagingUser(SearchCriteria searchCriteria) throws Exception;

	int getReplyListPagingUserCnt(String nickname, SearchCriteria searchCriteria) throws Exception;

}
