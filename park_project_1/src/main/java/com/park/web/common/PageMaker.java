package com.park.web.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {
	private int listCnt; //전체 게시글
    private int startPage; 	//시작 페이지 	
    private int endPage; 	//끝 페이지
    private boolean prevPage;	//이전 링크
    private boolean nextPage;	//다음 링크

    private int pageNum = 10; //페이지 번호 갯수
    
    private String bg_no; //게시판 카테고리

    private Criteria criteria;

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public void setListCnt(int listCnt) {
        this.listCnt = listCnt;
        pageInfo();
    }
    
    //페이징 계산
    private void pageInfo() {
    	
    	//끝 페이지 = (현재 페이지 / 페이지 번호 갯수) * 페이지 번호 갯수
        endPage = (int) (Math.ceil(criteria.getPage() / (double) pageNum) * pageNum);
        
        //시작 페이지 = (끝 페이지 - 페이지 번호 갯수) + 1 
        startPage = (endPage - pageNum) + 1;
        
        //끝 페이지 보정 계산
        // ( 전체 게시글  / 페이지 당 출력 할 게시글 갯수)
        int tempEndPage = (int) (Math.ceil(listCnt / (double) criteria.getListSize()));
        
        if (endPage > tempEndPage) {
            endPage = tempEndPage;
        }

        prevPage = startPage == 1 ? false : true;

        nextPage = endPage * criteria.getListSize() >= listCnt ? false : true;

    }
    
    
    public String makeQuery(int page) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("bg_no", bg_no)
        		.queryParam("page", page)
                .queryParam("listSize", criteria.getListSize())
                .build();

        return uriComponents.toUriString();
    }
    
    //검색 기능
    public String makeSearch(int page) {

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
        		.queryParam("bg_no", bg_no)
                .queryParam("page", page)
                .queryParam("listSize", criteria.getListSize())
                .queryParam("searchType", ((SearchCriteria) criteria).getSearchType())
                .queryParam("keyword", encoding(((SearchCriteria) criteria).getKeyword()))
                .build();

        return uriComponents.toUriString();
    }
    
    
    //bg_no 제외
    public String makeAllQuery(int page) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
        		.queryParam("page", page)
                .queryParam("listSize", criteria.getListSize())
                .build();

        return uriComponents.toUriString();
    }
    
    
    //전체 검색
    public String makeAllSearch(int page) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
        		.queryParam("page", page)
                .queryParam("listSize", criteria.getListSize())
                .queryParam("searchType", ((SearchCriteria) criteria).getSearchType())
                .queryParam("keyword", encoding(((SearchCriteria) criteria).getKeyword()))
                .build();

        return uriComponents.toUriString();
    }
    
    
    private String encoding(String keyword) {
        if (keyword == null || keyword.trim().length() == 0) {
            return "";
        }

        try {
            return URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
	

    public int getListCnt() {
        return listCnt;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isPrevPage() {
        return prevPage;
    }

    public void setPrev(boolean prevPage) {
        this.prevPage = prevPage;
    }

    public boolean isNextPage() {
        return nextPage;
    }

    public void setNextPage(boolean nextPage) {
        this.nextPage = nextPage;
    }

    public int getpageNum() {
        return pageNum;
    }

    public void setpageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public String getBg_no() {
		return bg_no;
	}

	public void setBg_no(String bg_no) {
		this.bg_no = bg_no;
	}

	@Override
    public String toString() {
        return "PageMaker{" +
                "listCnt=" + listCnt +
                ", startPage=" + startPage +
                ", endPage=" + endPage +
                ", prevPage=" + prevPage +
                ", nextPage=" + nextPage +
                ", pageNum=" + pageNum +
                ", criteria=" + criteria +
                '}';
    }
}
