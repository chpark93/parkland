package com.park.web.userpage.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.park.web.board.db.BoardVO;
import com.park.web.board.db.ReplyVO;
import com.park.web.common.SearchCriteria;
import com.park.web.user.db.UserVO;
import com.park.web.userpage.db.UserPageDAO;

@Service
public class UserPageServiceImpl implements UserPageService{
	
	@Inject
	private UserPageDAO userpagemanager;
	
	
	@Override
	public UserVO getUserInfo(String nickname) throws Exception {
		return userpagemanager.getUserInfo(nickname);
	}
	
	//User Board
	@Override
	public List<BoardVO> getBoardListPagingUser(SearchCriteria searchCriteria) throws Exception {
		return userpagemanager.getBoardListPagingUser(searchCriteria);
	}
	
	@Override
	public int getBoardListPagingUserCnt(String nickname, SearchCriteria searchCriteria) throws Exception {
		return userpagemanager.getBoardListPagingUserCnt(nickname, searchCriteria);
	}
	
	
	//User Reply
	@Override
	public List<ReplyVO> getReplyListPagingUser(SearchCriteria searchCriteria) throws Exception {
		return userpagemanager.getReplyListPagingUser(searchCriteria);
	}
	
	@Override
	public int getReplyListPagingUserCnt(String nickname, SearchCriteria searchCriteria) throws Exception {
		return userpagemanager.getReplyListPagingUserCnt(nickname, searchCriteria);
	}
}
