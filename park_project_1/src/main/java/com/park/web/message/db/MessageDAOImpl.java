package com.park.web.message.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.park.web.common.SearchCriteria;
import com.park.web.user.db.UserVO;

@Repository
public class MessageDAOImpl implements MessageDAO{
	
	@Autowired
	private SqlSession sqlsession;
	
	//�޼��� �ۼ�
	@Override
	public void insertMessage(MessageVO messageVO) throws Exception {
		sqlsession.insert("message.insertMessage", messageVO);
	}
	
	@Override
	public void insertMessageReceive(MessageVO messageVO) throws Exception {
		sqlsession.insert("message.insertMessageReceive", messageVO);
	}
	
	//�޽��� ����Ʈ(Receive)
	@Override
	public List<MessageVO> getReceiveMessageList(String nickname, SearchCriteria searchCriteria) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("nickname", nickname);
		paramMap.put("pagination", searchCriteria);
		
		return sqlsession.selectList("message.getReceiveMessageList", paramMap);
	}
	@Override
	public int getReceiveMessageListCnt(String nickname, SearchCriteria searchCriteria) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("nickname", nickname);
		paramMap.put("pagination", searchCriteria);
		
		return sqlsession.selectOne("message.getReceiveMessageListCnt", paramMap);
	}
	

	//�޽��� ����Ʈ(Send)
	@Override
	public List<MessageVO> getSendMessageList(String nickname, SearchCriteria searchCriteria) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("nickname", nickname);
		paramMap.put("pagination", searchCriteria);
		
		return sqlsession.selectList("message.getSendMessageList", paramMap);
	}
	@Override
	public int getSendMessageListCnt(String nickname, SearchCriteria searchCriteria) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("nickname", nickname);
		paramMap.put("pagination", searchCriteria);
		
		return sqlsession.selectOne("message.getSendMessageListCnt", paramMap);
	}

	
	@Override
	public MessageVO getMessageSendContent(Integer mid) throws Exception {
		return sqlsession.selectOne("message.getMessageSendContent", mid);
	}
	
	@Override
	public MessageVO getMessageReceiveContent(Integer mid) throws Exception {
		return sqlsession.selectOne("message.getMessageReceiveContent", mid);
	}
	
	@Override 
	public int updateMessageOpenSend(String nickname, Integer mid, String message_serial) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mid", mid);
		paramMap.put("nickname", nickname);
		paramMap.put("message_serial", message_serial);
		
		return sqlsession.update("message.updateMessageOpenSend", paramMap);
	}
	
	@Override
	public int updateMessageOpenReceive(String nickname, Integer mid, String message_serial) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mid", mid);
		paramMap.put("nickname", nickname);		
		paramMap.put("message_serial", message_serial);
		
		return sqlsession.update("message.updateMessageOpenReceive", paramMap);
	}

	
	@Override
	public int deleteMessageSend(Integer mid) throws Exception {
		
		return sqlsession.delete("message.deleteMessageSend", mid);
	}
	
	@Override
	public int deleteMessageReceive(Integer mid) throws Exception {
		return sqlsession.delete("message.deleteMessageReceive", mid);
	}
	
	//delete Checked Message
	@Override
	public void deleteCheckedMessageReceive(MessageVO messageVO) throws Exception {
		sqlsession.delete("message.deleteCheckedMessageReceive", messageVO);
	}
	
	@Override
	public void deleteCheckedMessageSend(MessageVO messageVO) throws Exception {
		sqlsession.delete("message.deleteCheckedMessageSend", messageVO);
	}
	
	@Override
	public String getReceiveSerialKey(Integer mid) throws Exception {
		return sqlsession.selectOne("message.getReceiveSerialKey", mid);
	}
	
	@Override
	public String getSendSerialKey(Integer mid) throws Exception {
		return sqlsession.selectOne("message.getReceiveSerialKey", mid);
	}
	
	@Override
	public String getUserIdFromNickname(String nickname) throws Exception {
		return sqlsession.selectOne("message.getUserIdFromNickname", nickname);
	}
	
	
}
