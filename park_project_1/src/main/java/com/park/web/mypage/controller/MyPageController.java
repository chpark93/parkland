package com.park.web.mypage.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.park.web.board.db.BoardVO;
import com.park.web.board.db.ReplyVO;
import com.park.web.board.service.BoardService;
import com.park.web.common.Criteria;
import com.park.web.common.PageMaker;
import com.park.web.common.SearchCriteria;
import com.park.web.interceptor.SessionName;
import com.park.web.memberShip.service.MemberShipService;
import com.park.web.menu.db.MenuVO;
import com.park.web.mypage.db.AlarmVO;
import com.park.web.mypage.service.MyPageService;
import com.park.web.user.db.UserVO;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

	private static final Logger logger = LoggerFactory.getLogger(MyPageController.class);

	@Inject
	private MyPageService mpservice;
	
	@Inject
	private BoardService service;
	
	@Inject
	private MemberShipService msservice;

	
	//전체 게시판 List Paging From Id
	@RequestMapping(value="/getBoardListPagingFromId", method=RequestMethod.GET)
	public String getBoardListPagingFromId(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria, BoardVO boardVO, Model model, 
			@RequestParam(required  = false, defaultValue="1") Integer page, HttpSession session) throws Exception {
		
		//user info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {
			
			model.addAttribute("loginUser", loginUser);
			searchCriteria.setLoginUser(loginUser.getId());
			searchCriteria.setBoardSection("mypage");
			
			Criteria criteria = new Criteria();
			criteria.setPage(page);
			
			List<BoardVO> board = mpservice.getBoardListPagingFromId(searchCriteria);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCriteria(searchCriteria);
			pageMaker.setListCnt(mpservice.getBoardListPagingFromIdCnt(loginUser.getId(), searchCriteria));
			
			model.addAttribute("pagination", pageMaker);
			model.addAttribute("boardList", board); 
			model.addAttribute("profile", msservice.getUserProfile(loginUser.getId()));
			
			return "user/myPageBoard";
		}
		else {
			
			return "user/myPageBoard";
		}
		
		
	}
	
	
	//마이페이지 게시글 리스트에서 제거
	@RequestMapping(value="/deleteMyPageBoard", method= {RequestMethod.GET, RequestMethod.POST})
	public String deleteMyPageBoard(@RequestParam("deleteCheckBox") List<String> deleteCheckBoxArr ,
			BoardVO boardVO, HttpServletRequest request, HttpSession session) throws Exception {
		
		//user info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {	
			
			for(String i : deleteCheckBoxArr) {
				Integer bid = Integer.parseInt(i);
				
				boardVO.setBid(bid);
				mpservice.deleteMyPageBoard(boardVO);
			}
			
		}
		
		return "redirect:/mypage/getBoardListPagingFromId";
	}
	
	
	
	//전체 댓글 List Paging
	@RequestMapping(value="/getReplyListPagingFromId", method=RequestMethod.GET)
	public String getReplyListPagingFromId(ReplyVO replyVO,@ModelAttribute("searchCriteria") SearchCriteria searchCriteria, Model model, 
			@RequestParam(required  = false, defaultValue="1") Integer page, HttpSession session) throws Exception {
		
		//user info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {
			
			model.addAttribute("loginUser", loginUser);
			
			searchCriteria.setLoginUser(loginUser.getId());
			searchCriteria.setBoardSection("mypage");
			
			Criteria criteria = new Criteria();
			criteria.setPage(page);
			
			List<ReplyVO> reply = mpservice.getReplyListPagingFromId(searchCriteria);
			System.out.println("reply : " + reply);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCriteria(searchCriteria);
			pageMaker.setListCnt(mpservice.getReplyListPagingFromIdCnt(loginUser.getId(), searchCriteria));
			pageMaker.setBg_no(searchCriteria.getBg_no());
			
			
			model.addAttribute("pagination", pageMaker);
			model.addAttribute("replyList", reply);
			model.addAttribute("profile", msservice.getUserProfile(loginUser.getId()));
			
			
			return "user/myPageReply";
		}
		else {
			return "user/myPageReply";			
		}
	}
	
	
	//마이페이지 댓글 리스트에서 제거
	@RequestMapping(value="/deleteMyPageReply", method= {RequestMethod.GET, RequestMethod.POST})
	public String deleteMyPageReply(@RequestParam("deleteCheckBox") List<String> deleteCheckBoxArr ,
			ReplyVO replyVO, HttpServletRequest request, HttpSession session) throws Exception {
		
		//user info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {	
			
			
			for(String i : deleteCheckBoxArr) {
				Integer rid = Integer.parseInt(i);
				
				replyVO.setRid(rid);
				mpservice.deleteMyPageReply(replyVO);
			}
			
		}
		
		return "redirect:/mypage/getReplyListPagingFromId";
	}
	
	@RequestMapping(value="/getAlarmListPagingFromId", method=RequestMethod.GET)
	public String getAlarmListPagingFromId(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria, AlarmVO alarmVO, Model model, 
			@RequestParam(required  = false, defaultValue="1") Integer page, HttpSession session) throws Exception {
		
		//user info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
				
		if(loginUser != null) {
			
			//만료 기간 지난 알람 삭제 처리
			mpservice.deleteMyPageAlarmExpired();
			
			model.addAttribute("loginUser", loginUser);
			searchCriteria.setLoginUser(loginUser.getId());
			
			Criteria criteria = new Criteria();
			criteria.setPage(page);
			
			List<AlarmVO> alarm = mpservice.getAlarmListPagingFromId(searchCriteria);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCriteria(searchCriteria);
			pageMaker.setListCnt(mpservice.getAlarmListPagingFromIdCnt(loginUser.getId(), searchCriteria));
			
			model.addAttribute("pagination", pageMaker);
			model.addAttribute("alarmList", alarm);
			model.addAttribute("profile", msservice.getUserProfile(loginUser.getId()));
			
			return "user/myPageAlarm";
		}
		else {
			return "user/myPageAlarm";
		}
	}


	//마이페이지 Alarm 리스트에서 제거
	@RequestMapping(value="/deleteMyPageAlarm", method= {RequestMethod.GET, RequestMethod.POST})
	public String deleteMyPageAlarm(@RequestParam("deleteCheckBox") List<String> deleteCheckBoxArr,
			AlarmVO alarmVO, HttpServletRequest request, HttpSession session) throws Exception {
		
		//user info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {	
			
			for(String i : deleteCheckBoxArr) {
				Integer aid = Integer.parseInt(i);
				
				alarmVO.setAid(aid);
				mpservice.deleteMyPageAlarm(alarmVO);
			}
			
		}
		
		return "redirect:/mypage/getAlarmListPagingFromId";
	}




}
