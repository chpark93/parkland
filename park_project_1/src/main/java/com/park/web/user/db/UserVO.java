package com.park.web.user.db;

import java.util.Date;

public class UserVO {
	
	private String id;
	private String password;
	private String email;
	private String name;
	private String nickname;
	private String authority;
	private String enabled;
	private String reg_dt;
	private Date access_date;
	private String member_section;
	
	//회원 정지
	private Integer login_fail_cnt;
	private Integer suspend_term;
	private String is_lock;
	private Date lock_dt;
	
	//추가 정보
	private String mobile;
	private String birth;
	private String sex;
	private String profileImg;

	private String[] files;
	
	
	
	
	//비밀번호 체크
	public boolean matchPw(String pw) {
		return this.password.equals(pw);
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public Date getAccess_date() {
		return access_date;
	}

	public void setAccess_date(Date access_date) {
		this.access_date = access_date;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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


	public String[] getFiles() {
		return files;
	}


	public void setFiles(String[] files) {
		this.files = files;
	}


	public Integer getLogin_fail_cnt() {
		return login_fail_cnt;
	}


	public void setLogin_fail_cnt(Integer login_fail_cnt) {
		this.login_fail_cnt = login_fail_cnt;
	}


	public String getIs_lock() {
		return is_lock;
	}


	public void setIs_lock(String is_lock) {
		this.is_lock = is_lock;
	}


	public Date getLock_dt() {
		return lock_dt;
	}


	public void setLock_dt(Date lock_dt) {
		this.lock_dt = lock_dt;
	}


	public String getReg_dt() {
		return reg_dt;
	}


	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}


	public Integer getSuspend_term() {
		return suspend_term;
	}


	public void setSuspend_term(Integer suspend_term) {
		this.suspend_term = suspend_term;
	}


}
