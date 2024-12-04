package model;

import java.time.LocalDateTime;

public class Subscriptions {
	private int id;
	private int account_id;
    private String end_point;
    private String p256dh;
    private String auth;
    private LocalDateTime createdAt;
    private LocalDateTime updateDate;
	public Subscriptions(int id, int account_id, String end_point, String p256dh, String auth, LocalDateTime createdAt,
			LocalDateTime updateDate) {
		super();
		this.id = id;
		this.account_id = account_id;
		this.end_point = end_point;
		this.p256dh = p256dh;
		this.auth = auth;
		this.createdAt = createdAt;
		this.updateDate = updateDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public String getEnd_point() {
		return end_point;
	}
	public void setEnd_point(String end_point) {
		this.end_point = end_point;
	}
	public String getP256dh() {
		return p256dh;
	}
	public void setP256dh(String p256dh) {
		this.p256dh = p256dh;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
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
    
    
}
