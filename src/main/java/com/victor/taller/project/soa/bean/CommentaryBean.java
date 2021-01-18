package com.victor.taller.project.soa.bean;

import com.victor.taller.project.entity.UserEntity;

public class CommentaryBean extends BaseBean {

	private Integer id;
	private ProductBean product;
	private String description;

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
}
