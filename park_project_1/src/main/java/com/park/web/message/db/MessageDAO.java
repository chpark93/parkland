package com.park.web.message.db;

import java.util.List;

import com.park.web.common.SearchCriteria;
import com.park.web.user.db.UserVO;

public interface MessageDAO {

	public void insertMessage(MessageVO messageVO) throws Exception;
	
	public List<MessageVO> getReceiveMessageList(String nickname, SearchCriteria searchCriteria) throws Exception;

	public int getReceiveMessageListCnt(String nickname, SearchCriteria searchCriteria) throws Exception;

	public MessageVO getMessageSendContent(Integer mid) throws Exception;

	public MessageVO getMessageReceiveContent(Integer mid) throws Exception;
	
	public int updateMessageOpenSend(String nickname, Integer mid, String message_serial) throws Exception;

	public int updateMessageOpenReceive(String nickname, Integer mid, String message_serial) throws Exception;

	public List<MessageVO> getSendMessageList(String nickname, SearchCriteria searchCriteria) throws Exception;

	public int getSendMessageListCnt(String nickname, SearchCriteria searchCriteria) throws Exception;

	public int deleteMessageSend(Integer mid) throws Exception;

	public void deleteCheckedMessageReceive(MessageVO messageVO) throws Exception;

	public void insertMessageReceive(MessageVO messageVO) throws Exception;


	public int deleteMessageReceive(Integer mid) throws Exception;


	public void deleteCheckedMessageSend(MessageVO messageVO) throws Exception;

	String getReceiveSerialKey(Integer mid) throws Exception;

	String getSendSerialKey(Integer mid) throws Exception;

	String getUserIdFromNickname(String nickname) throws Exception;

	
	
}
