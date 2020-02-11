package com.park.web.admin.db;

import java.util.List;

import com.park.web.common.SearchCriteria;
import com.park.web.user.db.UserVO;

public interface AdminDAO {

	List<UserVO> selectUserList(SearchCriteria searchCriteria) throws Exception;

	int selectUserListCnt(SearchCriteria searchCriteria) throws Exception;

	UserVO getUserInfo(String id) throws Exception;

	void ejectUserAdmin(String id) throws Exception;

	void userSuspend(UserVO userVO) throws Exception;

	String isAccountSuspend(String id) throws Exception;

	void userSuspendClear(UserVO userVO) throws Exception;

	void userSuspendClearAdmin(String id) throws Exception;

	void updateUserSuspend(UserVO userVO) throws Exception;

	UserVO getSuspendUserInfo(String id) throws Exception;


}
