package model;

import java.util.Date;

public class Member {
	
	private String id; // primary key, �ڵ������� �ƴ϶� ����ڰ� �Է�
	private String pw; // ����� ��й�ȣ
	private String name; // ����� �̸� (�г���)
	private String email; // ����� �̸��� ( �ߺ� �Ұ� )
	private Date regDate; // ȸ������ ��¥ (�⺻�� : ���Խ� �ð�, sysdate)
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
