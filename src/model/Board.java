package model;

import java.util.Date;

public class Board {
	
	private int id;// primary key, 시퀀스를 통해 자동으로 1씩 증가하는 값
	private String name; // 게시글 작성자 이름
	private String title; // 게시글 제목
	private String content; // 게시글 내용
	private Date createdTime; // 게시글 생성 시간
	
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
