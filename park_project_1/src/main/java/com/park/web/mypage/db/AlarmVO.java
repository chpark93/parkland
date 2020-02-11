package com.park.web.mypage.db;

public class AlarmVO {
	
	private Integer aid;
	private String target_id; 
	private String nickname; 
	private String msg; 
	private String alert_dt;
	
	
	
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getAlert_dt() {
		return alert_dt;
	}
	public void setAlert_dt(String alert_dt) {
		this.alert_dt = alert_dt;
	}
	public String getTarget_id() {
		return target_id;
	}
	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}


}
