package model;

import java.time.LocalDateTime;

public class Options {
	private int id;
	private int accountId;
	private boolean bluetooth;
	private boolean message;
	private String textColor;
	private String backgroundColor;
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
	public boolean isBluetooth() {
		return bluetooth;
	}
	public void setBluetooth(boolean bluetooth) {
		this.bluetooth = bluetooth;
	}
	public boolean isMessage() {
		return message;
	}
	public void setMessage(boolean message) {
		this.message = message;
	}
	public String getTextColor() {
		return textColor;
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
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
	
	
	public Options(int id, int accountId, boolean bluetooth, boolean message, String textColor, String backgroundColor,
			LocalDateTime createdAt, LocalDateTime updateDate) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.bluetooth = bluetooth;
		this.message = message;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
		this.createdAt = createdAt;
		this.updateDate = updateDate;
	}
	
}
