package com.park.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.park.web.user.db.UserVO;

public interface UserMapper {


	@Select("SELECT now()")
	public String time() throws Exception;
	
	
	@Select("SELECT name FROM tbl_user WHERE id = #{id}")
	public String getName(@Param("id") String id) throws Exception;
	
	
	public UserVO loginInfo(@Param("id") String id) throws Exception;
	
	
	@Update("UPDATE tbl_user SET access_date = now() WHERE id = #{id}")
	public void updateLogin(@Param("id") String id) throws Exception;
	
	
	@SelectProvider(type=UserProvider.class, method="searchUser")
	public List<UserVO> searchUser(@Param("searchCol") String searchCol, @Param("searchStr") String searchStr) throws Exception;
}
