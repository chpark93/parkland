package com.park.web.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.park.web.board.db.BoardVO;
import com.park.web.board.db.ReplyVO;
import com.park.web.board.service.BoardService;
import com.park.web.common.Criteria;
import com.park.web.common.PageMaker;
import com.park.web.common.SearchCriteria;
import com.park.web.interceptor.SessionName;
import com.park.web.memberShip.service.MemberShipService;
import com.park.web.menu.db.MenuVO;
import com.park.web.menu.service.MenuService;
import com.park.web.mypage.db.AlarmVO;
import com.park.web.mypage.service.MyPageService;
import com.park.web.user.db.UserVO;
import com.park.web.userpage.service.UserPageService;


@Controller
@RequestMapping(value="/board")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private BoardService service;
	
	@Inject
	private MemberShipService msservice;
	
	@Inject
	private MenuService menuservice;
	
	@Inject
	private UserPageService userpageservice;

	
	//�Խ��� ����Ʈ
	@RequestMapping(value="/getBoardList", method=RequestMethod.GET)
	public String getBoardList(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria, @RequestParam("bg_no") String bg_no, Model model, 
			@RequestParam(required  = false, defaultValue="1") int page, HttpServletRequest request, HttpSession session) throws Exception {
		
		Criteria criteria = new Criteria();
		criteria.setPage(page);
		
		List<BoardVO> boardList = service.searchBoardList(searchCriteria);
	
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(searchCriteria);
		pageMaker.setListCnt(service.searchedBoardCnt(bg_no, searchCriteria));
		pageMaker.setBg_no(searchCriteria.getBg_no());
		
		//boardGroupInfo
		MenuVO boardGroupInfo = service.boardGroupInfoFromBg_no(searchCriteria.getBg_no());
		
		model.addAttribute("pagination", pageMaker);
		model.addAttribute("boardList", boardList);
		model.addAttribute("boardGroupInfo", boardGroupInfo);
		
		//�α��� ����(boardlist)
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {
			String userName = service.getUserName(loginUser.getId()); 
			model.addAttribute("userName", userName);
			model.addAttribute("loginUser", loginUser);
			
			UserVO loginInfo = service.loginInfo(loginUser.getId());
			model.addAttribute("access_date", loginInfo.getAccess_date());
		
		}
		

		
		return "board/board_list";
	}	
	
	
	//�۾��� �� �̵�
	@RequestMapping(value="/boardForm", method=RequestMethod.GET)
	public String boardForm(@ModelAttribute("board") BoardVO board, SearchCriteria searchCriteria ,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		model.addAttribute("board", board);
		model.addAttribute("user", msservice.getUserInfo(loginUser.getId()));
		model.addAttribute("searchCriteria", searchCriteria);
		
		//�Խ��� ����Ʈ
		model.addAttribute("boardGroupList", menuservice.getBoardGroupList());
		return "board/boardForm";
	}
	
	
	//�Խñ� �μ�Ʈ
	@RequestMapping(value="/saveBoard", method=RequestMethod.POST)
	public String saveBoard(@ModelAttribute("boardVO") BoardVO boardVO, SearchCriteria searchCriteria, @RequestParam("mode") String mode, 
			@RequestParam("bid") Integer bid, Model model, HttpServletRequest request, RedirectAttributes rttr) throws Exception {
		
		//����
		if(mode.equals("edit")) {
			//edit
			service.updateBoard(boardVO);
			
		}
		else {
			//insert
			service.insertBoard(boardVO);

		}
		
		searchCriteria.setBg_no(boardVO.getBg_no());
		
		System.out.println("insert bg_no : " + boardVO.getBg_no());
		
		rttr.addAttribute("bid", boardVO.getBid());
		rttr.addAttribute("bg_no", searchCriteria.getBg_no());
		rttr.addAttribute("page", searchCriteria.getPage());
        rttr.addAttribute("listSize", searchCriteria.getListSize());
        rttr.addAttribute("searchType", searchCriteria.getSearchType());
        rttr.addAttribute("keyword", searchCriteria.getKeyword());
	
		return "redirect:/board/getBoardContent";
	}
	
	
	//�Խñ� ��������
	@RequestMapping(value="/getBoardContent", method=RequestMethod.GET)
	public String getBoardContent(SearchCriteria searchCriteria, @RequestParam("bid") Integer bid, BoardVO boardVO, ReplyVO replyVO, Model model, 
			AlarmVO alarmVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rttr) throws Exception {
				
		
		//�Խñ� ��ȸ�� �ߺ� ����
		Cookie[] cookies = request.getCookies();
		Map<String, Object> mapCookie = new HashMap<String, Object>();
		
		if (cookies != null) {
			for(int i = 0; i < cookies.length; i++) {
				Cookie obj = cookies[i];
				mapCookie.put(obj.getName(), obj.getValue());
			}	
		}
		String view_cnt_cookie = (String) mapCookie.get("view_cnt");
		String new_view_cnt_cookie = "|" + bid;
		
		if(StringUtils.indexOfIgnoreCase(view_cnt_cookie, new_view_cnt_cookie) == -1) {
			//�������� �ʴ� ��� ��Ű ����
			Cookie cookie = new Cookie("view_cnt", view_cnt_cookie + new_view_cnt_cookie);
			cookie.setMaxAge(60 * 60 * 24);
			response.addCookie(cookie);
			
			service.updateViewCnt(bid);			
		}
		
		model.addAttribute("boardContent", service.getBoardContent(bid));
		model.addAttribute("reply", replyVO);
		
		UserVO boardUserInfoFromNickName = userpageservice.getUserInfo(boardVO.getReg_nickname()); 
		if(boardUserInfoFromNickName != null) {				
			model.addAttribute("userInfo", boardUserInfoFromNickName);
		}
		
		searchCriteria.setBg_no(service.getBoardContent(bid).getBg_no());
		
		rttr.addAttribute("bg_no", searchCriteria.getBg_no());	
		rttr.addAttribute("page", searchCriteria.getPage());
        rttr.addAttribute("listSize", searchCriteria.getListSize());
        rttr.addAttribute("searchType", searchCriteria.getSearchType());
        rttr.addAttribute("keyword", searchCriteria.getKeyword());
        rttr.addAttribute("boardSection", searchCriteria.getBoardSection());
        
        
		return "board/boardContent";
	}
	
	
	//�Խñ� ����
	@RequestMapping(value="/updateBoard", method=RequestMethod.GET)
	public String updateBoard(@ModelAttribute("board") BoardVO boardVO, SearchCriteria searchCriteria , @RequestParam("bid") Integer bid, @RequestParam("mode") String mode, Model model, RedirectAttributes rttr) throws Exception {
		model.addAttribute("boardContent", service.getBoardContent(bid));
		model.addAttribute("mode", mode);
		model.addAttribute("board", boardVO);
		
		//���� ���� ���� 
		boardVO.setContent(service.getBoardContent(bid).getContent());
		
		searchCriteria.setBg_no(service.getBoardContent(bid).getBg_no());
		
		//�Խ��� ����Ʈ
		model.addAttribute("boardGroupList", menuservice.getBoardGroupList());
		
		rttr.addAttribute("bg_no", searchCriteria.getBg_no());
		rttr.addAttribute("page", searchCriteria.getPage());
        rttr.addAttribute("listSize", searchCriteria.getListSize());
        rttr.addAttribute("searchType", searchCriteria.getSearchType());
        rttr.addAttribute("keyword", searchCriteria.getKeyword());
		
		return "board/boardForm";    
	}
	
	//�Խñ� ����
	@RequestMapping(value="/deleteBoard", method=RequestMethod.GET)
	public String deleteBoard(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria, BoardVO boardVO, @RequestParam("bid") Integer bid,
			RedirectAttributes rttr, Model model) throws Exception {
		
		service.deleteBoard(bid);
		
		searchCriteria.setBg_no(boardVO.getBg_no());
		
		rttr.addAttribute("bg_no", searchCriteria.getBg_no());
		rttr.addAttribute("page", searchCriteria.getPage());
        rttr.addAttribute("listSize", searchCriteria.getListSize());
        rttr.addAttribute("searchType", searchCriteria.getSearchType());
        rttr.addAttribute("keyword", searchCriteria.getKeyword());
	
		return "redirect:/board/getBoardList";
	}
	
	
	//�Խñ� ����(Admin)
	@RequestMapping(value="/deleteBoardAdmin", method= {RequestMethod.GET, RequestMethod.POST})
	public String deleteBoardAdmin(@RequestParam("deleteCheckBox") List<String> deleteCheckBoxArr, SearchCriteria searchCriteria, 
			BoardVO boardVO, HttpSession session, Model model, RedirectAttributes rttr) throws Exception {
		
		//user info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser.getAuthority().equals("ROLE_ADMIN")) {	
			
			for(String i : deleteCheckBoxArr) {
				Integer bid = Integer.parseInt(i);
				
				boardVO.setBid(bid);
				service.deleteBoardAdmin(boardVO.getBid());
			}
			
		}
		searchCriteria.setBg_no(boardVO.getBg_no());
		
		rttr.addAttribute("bg_no", searchCriteria.getBg_no());
		rttr.addAttribute("page", searchCriteria.getPage());
        rttr.addAttribute("listSize", searchCriteria.getListSize());
        rttr.addAttribute("searchType", searchCriteria.getSearchType());
        rttr.addAttribute("keyword", searchCriteria.getKeyword());
		
		return "redirect:/board/getBoardList";
	}
	
	//�Խñ� ��õ
	@RequestMapping(value="/updateBoardRecommend", method= {RequestMethod.GET, RequestMethod.POST})
	public String updateBoardRecommend(BoardVO boardVO, SearchCriteria searchCriteria, RedirectAttributes rttr, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�Խñ� ��õ �ߺ� ����
		Cookie[] cookies = request.getCookies();
		Map<String, Object> mapCookie = new HashMap<String, Object>();
		
		if (cookies != null) {
			for(int i = 0; i < cookies.length; i++) {
				Cookie obj = cookies[i];
				mapCookie.put(obj.getName(), obj.getValue());
			}	
		}
		String recommend_cnt_cookie = (String) mapCookie.get("recommend_cnt");
		String new_recommend_cnt_cookie = "|" + boardVO.getBid();
		
		if(StringUtils.indexOfIgnoreCase(recommend_cnt_cookie, new_recommend_cnt_cookie) == -1) {
			//�������� �ʴ� ��� ��Ű ����
			Cookie cookie = new Cookie("recommend_cnt", recommend_cnt_cookie + new_recommend_cnt_cookie);
			cookie.setMaxAge(60 * 60 * 24);
			response.addCookie(cookie);
						
			service.updateBoardRecommend(boardVO);
		}
				
		rttr.addAttribute("bid", boardVO.getBid());
		rttr.addAttribute("bg_no", searchCriteria.getBg_no());
		rttr.addAttribute("page", searchCriteria.getPage());
		rttr.addAttribute("listSize", searchCriteria.getListSize());
		rttr.addAttribute("searchType", searchCriteria.getSearchType());
		rttr.addAttribute("keyword", searchCriteria.getKeyword());
		rttr.addAttribute("boardSection", searchCriteria.getBoardSection());
		 
		return "redirect:/board/getBoardContent";
	}
	
	
	//��õ �Խñ�
	@RequestMapping(value="/getRecommendBoardList", method=RequestMethod.GET)
	public String getRecommendBoardList(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria, BoardVO boardVO, Model model, 
			 @RequestParam(required  = false, defaultValue="1") int page, 
			HttpServletRequest request, HttpSession session) throws Exception {
		
		Criteria criteria = new Criteria();
		criteria.setPage(page);
		
		searchCriteria.setBoardSection("recommendBoard");
		
		List<BoardVO> recommendBoardList = service.getRecommendBoardList(searchCriteria);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(searchCriteria);
		pageMaker.setListCnt(service.getRecommendBoardCnt(boardVO.getRecommend_cnt(), searchCriteria));
		pageMaker.setBg_no(searchCriteria.getBg_no());
			
		model.addAttribute("pagination", pageMaker);
		model.addAttribute("recommendBoardList", recommendBoardList);

		
		//�α��� ����
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {
			String userName = service.getUserName(loginUser.getId()); 
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("userName", userName);
			
			UserVO loginInfo = service.loginInfo(loginUser.getId());
			model.addAttribute("access_date", loginInfo.getAccess_date());
		
		}
		
		return "board/recommendBoard_list";
	}
	
	
	
	//�α� �Խñ�
	@RequestMapping(value="/getViewBestBoardList", method=RequestMethod.GET)
	public String getViewBestBoardList(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria, BoardVO boardVO, Model model, 
			@RequestParam(required  = false, defaultValue="1") int page, 
			HttpServletRequest request, HttpSession session) throws Exception {
		
		Criteria criteria = new Criteria();
		criteria.setPage(page);

		searchCriteria.setBoardSection("viewBestBoard");
		
		List<BoardVO> viewBestBoardList = service.getViewBestBoardList(searchCriteria);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(searchCriteria);
		pageMaker.setListCnt(service.getViewBestBoardCnt(boardVO.getView_cnt(), searchCriteria));
		pageMaker.setBg_no(boardVO.getBg_no());
		
		model.addAttribute("pagination", pageMaker);
		model.addAttribute("viewBestBoardList", viewBestBoardList);
		
		//�α��� ����
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {
			String userName = service.getUserName(loginUser.getId()); 
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("userName", userName);
			
			UserVO loginInfo = service.loginInfo(loginUser.getId());
			model.addAttribute("access_date", loginInfo.getAccess_date());
			
		}
		
		return "board/viewBestBoard_list";
	}
	
	
}
