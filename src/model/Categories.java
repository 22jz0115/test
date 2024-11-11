package model;

import java.time.LocalDateTime;

public class Categories {
	private int id;
	private String categoryName;
	private LocalDateTime createdAt;
	private LocalDateTime updateDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
	
	public Categories(int id, String categoryName,  LocalDateTime createdAt, LocalDateTime updateDate) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.createdAt = createdAt;
		this.updateDate = updateDate;
	}
	
}
