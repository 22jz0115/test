package model;

import java.time.LocalDateTime;

public class Lifes {
	private int id;
	private int accountId;
	private String title;
	private String img;
	private String content;
	private LocalDateTime postDate;
	private LocalDateTime creeatedAt;
	private LocalDateTime updateDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getPostDate() {
		return postDate;
	}
	public void setPostDate(LocalDateTime postDate) {
		this.postDate = postDate;
	}
	public LocalDateTime getCreeatedAt() {
		return creeatedAt;
	}
	public void setCreeatedAt(LocalDateTime creeatedAt) {
		this.creeatedAt = creeatedAt;
	}
	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
	public Lifes(int id, int accountId, String title, String img, String content, LocalDateTime postDate,
			LocalDateTime creeatedAt, LocalDateTime updateDate) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.title = title;
		this.img = img;
		this.content = content;
		this.postDate = postDate;
		this.creeatedAt = creeatedAt;
		this.updateDate = updateDate;
	}
	
}
