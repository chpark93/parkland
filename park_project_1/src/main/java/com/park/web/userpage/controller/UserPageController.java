package com.park.web.userpage.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.park.web.board.db.BoardVO;
import com.park.web.board.db.ReplyVO;
import com.park.web.common.Criteria;
import com.park.web.common.PageMaker;
import com.park.web.common.SearchCriteria;
import com.park.web.interceptor.SessionName;
import com.park.web.memberShip.service.MemberShipService;
import com.park.web.user.db.UserVO;
import com.park.web.userpage.service.UserPageService;

@Controller
@RequestMapping("/userpage")
public class UserPageController {
	
	@Inject
	private UserPageService userpageservice; 
	
	@Inject
	private MemberShipService msservice;
	
	//User Board
	@RequestMapping(value="/getBoardListPagingUser", method=RequestMethod.GET)
	public String getBoardListPagingUser(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria, BoardVO boardVO, String nickname, String id, Model model, 
			@RequestParam(required  = false, defaultValue="1") Integer page, HttpSession session) throws Exception {
		
		//user info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {
			
			model.addAttribute("loginUser", loginUser);
			
			UserVO user = userpageservice.getUserInfo(nickname);
			if(user == null) {
				return "user/notUserPage";
			}
			
			searchCriteria.setNickname(userpageservice.getUserInfo(nickname).getNickname());	
			searchCriteria.setBoardSection("userpage");
			
			Criteria criteria = new Criteria();
			criteria.setPage(page);
			
			List<BoardVO> board = userpageservice.getBoardListPagingUser(searchCriteria);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCriteria(searchCriteria);
			pageMaker.setListCnt(userpageservice.getBoardListPagingUserCnt(nickname, searchCriteria));
			pageMaker.setBg_no(searchCriteria.getBg_no());
			
			model.addAttribute("user", userpageservice.getUserInfo(nickname));
			model.addAttribute("pagination", pageMaker);
			model.addAttribute("boardList", board);
			model.addAttribute("profile", msservice.getUserProfileNickName(nickname));
			
			return "user/userPageBoard";
		}
		else {
			
			return "user/userPageBoard";
		}
		
		
	}

	
	//User Reply
	@RequestMapping(value="/getReplyListPagingUser", method=RequestMethod.GET)
	public String getReplyListPagingUser(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria, BoardVO boardVO, String nickname, Model model, 
			@RequestParam(required  = false, defaultValue="1") Integer page, HttpSession session) throws Exception {
		
		//user info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {
			
			model.addAttribute("loginUser", loginUser);
			searchCriteria.setNickname(userpageservice.getUserInfo(nickname).getNickname());	
			searchCriteria.setBoardSection("userpage");
			
			Criteria criteria = new Criteria();
			criteria.setPage(page);
			
			List<ReplyVO> reply = userpageservice.getReplyListPagingUser(searchCriteria);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCriteria(searchCriteria);
			pageMaker.setListCnt(userpageservice.getReplyListPagingUserCnt(nickname, searchCriteria));
			
			model.addAttribute("user", userpageservice.getUserInfo(nickname));
			model.addAttribute("pagination", pageMaker);
			model.addAttribute("replyList", reply);
			model.addAttribute("profile", msservice.getUserProfileNickName(nickname));
			
			return "user/userPageReply";
		}
		else {
			
			return "user/userPageReply";
		}
		
		
	}

}
