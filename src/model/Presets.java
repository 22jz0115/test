package model;

import java.time.LocalDateTime;

public class Presets {
    private int id;
    private String presetName;
    private LocalDateTime createAt;
    private LocalDateTime updateDate;
    private int accountId;

    public Presets(int id, String presetName, LocalDateTime createAt, LocalDateTime updateDate, int accountId) {
        this.id = id;
        this.presetName = presetName;
        this.createAt = createAt;
        this.updateDate = updateDate;
        this.accountId = accountId;
    }
    // ゲッターとセッター
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
}
