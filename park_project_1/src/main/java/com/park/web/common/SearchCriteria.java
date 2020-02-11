package com.park.web.common;

public class SearchCriteria extends Criteria{
	
	private String keyword;
	private String searchType;
	private String bg_no;
	private String bg_name;
	private String loginUser;
	private String nickname;
	private String BoardSection;
	
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getBg_no() {
		return bg_no;
	}
	public void setBg_no(String bg_no) {
		this.bg_no = bg_no;
	}
	public String getBg_name() {
		return bg_name;
	}
	public void setBg_name(String bg_name) {
		this.bg_name = bg_name;
	}
	public String getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getBoardSection() {
		return BoardSection;
	}
	public void setBoardSection(String boardSection) {
		BoardSection = boardSection;
	}
	

	
	
	
}
