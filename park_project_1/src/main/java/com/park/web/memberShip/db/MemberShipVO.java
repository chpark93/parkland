package com.park.web.memberShip.db;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Entity;

@Entity
public class MemberShipVO {
	

	private int idx; 
	private int approval_idx;
	
	@NotBlank(message="¾ÆÀÌµğ¸¦ ÇÊ¼ö·Î ÀÔ·ÂÇØÁÖ¼¼¿ä.")
	@Size(min=4, max=25, message="¾ÆÀÌµğ´Â 4~25ÀÚ·Î ÀÔ·ÂÇØÁÖ¼¼¿ä.")
	@Pattern(regexp=".*(?=.{4,})(?=.*[\\d[a-z][A-Z]]{4,}).*",
			message="4ÀÚ¸® ÀÌ»óÀÇ ¿µ¹®°ú ¼ıÀÚ¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.")
	private String id;
	
	@NotBlank(message="ÀÌ¸ŞÀÏÀ» ÇÊ¼ö·Î ÀÔ·ÂÇØÁÖ¼¼¿ä.")
	@Pattern(regexp="\\w+[@]\\w+\\.\\w+", message="ÀÌ¸ŞÀÏ Çü½Ä¿¡ ¸ÂÁö ¾Ê½À´Ï´Ù.")
	private String email;
	
	@NotBlank(message="ÀÌ¸§À» ÇÊ¼ö·Î ÀÔ·ÂÇØÁÖ¼¼¿ä.")
	@Pattern(regexp="^[°¡-ÆRa-zA-Z]*$", message="¼ıÀÚ³ª Æ¯¼ö¹®ÀÚ´Â ÀÔ·ÂÇÒ ¼ö ¾ø½À´Ï´Ù.")
	private String name;
	
	@NotBlank(message="´Ğ³×ÀÓÀ» ÀÔ·Â ÇØÁÖ¼¼¿ä.")
	private String nickname;
	
	@NotNull(message="ºñ¹Ğ¹øÈ£¸¦ ÇÊ¼ö·Î ÀÔ·ÂÇØÁÖ¼¼¿ä.")
	@Size(min=4, max=20, message="ºñ¹Ğ¹øÈ£´Â 4ÀÚ ÀÌ»ó ÀÔ·ÂÇØÁÖ¼Å¾ß ÇÕ´Ï´Ù.")
	private String password;
	
	private String passwordChk;
	private String oldPassword;
	
	private String approval_key;
	private String approval_status;
	private Date reg_dt;
	private int unReadCount;
	private String member_section;
	
	//Ãß°¡ Á¤º¸
	@Pattern(regexp="/^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/", message="Á¦´ë·Î µÈ ¹øÈ£¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.")
	private String mobile;
	private String birth;
	private String sex;
	private String profileImg;
	
	

	@SuppressWarnings("unused")
	public MemberShipVO() {}

	public MemberShipVO(String id, String email, String name, String password, String mobile) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.mobile = mobile;
	}



	//ºñ¹Ğ¹øÈ£ Ã¼Å©
	public boolean checkPassword() {
		return password.equals(passwordChk);
	}
	
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordChk() {
		return passwordChk;
	}
	public void setPasswordChk(String passwordChk) {
		this.passwordChk = passwordChk;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(Date reg_dt) {
		this.reg_dt = reg_dt;
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

	public int getApproval_idx() {
		return approval_idx;
	}
	
	public void setApproval_idx(int approval_idx) {
		this.approval_idx = approval_idx;
	}

	public int getUnReadCount() {
		return unReadCount;
	}

	public void setUnReadCount(int unReadCount) {
		this.unReadCount = unReadCount;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
}
