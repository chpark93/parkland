package com.park.web.memberShip.db;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Entity;

@Entity
public class MemberShipSnsVO {
	
	private int idx; 
	private int approval_idx;
	
	@NotBlank
	@Size(min=4, max=25, message="아이디는 4 ~ 25자로 입력해주세요.")
	@Pattern(regexp=".*(?=.{4,})(?=.*[\\d[a-z][A-Z]]{4,}).*",
			message="4자리 이상의 영문과 숫자를 입력해주세요.")
	private String id;
	
	private String email;
	
	@NotBlank(message="이름을 필수로 입력해주세요.")
	private String name;
	
	@NotBlank(message="닉네임을 입력 해주세요.")
	private String nickname;
	
	private String approval_key;
	private String approval_status;
	private Date reg_dt;
	
	private String member_section;
	
	//추가 정보
	private String mobile;
	private String birth;
	private String sex;
	private String profileImg;
	
	
	
	
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getApproval_idx() {
		return approval_idx;
	}
	public void setApproval_idx(int approval_idx) {
		this.approval_idx = approval_idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getApproval_key() {
		return approval_key;
	}
	public void setApproval_key(String approval_key) {
		this.approval_key = approval_key;
	}
	public String getApproval_status() {
		return approval_status;
	}
	public void setApproval_status(String approval_status) {
		this.approval_status = approval_status;
	}
	public Date getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(Date reg_dt) {
		this.reg_dt = reg_dt;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public String getMember_section() {
		return member_section;
	}
	public void setMember_section(String member_section) {
		this.member_section = member_section;
	}
}
