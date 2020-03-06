package com.park.web.board.db;

import com.park.web.board.util.StringUtil;

public class ReplyVO {
	
	private int rid; 				//��� ��ȣ
	private int bid; 				//�Խñ� ��ȣ
	private Integer rparent_id; 	//���� �θ�
	private Integer rdepth;			//���� ����
	private Integer rorder;			//���� ����
	private Integer rgroup_id;		//���� �׷�
	private String rcontent;		//��� ����
	private String reg_id;			//��� �ۼ���
	private String reg_nickname;	//��� �г���
	private String replytarget_id;	//���� Ÿ��
	private String reg_dt;  		//��� �ۼ���
	private String edit_dt;			//��� ������
	private String title;			//�Խñ� ����
	
	
	
	//��� ���� ���� �� ����
	public String getShortRcontent(Integer len) {
	    return StringUtil.getShortString(rcontent, len);
	}
	
	//���� ���� �� ����
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
