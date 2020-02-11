package com.park.web.admin.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.web.admin.db.AdminDAO;
import com.park.web.common.SearchCriteria;
import com.park.web.user.db.UserVO;

@Service
public class AdminServiceImpl implements AdminService {

	@Inject
	private AdminDAO amanager;
	
	
	@Override
	public List<UserVO> selectUserList(SearchCriteria searchCriteria) throws Exception {
		return amanager.selectUserList(searchCriteria);
	}
	@Override
	public int selectUserListCnt(SearchCriteria searchCriteria) throws Exception {
		return amanager.selectUserListCnt(searchCriteria);
	}
	
	
	//회원 정보
	@Override
	public UserVO getUserInfo(String id) throws Exception {
		return amanager.getUserInfo(id);
	}
	
	
	//회원 강제 탈퇴
	@Override
	public void ejectUserAdmin(String id) throws Exception {
		amanager.ejectUserAdmin(id);
	}
	
	@Override
	public UserVO getSuspendUserInfo(String id) throws Exception {
		return amanager.getSuspendUserInfo(id);
	}
	
	
	//회원 정지
	@Override
	public void userSuspend(UserVO userVO) throws Exception {
		amanager.userSuspend(userVO);
		
	}
	
	@Override
	public String isAccountSuspend(String id) throws Exception {
		return amanager.isAccountSuspend(id);
	}
	
	@Override
	public void userSuspendClear(UserVO userVO) throws Exception {
		amanager.userSuspendClear(userVO);
	}
	
	@Override
	public void userSuspendClearAdmin(String id) throws Exception {
		amanager.userSuspendClearAdmin(id);
	}
}
 