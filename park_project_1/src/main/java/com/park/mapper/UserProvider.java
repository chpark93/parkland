package com.park.mapper;

import java.util.Map;

public class UserProvider {
	
	public static String searchUser(Map<String, Object> map) {
		if(map.get("searchCol").equals("id")) {
			return "SELECT * FROM tbl_user WHERE id = #{searchStr}";
		}
		else if(map.get("searchCol").equals("name")) {
			return "SELECT * FROM tbl_user WHERE name LIKE CONCAT('%', #{searchStr}, '%')";
		}
		else {
			return "SELECT * FROM tbl_user";
		}
	}
}
