package com.park.web.webSocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.park.web.interceptor.SessionName;
import com.park.web.user.db.UserVO;


public class ReplyEchoHandler extends TextWebSocketHandler {

	
	private final Logger logger = LoggerFactory.getLogger(ReplyEchoHandler.class);
	
	List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	Map<String, WebSocketSession> userSessions = new HashMap<String, WebSocketSession>(); 
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("afterConnectionEstablished : " + session);
		
		sessions.add(session);
		String senderId = getId(session);
		
		userSessions.put(senderId, session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage : " + session + " : " + message);
		
		//protocol : cmd , 댓글 작성자, 게시글 작성자 , 게시글 번호  (ex : reply, user2, user1, bid)
		String msg = message.getPayload();
		
		if(StringUtils.isNotEmpty(msg)) { //null 처리
			String[] str = msg.split(","); 
			
			if(str != null && str.length == 4) {
				String cmd = str[0];	
				String replyWriter = str[1];	
				String boardWriter = str[2];	
				String bid = str[3];	
				
				WebSocketSession boardWriterSession = userSessions.get(boardWriter);
				System.out.println("bwsession : " + boardWriterSession);
				
				if("reply".equals(cmd) || boardWriterSession != null) {
					System.out.println("3");
					
					TextMessage textmsg = new TextMessage(replyWriter + " 님이" + " <a href='../board/getBoardContent?bid=" + bid + " '>" + bid + "</a>번 글에 댓글을 남기셨습니다.");
					
					boardWriterSession.sendMessage(textmsg);
					
					
				}
				
				
				
			}
		}
		/*
		Map<String, Object> httpSession = session.getAttributes();
		UserVO loginUser = (UserVO) httpSession.get(SessionName.LOGIN);
				
		MyPageDAO mypagemanager = sqlsession.getMapper(MyPageDAO.class);
		
		session.sendMessage(new TextMessage(mypagemanager.getAlarmAllCnt(loginUser.getId())));
		System.out.println("message : " + message.getPayload());
		*/
		
	}


	private String getId(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes(); //httpsession의 값 map으로 
		
		UserVO loginUser = (UserVO) httpSession.get(SessionName.LOGIN);
		
		if(loginUser == null) {
			return session.getId();
		}
		else {
			return loginUser.getId();
		}
		
	}

	@Override 
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("afterConnectionClosed : " + session + " : " + status); 
	}

	
}
