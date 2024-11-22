package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private int checkTask;

    // 新しいフィールドを追加
    private String formattedTime;

    
    public int getCheck() {
		return checkTask;
	}
	public void setCheck(int check) {
		this.checkTask = check;
	}
	public void setFormattedTime(String formattedTime) {
		this.formattedTime = formattedTime;
	}
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
    

    // フォーマットされた時間のゲッター
    public String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // 時間のフォーマット
        return formattedTime = taskDate.format(formatter);
    }
    
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 時間のフォーマット
        return formattedTime = taskDate.format(formatter);
    }

    public Tasks(int id, int categoryId, int accountId, String taskName, LocalDateTime taskDate, String memo,
                 LocalDateTime createdAt, LocalDateTime updateDate, int outin, int checkTask) {
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
        this.checkTask = checkTask;
    }
}
