package com.park.web.user.service;

import java.util.Date;

import com.park.web.login.db.LoginDTO;
import com.park.web.user.db.UserVO;

public interface UserService {

	UserVO login(LoginDTO loginDTO) throws Exception;

	void loginSession(String id, String sessionId, Date expired) throws Exception;

	UserVO loginCheck(String loginCookie) throws Exception;

	UserVO getBySns(UserVO snsUser) throws Exception;

	UserVO userAuth(LoginDTO loginDTO) throws Exception;

	UserVO getByKakao(UserVO snsUser) throws Exception;

	UserVO getByGoogle(UserVO snsUser) throws Exception;

	
	void loginSuccessReset(String id) throws Exception;

	void loginFailCnt(String id) throws Exception;

	void updateLoginAccountLock(String id) throws Exception;

	String isAccountLock(String id) throws Exception;

	void loginSuccessResetAfter(String id) throws Exception;

	

}
