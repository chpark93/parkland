package com.park.web.main.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.park.web.board.db.BoardVO;
import com.park.web.interceptor.SessionName;
import com.park.web.main.service.MainService;
import com.park.web.user.db.UserVO;

@Controller
@RequestMapping("/main")
public class MainController {
	
	@Inject
	private MainService mainservice;
	
	
	@RequestMapping(value="/mainPage", method=RequestMethod.GET)
	public String mainPage(BoardVO boardVO, Model model, HttpServletRequest request, HttpSession session) throws Exception {
	
		//로그인 정보
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		if(loginUser != null) {
			model.addAttribute("loginUser", loginUser);
		}
		
		//추천 게시글
		List<BoardVO> recommendBoard = mainservice.getRecommendBoard(boardVO);
		model.addAttribute("recommendBoard", recommendBoard);
		
		//인기 게시글
		List<BoardVO> viewBestBoard = mainservice.getViewBestBoard(boardVO);
		model.addAttribute("viewBestBoard", viewBestBoard);
		
		//게시글
		List<BoardVO> commonBoard = mainservice.getCommonBoard("1");
		model.addAttribute("commonBoard", commonBoard);
		
		List<BoardVO> commonBoard2 = mainservice.getCommonBoard("2");
		model.addAttribute("commonBoard2", commonBoard2);
		
		List<BoardVO> commonBoard3 = mainservice.getCommonBoard("10");
		model.addAttribute("commonBoard3", commonBoard3);
		
		List<BoardVO> commonBoard4 = mainservice.getCommonBoard("5");
		model.addAttribute("commonBoard4", commonBoard4);
		
		List<BoardVO> commonBoard5 = mainservice.getCommonBoard("15");
		model.addAttribute("commonBoard5", commonBoard5);
		
		
		return "initPage/main";
	}
	
}
