package model;

import java.time.LocalDateTime;

public class Collections {
	private int id;
	private String img;
	private String rank;
	private LocalDateTime createdAt;
	private LocalDateTime updateDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
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
	public Collections(int id, String img, String rank, LocalDateTime createdAt,
			LocalDateTime updateDate) {
		super();
		this.id = id;

		this.img = img;
		this.rank = rank;
		this.createdAt = createdAt;
		this.updateDate = updateDate;
	}
}
