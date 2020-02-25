package com.park.web.admin.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.park.web.admin.service.AdminService;
import com.park.web.common.Criteria;
import com.park.web.common.PageMaker;
import com.park.web.common.SearchCriteria;
import com.park.web.interceptor.SessionName;
import com.park.web.user.db.UserVO;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Inject
	private AdminService aservice;	
	

	//회원 관리  리스트
	@RequestMapping(value="/selectUserList", method=RequestMethod.GET)
	public String selectUserList(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria , 
			HttpServletRequest request, HttpSession session, Model model, @RequestParam(required = false, defaultValue="1") int page) throws Exception {
		
		Criteria criteria = new Criteria();
		criteria.setPage(page);
		
		List<UserVO> userList = aservice.selectUserList(searchCriteria);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(searchCriteria);
		pageMaker.setListCnt(aservice.selectUserListCnt(searchCriteria));
		
		model.addAttribute("pagination", pageMaker);
		model.addAttribute("userList", userList);
		
		return "admin/userList";
	}
	
	
	//회원 정보
	@RequestMapping(value="/UserInfoPageAdmin", method=RequestMethod.GET)
	public String UserInfoPageAdmin(UserVO userVO, String id, Model model, HttpSession session, RedirectAttributes rttr) throws Exception {
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser.getAuthority().equals("ROLE_ADMIN")) {
			
			model.addAttribute("user", aservice.getUserInfo(id));
			model.addAttribute("suspend", aservice.getSuspendUserInfo(id));
		}
		return "admin/UserInfoAdmin";
	}
	
	
	//회원 정지 페이지
	@RequestMapping(value="/userSuspendPage", method=RequestMethod.GET)
	public String userSuspendPage(UserVO userVO, String id, Model model, HttpSession session, RedirectAttributes rttr) throws Exception {
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser.getAuthority().equals("ROLE_ADMIN")) {
			model.addAttribute("user", aservice.getUserInfo(id));
			model.addAttribute("suspend", aservice.getSuspendUserInfo(id));
			
		}
		
		return "admin/userSuspendPage";
	}
	
	@RequestMapping(value="/userSuspend", method= {RequestMethod.GET, RequestMethod.POST})
	public String userSuspend(@ModelAttribute("userVO") UserVO userVO, String id, Model model, HttpSession session, RedirectAttributes rttr) throws Exception {
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		UserVO user = aservice.getUserInfo(id);
		
		if(loginUser.getAuthority().equals("ROLE_ADMIN")) {
			userVO.setId(user.getId());
			userVO.setSuspend_term(userVO.getSuspend_term());
			
			aservice.userSuspend(userVO);
			
		}
		
		rttr.addAttribute("id", userVO.getId());
		
		return "redirect:/admin/userSuspendPage";
	}
	
	
	
	//회원 정지 해제(Admin)
	@RequestMapping(value="/userSuspendClearAdmin", method= {RequestMethod.GET, RequestMethod.POST})
	public String userSuspendClearAdmin(UserVO userVO, String id, HttpSession session, RedirectAttributes rttr) throws Exception {
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser.getAuthority().equals("ROLE_ADMIN")) {
			aservice.userSuspendClearAdmin(id);			
		}
		
		return "redirect:/admin/selectUserList";
	}

	//회원 강제 탈퇴
	@RequestMapping(value="/ejectUserAdmin", method= {RequestMethod.GET, RequestMethod.POST})
	public String ejectUserAdmin(UserVO userVO, String id, Model model, HttpSession session, RedirectAttributes rttr) throws Exception {
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser.getAuthority().equals("ROLE_ADMIN")) {
			aservice.ejectUserAdmin(id);			
		}
		
		return "redirect:/admin/selectUserList";
	}
	
	
	
	
	
}
