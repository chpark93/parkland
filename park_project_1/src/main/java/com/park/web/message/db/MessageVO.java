package com.park.web.message.db;

import java.util.Date;

import com.park.web.board.util.StringUtil;


public class MessageVO {
	
	private Integer mid;
	private String message_sender;
	private String message_receiver;
	private String message_content;
	private String message_open;
	private String message_serial;
	private String send_dt;
	
	
	
	//message_content list view 글자수 제한
	public String getShortMessageContent(Integer len) {
	    return StringUtil.getShortString(message_content, len);
	}
	
	
	
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public String getMessage_sender() {
		return message_sender;
	}
	public void setMessage_sender(String message_sender) {
		this.message_sender = message_sender;
	}
	public String getMessage_receiver() {
		return message_receiver;
	}
	public void setMessage_receiver(String message_receiver) {
		this.message_receiver = message_receiver;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public String getSend_dt() {
		return send_dt;
	}
	public void setSend_dt(String send_dt) {
		this.send_dt = send_dt;
	}
	public String getMessage_open() {
		return message_open;
	}
	public void setMessage_open(String message_open) {
		this.message_open = message_open;
	}



	public String getMessage_serial() {
		return message_serial;
	}



	public void setMessage_serial(String message_serial) {
		this.message_serial = message_serial;
	}
	
	
}
