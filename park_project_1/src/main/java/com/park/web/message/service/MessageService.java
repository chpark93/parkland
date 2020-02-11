package com.park.web.message.service;

import java.util.List;

import com.park.web.common.SearchCriteria;
import com.park.web.message.db.MessageVO;

public interface MessageService {

	public void insertMessage(MessageVO messageVO) throws Exception;

	public List<MessageVO> getReceiveMessageList(String nickname, SearchCriteria searchCriteria) throws Exception;

	public int getReceiveMessageListCnt(String nickname, SearchCriteria searchCriteria) throws Exception;

	public List<MessageVO> getSendMessageList(String nickname, SearchCriteria searchCriteria) throws Exception;
	
	public int getSendMessageListCnt(String nickname, SearchCriteria searchCriteria) throws Exception;

	
	public MessageVO getMessageSendContent(String nickname, Integer mid, String message_serial) throws Exception;

	public void deleteMessageSend(Integer mid) throws Exception;

	public MessageVO getMessageReceiveContent(String nickname, Integer mid, String message_serial) throws Exception;

	public void deleteCheckedMessageReceive(MessageVO messageVO) throws Exception;

	public void deleteMessageReceive(Integer mid) throws Exception;


	public void deleteCheckedMessageSend(MessageVO messageVO) throws Exception;


	
	
}
