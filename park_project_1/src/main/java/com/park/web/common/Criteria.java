package com.park.web.common;

import org.springframework.web.util.UriComponentsBuilder;

public class Criteria {
	private Integer page; //현재 페이지
	private int listSize; //페이지 목록 개수
	
	private String searchType;
	private String keyword;



	public Criteria() {
		this.page = 1;
		this.listSize = 10;
	}

	public void setPage(Integer page) {

		if (page <= 0) {
			this.page = 1;
			return;
		}

		this.page = page;
	}

	public Integer getPage() {
		return page;
	}

	public void setListSize(int listSize) {

		if (listSize <= 0 || listSize > 100) {
			this.listSize = 10;
			return;
		}
	
		this.listSize = listSize;
	}

	public int getListSize() {
		return this.listSize;
	}
	
	//게시글 시작 위치
	public int getPageStart() {
		return (this.page - 1) * listSize;
	}
	
	
	//검색
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
	//검색 기능
	public String makeQuery() {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
					.queryParam("page", page)
					.queryParam("listSize", this.listSize);
		
		if(searchType != null) {
			uriComponentsBuilder.queryParam("searchType", searchType)
								.queryParam("keyword", keyword);
		}
		
		return uriComponentsBuilder.build().encode().toString();
	}
	
	
	
	
	@Override
	public String toString() {
		return "Criteria{" + "page=" + page + ", listSize=" + listSize + '}';
	}
}
