package com.park.web.memberShip.db;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.park.web.login.db.LoginDTO;
import com.park.web.user.db.UserVO;

@Repository
public class MemberShipDAOImpl implements MemberShipDAO{
	
	@Inject
	private SqlSession sqlsession;
	
	
	@Override
	public int checkId(String id) throws Exception {
		return sqlsession.selectOne("memberShip.checkId", id);
	}
	
	@Override
	public int checkEmail(String email) throws Exception {
		return sqlsession.selectOne("memberShip.checkEmail", email);
	}
	
	@Override
	public int checkNickName(String nickname) throws Exception {
		return sqlsession.selectOne("memberShip.checkNickName", nickname);
	}
	
	
	@Override
	public UserVO getUserInfo(String id) throws Exception {
		return sqlsession.selectOne("memberShip.getUserInfo", id);
	}

	
	@Override
	public void memberRegister(MemberShipVO memberShipVO) throws Exception {
		sqlsession.insert("memberShip.memberRegister", memberShipVO);
		
	}
	
	@Override
	public void memberRegisterSns(MemberShipSnsVO memberShipSnsVO) throws Exception {
		sqlsession.insert("memberShip.memberRegisterSns", memberShipSnsVO);
	}

	@Override
	public void memberApproval(MemberShipVO memberShipVO) throws Exception {
		sqlsession.insert("memberShip.memberApproval", memberShipVO);
		
	}

	@Override
	public int approvalStatus(MemberShipVO memberShipVO) throws Exception {
		return sqlsession.update("memberShip.approvalStatus", memberShipVO);

	}
	
	@Override
	public int approvalStatusSns(MemberShipSnsVO memberShipSnsVO) throws Exception {
		return sqlsession.insert("memberShip.approvalStatusSns", memberShipSnsVO);
	}
	
	//회원 정보 찾기
	@Override
	public MemberShipVO findByEmail(MemberShipVO memberShipVO) throws Exception {
		return sqlsession.selectOne("memberShip.findByEmail", memberShipVO);
	}
	
	@Override
	public void findPw(MemberShipVO memberShipVO) throws Exception {
		sqlsession.update("memberShip.findPw", memberShipVO);
	}
	
	
	//회원 정보 변경
	@Override
	public void updateUserInfo(UserVO userVO) throws Exception {
		sqlsession.update("memberShip.updateUserInfo", userVO);
	}
	
	//비밀번호 변경
	@Override
	public void updateUserPw(MemberShipVO memberShipVO) throws Exception {
		sqlsession.update("memberShip.updateUserPw", memberShipVO);
	}
	
	//회원 탈퇴
	@Override
	public void deleteMemberShip(UserVO userVO) throws Exception {
		sqlsession.delete("memberShip.deleteMemberShip", userVO);
	}
	
	@Override
	public boolean passwordCheck(LoginDTO loginDTO) throws Exception {
		return sqlsession.selectOne("memberShip.passwordCheck", loginDTO);
	}
	
	
	
	/* MemberShip Detail */
	
	//회원 추가 정보
	@Override
	public void insertMemberShipDetail(MemberShipDetailVO memberShipDetailVO) throws Exception {
		sqlsession.insert("memberShip.insertMemberShipDetail", memberShipDetailVO);
	}
		
	//회원 정보 수정(마이 페이지)
	@Override
	public void updateMemberShipDetail(UserVO userVO) throws Exception {
		sqlsession.update("memberShip.updateMemberShipDetail", userVO);
	}
	
	@Override
	public void updateUserProfile(UserVO userVO) throws Exception {
		sqlsession.update("memberShip.updateUserProfile", userVO);
	}
	
	@Override
	public String getUserProfile(String id) throws Exception {
		return sqlsession.selectOne("memberShip.getUserProfile", id);
	}

	@Override
	public String getUserProfileNickName(String nickname) throws Exception {
		return sqlsession.selectOne("memberShip.getUserProfileNickName", nickname);
	}
	
	@Override
	public void deleteUserDetailProfile(String id) throws Exception {
		sqlsession.update("memberShip.deleteUserDetailProfile", id);
	}
	




	

}
