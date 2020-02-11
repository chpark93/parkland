package com.park.web.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.park.web.board.db.ReplyVO;
import com.park.web.board.service.ReplyService;
import com.park.web.common.Criteria;
import com.park.web.common.PageMaker;
import com.park.web.common.SearchCriteria;
import com.park.web.interceptor.SessionName;
import com.park.web.user.db.UserVO;


@RestController
@RequestMapping("/replyBoard")
public class ReplyController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	@Inject
	private ReplyService reservice;
	
	
	//List Paging
	@RequestMapping(value="/{bid}/{page}", method= RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getReplyListPaging(ReplyVO replyVO, SearchCriteria searchCriteria, @PathVariable("bid") Integer bid, @PathVariable("page") Integer page,
			HttpSession session) throws Exception {
		logger.info("ReplyListPaging bid, page>> {}, {}", bid, page);
		
		ResponseEntity<Map<String, Object>> entity = null;
		
		try {
			Criteria criteria = new Criteria();
			criteria.setPage(page);
			
			List<ReplyVO> reply = reservice.getReplyListPaging(bid, searchCriteria);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCriteria(searchCriteria); 
			pageMaker.setListCnt(reservice.getReplyListPagingCnt(bid, searchCriteria));
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pagination", pageMaker); 
			map.put("reply", reply);
			map.put("searchCriteria", searchCriteria);
			
			//user info
			UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
			if(loginUser != null) {
				
				map.put("loginUser", loginUser.getId());
			}
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			
		}catch(Exception e) {
			
			e.printStackTrace();
			entity = new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		
		}
			
		return entity;
	}

	
	//insert
	@RequestMapping(value="/insertReply", method=RequestMethod.POST)
	public ResponseEntity<String> insertReply(@RequestBody ReplyVO replyVO, HttpServletRequest request, HttpSession session) throws Exception {
		ResponseEntity<String> entity = null;

		//user info
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		try {
			//어뷰징 방지
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			if(loginUser == null && loginCookie == null) {
				entity = new ResponseEntity<>("Not Login", HttpStatus.UNAUTHORIZED);
			}
			else if(loginUser != null) {
				reservice.insertReply(replyVO);
				
				entity = new ResponseEntity<>("insertSuccess", HttpStatus.OK);
			}
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	
	//update
	//PUT : 전체 entity
	//PATCH : 변경하고자 하는 속성
	@RequestMapping(value="/updateReply", method= {RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> updateReply(@RequestBody ReplyVO replyVO) throws Exception {
		ResponseEntity<String> entity = null;
		
		try{
			reservice.updateReply(replyVO);
			
			entity = new ResponseEntity<>("updateSuccess", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	//delete
	@RequestMapping(value="/deleteReply", method= RequestMethod.DELETE)
	public ResponseEntity<String> deleteReply(@RequestBody ReplyVO replyVO) throws Exception {
		ResponseEntity<String> entity = null;
		
		try { 
			reservice.deleteReply(replyVO);
			
			entity = new ResponseEntity<>("deleteSuccess", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	
	
}
