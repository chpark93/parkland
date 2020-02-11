package com.park.web.admin.db;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.park.web.common.SearchCriteria;
import com.park.web.user.db.UserVO;

@Repository
public class AdminDAOImpl implements AdminDAO{
	
	@Inject
	private SqlSession sqlsession;

	
	@Override
	public List<UserVO> selectUserList(SearchCriteria searchCriteria) throws Exception {
		return sqlsession.selectList("admin.selectUserList", searchCriteria);
	}

	@Override
	public int selectUserListCnt(SearchCriteria searchCriteria) throws Exception {
		return sqlsession.selectOne("admin.selectUserListCnt", searchCriteria);
	}
	
	//회원 정보 
	@Override
	public UserVO getUserInfo(String id) throws Exception {
		return sqlsession.selectOne("admin.getUserInfo", id);
	}
	
	@Override
	public UserVO getSuspendUserInfo(String id) throws Exception {
		return sqlsession.selectOne("admin.getSuspendUserInfo", id);
	}
	
	@Override
	public void ejectUserAdmin(String id) throws Exception {
		sqlsession.delete("admin.ejectUserAdmin", id);
	}
	
	//회원 정지
	@Override
	public void userSuspend(UserVO userVO) throws Exception {
		sqlsession.insert("admin.userSuspend", userVO);
	}
	
	@Override
	public void updateUserSuspend(UserVO userVO) throws Exception {
		sqlsession.update("admin.updateUserSuspend", userVO);
	}
	
	@Override
	public String isAccountSuspend(String id) throws Exception {
		return sqlsession.selectOne("admin.isAccountSuspend", id);
	}
	
	@Override
	public void userSuspendClear(UserVO userVO) throws Exception {
		sqlsession.delete("admin.userSuspendClear", userVO);
	}
	
	@Override
	public void userSuspendClearAdmin(String id) throws Exception {
		sqlsession.delete("admin.userSuspendClearAdmin", id);
	}
	
	
}
