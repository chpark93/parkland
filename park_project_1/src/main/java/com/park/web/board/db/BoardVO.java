package com.park.web.board.db;

import com.park.web.board.util.StringUtil;

public class BoardVO {

	private Integer bid; //�۹�ȣ
	private String title; //����
	private String content; //�� ����
	private int view_cnt;
	private int reply_view_cnt;
	private int recommend_cnt;
	private String reg_id; //�ۼ��� ���̵�
	private String reg_nickname; //�ۼ��� �г���
	private String reg_dt;
	private String edit_dt;	
	private String bg_no; //�Խ��� �׷� ��ȣ(���� �Խ���)
	private String bg_name; //�Խ��� �׷� �̸�
	
	/* ÷������ */
	private String[] files;
	private int file_cnt;
	
	//Ÿ��Ʋ ���� �� ����
	public String getShortTitle(Integer len) {
	    return StringUtil.getShortString(title, len);
	}
	
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getView_cnt() {
		return view_cnt;
	}
	public void setView_cnt(int view_cnt) {
		this.view_cnt = view_cnt;
	}
	public int getReply_view_cnt() {
		return reply_view_cnt;
	}

	public void setReply_view_cnt(int reply_view_cnt) {
		this.reply_view_cnt = reply_view_cnt;
	}

	public String getReg_id() {
		return reg_id;
	}
	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}
	public String getReg_nickname() {
		return reg_nickname;
	}

	public void setReg_nickname(String reg_nickname) {
		this.reg_nickname = reg_nickname;
	}

	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	public String getEdit_dt() {
		return edit_dt;
	}
	public void setEdit_dt(String edit_dt) {
		this.edit_dt = edit_dt;
	}
	public String getBg_no() {
		return bg_no;
	}
	public void setBg_no(String bg_no) {
		this.bg_no = bg_no;
	}
	

	public int getFile_cnt() {
		return file_cnt;
	}

	public void setFile_cnt(int file_cnt) {
		this.file_cnt = file_cnt;
	}

	public String[] getFiles() {
		return files;
	}

	public void setFiles(String[] files) {
		this.files = files;
		//�ܺο��� ȣ���Ͽ� ÷������ ������ �־����� �ʾƵ� �ȴ�.
		setFile_cnt(files.length);
	}

	public int getRecommend_cnt() {
		return recommend_cnt;
	}

	public void setRecommend_cnt(int recommend_cnt) {
		this.recommend_cnt = recommend_cnt;
	}

	public String getBg_name() {
		return bg_name;
	}

	public void setBg_name(String bg_name) {
		this.bg_name = bg_name;
	}




	


}
