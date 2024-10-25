package model;

import java.time.LocalDateTime;

public class Tasks {
	private int id;
	private int categoryId;
	private int accountId;
	private String taskName;
	private LocalDateTime taskDate;
	private String memo;
	private LocalDateTime createdAt;
	private LocalDateTime updateDate;
	private int outin;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public LocalDateTime getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(LocalDateTime taskDate) {
		this.taskDate = taskDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
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
	public int getOutin() {
		return outin;
	}
	public void setOutin(int outin) {
		this.outin = outin;
	}
	public Tasks(int id, int categoryId, int accountId, String taskName, LocalDateTime taskDate, String memo,
			LocalDateTime createdAt, LocalDateTime updateDate, int outin) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.accountId = accountId;
		this.taskName = taskName;
		this.taskDate = taskDate;
		this.memo = memo;
		this.createdAt = createdAt;
		this.updateDate = updateDate;
		this.outin = outin;
	}
	
}
