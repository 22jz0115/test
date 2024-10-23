package model;

import java.time.LocalDateTime;

public class Accounts {
	private int id;
	private String name;
    private String email;
    private String pass;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updateDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	public Accounts(int id, String name, String email, String pass, String location, LocalDateTime createdAt,
			LocalDateTime updateDate) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.pass = pass;
		this.location = location;
		this.createdAt = createdAt;
		this.updateDate = updateDate;
	}
    
    
    

}