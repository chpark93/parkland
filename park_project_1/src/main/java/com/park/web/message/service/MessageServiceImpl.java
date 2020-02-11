package com.park.web.message.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.web.common.SearchCriteria;
import com.park.web.message.db.MessageDAO;
import com.park.web.message.db.MessageVO;
import com.park.web.message.util.SerialKey;
import com.park.web.mypage.db.AlarmVO;
import com.park.web.mypage.db.MyPageDAO;
import com.park.web.user.db.UserDAO;
import com.park.web.user.db.UserVO;

@Service
public class MessageServiceImpl implements MessageService{

	@Inject
	private MessageDAO mmanager;
	
	@Inject
	private UserDAO usermanager;
	
	@Inject
	private MyPageDAO mpmanager;
	
	//쪽지 작성
	@Transactional
	@Override
	public void insertMessage(MessageVO messageVO) throws Exception {
		
		String serialKey = new SerialKey().serialKey();
		messageVO.setMessage_serial(serialKey);
		
		mmanager.insertMessage(messageVO);
		mmanager.insertMessageReceive(messageVO);
		
		
		//Alarm
		Integer mid = mpmanager.getMessageAlarm(messageVO).getMid();
		String target_nickname = mpmanager.getMessageAlarm(messageVO).getMessage_receiver();
		String target_id = mmanager.getUserIdFromNickname(target_nickname);
		
		if(!messageVO.getMessage_sender().equals(target_id)) {			
			String msg = messageVO.getMessage_sender() + "님이 " + "<a href='../message/getMessageReceiveContent?mid=" + mid + " '>쪽지</a>" + " 를 보내셨습니다.";
			
			AlarmVO alarmVO = new AlarmVO();
			alarmVO.setNickname(messageVO.getMessage_sender());
			alarmVO.setTarget_id(target_id);
			alarmVO.setMsg(msg);
			 
			mpmanager.insertAlarm(alarmVO);
			mpmanager.getMessageAlarm(messageVO);
		}
	}
	
	
	//쪽지 리스트
	//Receive
	@Override
	public List<MessageVO> getReceiveMessageList(String nickname, SearchCriteria searchCriteria) throws Exception {
	
		return mmanager.getReceiveMessageList(nickname ,searchCriteria);
	}	
	@Override
	public int getReceiveMessageListCnt(String nickname, SearchCriteria searchCriteria) throws Exception {
		return mmanager.getReceiveMessageListCnt(nickname, searchCriteria);
	}
	
	
	//Send
	@Override
	public List<MessageVO> getSendMessageList(String nickname, SearchCriteria searchCriteria) throws Exception {
	
		return mmanager.getSendMessageList(nickname ,searchCriteria);
	}	
	@Override
	public int getSendMessageListCnt(String nickname, SearchCriteria searchCriteria) throws Exception {
		return mmanager.getSendMessageListCnt(nickname, searchCriteria);
	}
	
	
	//쪽지 확인
	@Transactional
	@Override
	public MessageVO getMessageSendContent(String nickname, Integer mid, String message_serial) throws Exception {

		return mmanager.getMessageSendContent(mid);
	}
	
	@Transactional
	@Override
	public MessageVO getMessageReceiveContent(String nickname, Integer mid, String message_serial) throws Exception {
		
		String SendMessage_serial = mmanager.getSendSerialKey(mid);
		String ReceiveMessage_serial = mmanager.getReceiveSerialKey(mid);
		
		mmanager.updateMessageOpenReceive(nickname, mid, ReceiveMessage_serial);
		mmanager.updateMessageOpenSend(nickname, mid, SendMessage_serial);
		
		return mmanager.getMessageReceiveContent(mid);
	}
	
	//쪽지 삭제
	//send
	@Override
	public void deleteMessageSend(Integer mid) throws Exception {
		mmanager.deleteMessageSend(mid);
	}
	
	//receive
	@Override
	public void deleteMessageReceive(Integer mid) throws Exception {
		mmanager.deleteMessageReceive(mid);
	}
	
	//delete Checked Message
	@Override
	public void deleteCheckedMessageReceive(MessageVO messageVO) throws Exception {
		mmanager.deleteCheckedMessageReceive(messageVO);
	}
	
	@Override
	public void deleteCheckedMessageSend(MessageVO messageVO) throws Exception {
		mmanager.deleteCheckedMessageSend(messageVO);
	}
	
}
