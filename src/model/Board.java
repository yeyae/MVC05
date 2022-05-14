package model;

import java.util.Date;

public class Board {
	
	private int id;// primary key, �������� ���� �ڵ����� 1�� �����ϴ� ��
	private String name; // �Խñ� �ۼ��� �̸�
	private String title; // �Խñ� ����
	private String content; // �Խñ� ����
	private Date createdTime; // �Խñ� ���� �ð�
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	@Override
	public String toString() {
		return "Board [id=" + id + ", name=" + name + ", title=" + title + ", content=" + content + ", createdTime="
				+ createdTime + "]";
	}
	
}
