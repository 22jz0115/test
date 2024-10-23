package model;

import java.time.LocalDateTime;

public class Presets {
	private int id;
	private int taskId;
	private int categoryid;
	private int accountId;
	private String presetName;
	private LocalDateTime createAt;
	private LocalDateTime updateDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTasksId() {
		return taskId;
	}
	public void setTasksId(int tasksId) {
		this.taskId = tasksId;
	}
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getPresetName() {
		return presetName;
	}
	public void setPresetName(String presetName) {
		this.presetName = presetName;
	}
	public LocalDateTime getCreateAt() {
		return createAt;
	}
	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}
	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
	public Presets(int id, int taskId, int categoryid, int accountId, String presetName, LocalDateTime createAt,
			LocalDateTime updateDate) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.categoryid = categoryid;
		this.accountId = accountId;
		this.presetName = presetName;
		this.createAt = createAt;
		this.updateDate = updateDate;
	}
	
}
