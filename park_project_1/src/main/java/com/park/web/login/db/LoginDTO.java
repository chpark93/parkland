package com.park.web.login.db;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

//Hiberante
//null 체크

@Entity
public class LoginDTO {
	
	@NotEmpty(message="아이디를 입력 해주세요.")
	private String id;
	
	@NotEmpty(message="비밀번호를 입력 해주세요.")
	private String pw;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	
}
