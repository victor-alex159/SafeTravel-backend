package com.victor.taller.project.soa.bean;

import com.victor.taller.project.entity.UserEntity;

public class CommentayBean extends BaseBean {

	private Integer id;
	private OrganizationBean organization;
	private UserEntity user;
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OrganizationBean getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationBean organization) {
		this.organization = organization;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
