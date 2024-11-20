package model;

import java.time.LocalDateTime;

public class MyBoxs {
	int id;
	int accountId;
	int collectionId;
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

	public int getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
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
	public MyBoxs(int id, int accountId, int collectionId, LocalDateTime createdAt, LocalDateTime updateDate) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.collectionId = collectionId;
		this.createdAt = createdAt;
		this.updateDate = updateDate;
	}

    
    
}
