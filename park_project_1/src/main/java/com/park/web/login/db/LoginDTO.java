package com.park.web.login.db;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

//Hiberante
//null üũ

@Entity
public class LoginDTO {
	
	@NotEmpty(message="���̵� �Է� ���ּ���.")
	private String id;
	
	@NotEmpty(message="��й�ȣ�� �Է� ���ּ���.")
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
