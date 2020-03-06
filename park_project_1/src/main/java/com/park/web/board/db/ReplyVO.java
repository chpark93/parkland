package com.park.web.board.db;

import com.park.web.board.util.StringUtil;

public class ReplyVO {
	
	private int rid; 				//댓글 번호
	private int bid; 				//게시글 번호
	private Integer rparent_id; 	//대댓글 부모
	private Integer rdepth;			//대댓글 뎁스
	private Integer rorder;			//대댓글 오더
	private Integer rgroup_id;		//대댓글 그룹
	private String rcontent;		//댓글 내용
	private String reg_id;			//댓글 작성자
	private String reg_nickname;	//댓글 닉네임
	private String replytarget_id;	//대댓글 타겟
	private String reg_dt;  		//댓글 작성일
	private String edit_dt;			//댓글 수정일
	private String title;			//게시글 제목
	
	
	
	//댓글 내용 글자 수 제한
	public String getShortRcontent(Integer len) {
	    return StringUtil.getShortString(rcontent, len);
	}
	
	//제목 글자 수 제한
	public String getShortTitle(Integer len) {
	    return StringUtil.getShortString(title, len);
	}
	
	
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getRcontent() {
		return rcontent;
	}
	public void setRcontent(String rcontent) {
		this.rcontent = rcontent;
	}
	public String getReg_id() {
		return reg_id;
	}
	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
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
	public Integer getRparent_id() {
		return rparent_id;
	}
	public void setRparent_id(Integer rparent_id) {
		this.rparent_id = rparent_id;
	}
	public Integer getRdepth() {
		return rdepth;
	}
	public void setRdepth(Integer rdepth) {
		this.rdepth = rdepth;
	}
	public Integer getRorder() {
		return rorder;
	}
	public void setRorder(Integer rorder) {
		this.rorder = rorder;
	}
	public Integer getRgroup_id() {
		return rgroup_id;
	}
	public void setRgroup_id(Integer rgroup_id) {
		this.rgroup_id = rgroup_id;
	}
	public String getReplytarget_id() {
		return replytarget_id;
	}
	public void setReplytarget_id(String replytarget_id) {
		this.replytarget_id = replytarget_id;
	}
	public String getReg_nickname() {
		return reg_nickname;
	}
	public void setReg_nickname(String reg_nickname) {
		this.reg_nickname = reg_nickname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	


}
