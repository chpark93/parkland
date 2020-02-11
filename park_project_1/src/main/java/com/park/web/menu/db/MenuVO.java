package com.park.web.menu.db;

public class MenuVO {
	
	
	private Integer m_id;
	private String bg_no;
	private String bg_name;
	private String upper_bg_name;
	private String bg_info;
	private String reg_id;
	private String reg_dt;
	
	
	
	
	
	public Integer getM_id() {
		return m_id;
	}
	public void setM_id(Integer m_id) {
		this.m_id = m_id;
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
	public String getUpper_bg_name() {
		return upper_bg_name;
	}
	public void setUpper_bg_name(String upper_bg_name) {
		this.upper_bg_name = upper_bg_name;
	}
	public String getBg_info() {
		return bg_info;
	}
	public void setBg_info(String bg_info) {
		this.bg_info = bg_info;
	}
	
	
}
