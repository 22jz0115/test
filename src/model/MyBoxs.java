package model;

import java.time.LocalDateTime;

public class MyBoxs {
	int id;
	int accountId;
	int collection;
    private LocalDateTime createdAt;
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
	public int getCollection() {
		return collection;
	}
	public void setCollection(int collection) {
		this.collection = collection;
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
	public MyBoxs(int id, int accountId, int collection, LocalDateTime createdAt, LocalDateTime updateDate) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.collection = collection;
		this.createdAt = createdAt;
		this.updateDate = updateDate;
	}
    
    
}
