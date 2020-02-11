package com.park.web.message.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.park.web.board.db.BoardVO;
import com.park.web.common.Criteria;
import com.park.web.common.PageMaker;
import com.park.web.common.SearchCriteria;
import com.park.web.interceptor.SessionName;
import com.park.web.message.db.MessageVO;
import com.park.web.message.service.MessageService;
import com.park.web.user.db.UserVO;

//RestController

@Controller
@RequestMapping("/message")
public class MessageController {
	
	@Inject
	private MessageService mservice;
	
	
	//쪽지 보내기
	@RequestMapping(value="/insertMessage", method=RequestMethod.POST)
	public String insertMessage(@ModelAttribute("messageVO") MessageVO messageVO, UserVO userVO, SearchCriteria searchCriteria, 
			Model model, HttpSession session, HttpServletRequest request, RedirectAttributes rttr) throws Exception {
		
		//user info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
	
		messageVO.setMessage_sender(loginUser.getNickname());	
		messageVO.setMessage_receiver(messageVO.getMessage_receiver());
		
		mservice.insertMessage(messageVO);
		
		rttr.addAttribute("page", searchCriteria.getPage());
        rttr.addAttribute("listSize", searchCriteria.getListSize());
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
	
	
	//쪽지 리스트(Receive) => nickname으로 
	@RequestMapping(value="/getReceiveMessageList", method=RequestMethod.GET)
	public String getReceiveMessageList(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria, MessageVO messageVO, Model model, 
			@RequestParam(required  = false, defaultValue="1") int page, HttpServletRequest request, HttpSession session) throws Exception {
		
		//User Info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		if(loginUser != null) {
			model.addAttribute("loginUser", loginUser);
			
			Criteria criteria = new Criteria();
			criteria.setPage(page);
			
			List<MessageVO> messageList = mservice.getReceiveMessageList(loginUser.getNickname(), searchCriteria);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCriteria(searchCriteria);
			pageMaker.setListCnt(mservice.getReceiveMessageListCnt(loginUser.getNickname(), searchCriteria));
			
			model.addAttribute("pagination", pageMaker);
			model.addAttribute("messageList", messageList);
			
			return "message/userReceiveMessagePage";
			
		}
		else {
			return "message/userReceiveMessagePage";
		}
		
	}
	
	
	//쪽지 리스트(Send) => nickname으로 
	@RequestMapping(value="/getSendMessageList", method=RequestMethod.GET)
	public String getSendMessageList(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria, MessageVO messageVO, Model model, 
			@RequestParam(required  = false, defaultValue="1") int page, HttpServletRequest request, HttpSession session) throws Exception {
		
		//User Info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		if(loginUser != null) {
			model.addAttribute("loginUser", loginUser);
			
			Criteria criteria = new Criteria();
			criteria.setPage(page);
			
			List<MessageVO> messageList = mservice.getSendMessageList(loginUser.getNickname(), searchCriteria);
			
			PageMaker pageMaker = new PageMaker(); 
			pageMaker.setCriteria(searchCriteria);
			pageMaker.setListCnt(mservice.getSendMessageListCnt(loginUser.getNickname(), searchCriteria));
			
			model.addAttribute("pagination", pageMaker);
			model.addAttribute("messageList", messageList);
			
			return "message/userSendMessagePage";
		}
		else {
			return "message/userSendMessagePage";
		}
		
	}
	
	//쪽지 상세페이지(send)
	@RequestMapping(value="/getMessageSendContent", method=RequestMethod.GET)
	public String getMessageSendContent(SearchCriteria searchCriteria, @RequestParam("mid") Integer mid, Model model,
			String message_serial, MessageVO messageVO, HttpSession session, RedirectAttributes rttr ) throws Exception {
		
		//User Info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {
			
			model.addAttribute("messageSendContent", mservice.getMessageSendContent(loginUser.getNickname(), mid, message_serial));
			
			rttr.addAttribute("page", searchCriteria.getPage());
	        rttr.addAttribute("listSize", searchCriteria.getListSize());
	        
			return "message/messageSendContent";
		}
		else {
			return "message/messageSendContent";
		}
		
	}
	
	//쪽지 상세페이지(receive)
	@RequestMapping(value="/getMessageReceiveContent", method=RequestMethod.GET)
	public String getMessageReceiveContent(SearchCriteria searchCriteria, @RequestParam("mid") Integer mid, Model model,
			String message_serial, HttpSession session, RedirectAttributes rttr ) throws Exception {
		
		//User Info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {
		
			model.addAttribute("messageReceiveContent", mservice.getMessageReceiveContent(loginUser.getNickname(), mid, message_serial));
			
			rttr.addAttribute("page", searchCriteria.getPage());
	        rttr.addAttribute("listSize", searchCriteria.getListSize());
	        
			return "message/messageReceiveContent";
		}
		else {
			return "message/messageReceiveContent";
		}
		
	}
	
	
	//쪽지 삭제(Send)
	@RequestMapping(value="/deleteMessageSend", method=RequestMethod.GET)
	public String deleteMessageSend(SearchCriteria searchCriteria, MessageVO messageVO, @RequestParam("mid") Integer mid,
			RedirectAttributes rttr, Model model) throws Exception {
	
		mservice.deleteMessageSend(mid);
		
		rttr.addAttribute("page", searchCriteria.getPage());
		rttr.addAttribute("listSize", searchCriteria.getListSize());
		
		return "redirect:/message/getSendMessageList";
		
	}

	
	//쪽지 삭제(Receive)
	@RequestMapping(value="/deleteMessageReceive", method=RequestMethod.GET)
	public String deleteMessageReceive(SearchCriteria searchCriteria, MessageVO messageVO, @RequestParam("mid") Integer mid,
			RedirectAttributes rttr, Model model) throws Exception {
		
		mservice.deleteMessageReceive(mid);
		
		rttr.addAttribute("page", searchCriteria.getPage());
        rttr.addAttribute("listSize", searchCriteria.getListSize());
        
		return "redirect:/message/getReceiveMessageList";
		
	}
	
	
	//쪽지 게시글 리스트에서체크  제거(Receive)
	@RequestMapping(value="/deleteCheckedMessageReceive", method= {RequestMethod.GET, RequestMethod.POST})
	public String deleteCheckedMessageReceive(@RequestParam("deleteCheckBox") List<String> deleteCheckBoxArr ,
			MessageVO messageVO, HttpServletRequest request, HttpSession session) throws Exception {
		
		//user info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {	
			
			for(String i : deleteCheckBoxArr) {
				Integer mid = Integer.parseInt(i);
				
				messageVO.setMid(mid);
				mservice.deleteCheckedMessageReceive(messageVO);
			}
		}
		
		return "redirect:/message/getReceiveMessageList";
	}
		
	
	//쪽지 게시글 리스트에서 체크 제거(Send)
	@RequestMapping(value="/deleteCheckedMessageSend", method= {RequestMethod.GET, RequestMethod.POST})
	public String deleteCheckedMessageSende(@RequestParam("deleteCheckBox") List<String> deleteCheckBoxArr ,
			MessageVO messageVO, HttpServletRequest request, HttpSession session) throws Exception {
		
		//user info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {	
			
			for(String i : deleteCheckBoxArr) {
				Integer mid = Integer.parseInt(i);
				
				messageVO.setMid(mid);
				mservice.deleteCheckedMessageSend(messageVO);
			}
		}
		
		return "redirect:/message/getSendMessageList";
	}
	

	
}
