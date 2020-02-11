package com.park.web.user.db;

import java.util.Date;

import com.park.web.login.db.LoginDTO;

public interface UserDAO {

	public UserVO login(LoginDTO loginDTO) throws Exception;

	public void loginSession(String id, String sessionId, Date expired) throws Exception;

	public UserVO loginCheck(String loginCookie) throws Exception;

	public UserVO selectUserByNickName(String nickname) throws Exception;

	public UserVO selectUserById(String id) throws Exception;

	public UserVO getBySns(UserVO snsUser) throws Exception;

	public UserVO getByKakao(UserVO snsUser) throws Exception;

	public UserVO getByGoogle(UserVO snsUser) throws Exception;

	
	
	public void loginSuccessReset(String id) throws Exception;

	public void loginFailCnt(String id) throws Exception;

	public void updateLoginAccountLock(String id) throws Exception;

	public String isAccountLock(String id) throws Exception;

	public void loginSuccessResetAfter(String id) throws Exception;

	


}
