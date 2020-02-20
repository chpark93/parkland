package com.park.web.memberShip.service;

import javax.servlet.http.HttpServletResponse;

import com.park.web.login.db.LoginDTO;
import com.park.web.memberShip.db.MemberShipDetailVO;
import com.park.web.memberShip.db.MemberShipSnsVO;
import com.park.web.memberShip.db.MemberShipVO;
import com.park.web.user.db.UserVO;

public interface MemberShipService {
	
	public boolean checkId(String id) throws Exception;
	
	public boolean checkEmail(String email) throws Exception;
	
	public boolean checkNickName(String nickname) throws Exception;
	
	public UserVO getUserInfo(String id) throws Exception;

	public void register(MemberShipVO memberShipVO, HttpServletResponse response) throws Exception;
	
	public void registerSNS(MemberShipSnsVO memberShipSnsVO) throws Exception;
		
	public void sendMail(MemberShipVO memberShipVO, String div) throws Exception;
	
	public void approvalStatus(MemberShipVO memberShipVO) throws Exception;
	
	
	public MemberShipVO findByEmail(MemberShipVO memberShipVO) throws Exception;
	
	public void findPw(MemberShipVO memberShipVO, HttpServletResponse response) throws Exception;
	
	public MemberShipVO execute(MemberShipVO memberShipVO) throws Exception;
	
	public void updateUserInfo(UserVO userVO) throws Exception;

	public void updateUserPw(MemberShipVO memberShipVO) throws Exception;
	
	public void deleteMemberShip(UserVO userVO) throws Exception;
	
	public boolean passwordCheck(LoginDTO loginDTO) throws Exception;
	
	
	public void registerDetail(MemberShipDetailVO memberShipDetailVO) throws Exception;

	public void updateMemberShipDetail(UserVO userVO) throws Exception;
	
	public void updateUserProfile(UserVO userVO) throws Exception;
	
	public String getUserProfile(String id) throws Exception;

	
	public String getUserProfileNickName(String nickname) throws Exception;











	
}
 