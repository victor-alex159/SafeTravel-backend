package com.victor.taller.project.service;

import com.victor.taller.project.soa.bean.UserBean;

public interface UserService {

	abstract UserBean saveUser(UserBean userBean);
	abstract UserBean getUserById(Integer id);
	abstract UserBean getUserByUsername(String username);
	
}
