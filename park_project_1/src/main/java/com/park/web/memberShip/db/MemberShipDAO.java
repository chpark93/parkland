package com.park.web.memberShip.db;

import java.util.List;

import com.park.web.common.Criteria;
import com.park.web.login.db.LoginDTO;
import com.park.web.user.db.UserVO;

public interface MemberShipDAO {
	
	public int checkId(String id) throws Exception;
	
	public int checkEmail(String email) throws Exception;

	public int checkNickName(String nickname) throws Exception;
	
	public UserVO getUserInfo(String id) throws Exception;
	
	public void memberRegister(MemberShipVO memberShipVO) throws Exception;
	
	public void memberRegisterSns(MemberShipSnsVO memberShipSnsVO) throws Exception;

	public void memberApproval(MemberShipVO memberShipVO) throws Exception;
	
	public int approvalStatus(MemberShipVO memberShipVO) throws Exception;
	
	public int approvalStatusSns(MemberShipSnsVO memberShipSnsVO) throws Exception;
	
	public MemberShipVO findByEmail(MemberShipVO memberShipVO) throws Exception;
	
	public void findPw(MemberShipVO memberShipVO) throws Exception;
	
	
	public void updateUserInfo(UserVO userVO) throws Exception;
	
	public void updateUserPw(MemberShipVO memberShipVO) throws Exception;
	
	public void deleteMemberShip(UserVO userVO) throws Exception;
	
	public boolean passwordCheck(LoginDTO loginDTO) throws Exception;
	
	//회원 추가 정보
	public void insertMemberShipDetail(MemberShipDetailVO memberShipDetailVO) throws Exception;
	
	public void updateMemberShipDetail(UserVO userVO) throws Exception;
	
	public void updateUserProfile(UserVO userVO) throws Exception;
	
	public String getUserProfile(String id) throws Exception;
	
	public void deleteUserDetailProfile(String id) throws Exception;

	
	public String getUserProfileNickName(String nickname) throws Exception;
	









	
	
}
