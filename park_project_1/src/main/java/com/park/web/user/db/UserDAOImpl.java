package com.park.web.user.db;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.park.web.login.db.LoginDTO;

@Repository
public class UserDAOImpl implements UserDAO{
	
	@Inject
	private SqlSession sqlsession;
	
	
	
	@Override
	public UserVO login(LoginDTO loginDTO) throws Exception {
		return sqlsession.selectOne("user.login", loginDTO);
	}
	
	@Override
	public UserVO selectUserById(String id) throws Exception {
		return sqlsession.selectOne("user.selectUserById", id);
	}
	
	@Override
	public UserVO selectUserByNickName(String nickname) throws Exception {
		return sqlsession.selectOne("user.selectUserByNickName", nickname);
	}
	
	@Override
	public void loginSession(String id, String sessionId, Date expired) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("session_key", sessionId);
		paramMap.put("session_limit", expired);
		
		sqlsession.update("user.loginSession", paramMap);
	}
	
	@Override
	public UserVO loginCheck(String loginCookie) throws Exception {
		return sqlsession.selectOne("user.loginCheck", loginCookie);
	}
	
	
	@Override
	public UserVO getBySns(UserVO snsUser) throws Exception {
		
		if(StringUtils.isNotEmpty(snsUser.getId())) {
			return sqlsession.selectOne("user.getByNaver", snsUser.getId());
		}
		else{
			return sqlsession.selectOne("user.getByGoogle", snsUser.getId());
		}
		
	}
	
	@Override
	public UserVO getByGoogle(UserVO snsUser) throws Exception {
		return sqlsession.selectOne("user.getByGoogle", snsUser.getId());
	}
	
	
	@Override
	public UserVO getByKakao(UserVO snsUser) throws Exception {
		
		return sqlsession.selectOne("user.getByKakao", snsUser.getId());
		
	}
	
	
	//로그인 시도(계정 잠금)
	@Override
	public void loginSuccessReset(String id) throws Exception {
		sqlsession.update("user.loginSuccessReset", id);
	}
	
	@Override
	public void loginFailCnt(String id) throws Exception {
		sqlsession.update("user.loginFailCnt", id);
	}
	
	@Override
	public void updateLoginAccountLock(String id) throws Exception {
		sqlsession.update("user.updateLoginAccountLock", id);
	}
	
	@Override
	public String isAccountLock(String id) throws Exception {
		return sqlsession.selectOne("user.isAccountLock", id);
	}
	
	@Override
	public void loginSuccessResetAfter(String id) throws Exception {
		sqlsession.update("user.loginSuccessResetAfter", id);
	}
	
}
