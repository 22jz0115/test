package model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class PresetTasks {
	private int id;
	private int presetId;
    private int categoryId;
    private String name;
    private LocalTime taskTime;
    private String memo;
    private int outin;
    private LocalDateTime createAt;
    private LocalDateTime updateDate;
	public PresetTasks(int id, int presetId, int categoryId, String name, LocalTime taskTime, String memo,
			int outin, LocalDateTime createAt, LocalDateTime updateDate) {
		this.id = id;
		this.presetId = presetId;
		this.categoryId = categoryId;
		this.name = name;
		this.taskTime = taskTime;
		this.memo = memo;
		this.outin = outin;
		this.createAt = createAt;
		this.updateDate = updateDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPresetId() {
		return presetId;
	}
	public void setPresetId(int presetId) {
		this.presetId = presetId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalTime getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(LocalTime taskTime) {
		this.taskTime = taskTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getOutin() {
		return outin;
	}
	public void setOutin(int outin) {
		this.outin = outin;
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
    
    
}
