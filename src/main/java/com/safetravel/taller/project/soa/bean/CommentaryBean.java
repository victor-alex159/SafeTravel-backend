package com.safetravel.taller.project.soa.bean;

import com.safetravel.taller.project.entity.UserEntity;

public class CommentaryBean extends BaseBean {

	private Integer id;
	private ProductBean product;
	private String description;
	private String username;
	private byte[] userPhoto;
	private String genderTypeId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductBean getProduct() {
		return product;
	}

	public void setProduct(ProductBean product) {
		this.product = product;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte[] getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(byte[] userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getGenderTypeId() {
		return genderTypeId;
	}

	public void setGenderTypeId(String genderTypeId) {
		this.genderTypeId = genderTypeId;
	}
}
