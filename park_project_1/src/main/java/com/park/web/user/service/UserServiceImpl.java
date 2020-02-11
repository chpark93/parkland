package com.park.web.user.service;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.park.web.exception.NotMatchedLoginException;
import com.park.web.login.db.LoginDTO;
import com.park.web.user.db.UserDAO;
import com.park.web.user.db.UserVO;

@Service
public class UserServiceImpl implements UserService{
	
	@Inject
	private UserDAO usermanager;
	

	
	@Override
	public UserVO login(LoginDTO loginDTO) throws Exception {
		
		return usermanager.login(loginDTO);
		
	}
	
	@Override
	public void loginSession(String id, String sessionId, Date expired) throws Exception {
		usermanager.loginSession(id, sessionId, expired);
	}
	
	@Override
	public UserVO userAuth(LoginDTO loginDTO) throws Exception {
		UserVO user = usermanager.selectUserById(loginDTO.getId());
		
		if(user == null) {
			throw new NotMatchedLoginException();
		}
		if(!user.matchPw(loginDTO.getPw())) {
			throw new NotMatchedLoginException();
		}
		
		return user;
	}
	
	@Override
	public UserVO loginCheck(String loginCookie) throws Exception {
		return usermanager.loginCheck(loginCookie);
	}
	
	@Override
	public UserVO getBySns(UserVO snsUser) throws Exception {
		return usermanager.getBySns(snsUser);
	}
	
	@Override
	public UserVO getByGoogle(UserVO snsUser) throws Exception {
		return usermanager.getByGoogle(snsUser);
	}
	
	
	@Override
	public UserVO getByKakao(UserVO snsUser) throws Exception {
		return usermanager.getByKakao(snsUser);
	}
	
	
	//로그인 시도(계정 잠금)
	@Override
	public void loginSuccessReset(String id) throws Exception {
		usermanager.loginSuccessReset(id);
	}
	
	@Override
	public void loginFailCnt(String id) throws Exception {
		usermanager.loginFailCnt(id);
	}
	
	@Override
	public void updateLoginAccountLock(String id) throws Exception {
		usermanager.updateLoginAccountLock(id);
	}
	
	@Override
	public String isAccountLock(String id) throws Exception {
		return usermanager.isAccountLock(id);
	}
	
	@Override
	public void loginSuccessResetAfter(String id) throws Exception {
		usermanager.loginSuccessResetAfter(id);
	}
	
}
