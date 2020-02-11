package com.park.web.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {
	private int listCnt; //��ü �Խñ�
    private int startPage; 	//���� ������ 	
    private int endPage; 	//�� ������
    private boolean prevPage;	//���� ��ũ
    private boolean nextPage;	//���� ��ũ

    private int pageNum = 10; //������ ��ȣ ����
    
    private String bg_no; //�Խ��� ī�װ�

    private Criteria criteria;

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public void setListCnt(int listCnt) {
        this.listCnt = listCnt;
        pageInfo();
    }
    
    //����¡ ���
    private void pageInfo() {
    	
    	//�� ������ = (���� ������ / ������ ��ȣ ����) * ������ ��ȣ ����
        endPage = (int) (Math.ceil(criteria.getPage() / (double) pageNum) * pageNum);
        
        //���� ������ = (�� ������ - ������ ��ȣ ����) + 1 
        startPage = (endPage - pageNum) + 1;
        
        //�� ������ ���� ���
        // ( ��ü �Խñ�  / ������ �� ��� �� �Խñ� ����)
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
    
    //�˻� ���
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
    
    
    //bg_no ����
    public String makeAllQuery(int page) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
        		.queryParam("page", page)
                .queryParam("listSize", criteria.getListSize())
                .build();

        return uriComponents.toUriString();
    }
    
    
    //��ü �˻�
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
