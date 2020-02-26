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
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.park.web.interceptor.SessionName;
import com.park.web.mypage.db.MyPageDAO;
import com.park.web.user.db.UserVO;

@Repository
public class EchoHandler extends TextWebSocketHandler {

	@Inject
	SqlSession sqlsession;
	
	private final Logger logger = LoggerFactory.getLogger(EchoHandler.class);
	
	List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	Map<String, WebSocketSession> userSessions = new HashMap<String, WebSocketSession>(); 
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("afterConnectionEstablished : " + session);
		
		
		sessions.add(session);
		String senderId = getId(session);
		
		userSessions.put(senderId, session);
		
		logger.info("{} 연결 ", senderId);
		
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage : " + session + " : " + message);
		
		Map<String, Object> httpSession = session.getAttributes();
		UserVO loginUser = (UserVO) httpSession.get(SessionName.LOGIN);
		System.out.println("user : " + loginUser.getId());		
		
		String mypagemanager = sqlsession.selectOne("mypage.getAlarmAllCnt", loginUser.getId());
		System.out.println("mypagemanager : " + mypagemanager);
		
		session.sendMessage(new TextMessage(mypagemanager));
		
			
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
