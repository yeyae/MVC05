package model;

import java.util.Date;

public class Member {
	
	private String id; // primary key, 자동생성이 아니라 사용자가 입력
	private String pw; // 사용자 비밀번호
	private String name; // 사용자 이름 (닉네임)
	private String email; // 사용자 이메일 ( 중복 불가 )
	private Date regDate; // 회원가입 날짜 (기본값 : 가입시 시간, sysdate)
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "Member [id=" + id + ", pw=" + pw + ", name=" + name + ", email=" + email + ", regDate=" + regDate + "]";
	}
	
}
