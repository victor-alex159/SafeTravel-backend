package com.victor.taller.project.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.victor.taller.project.service.UserService;
import com.victor.taller.project.soa.bean.UserBean;
import com.victor.taller.project.soa.request.GenericRequest;
import com.victor.taller.project.soa.response.GenericResponse;

@RestController
@RequestMapping(value = "/uc")
public class UserController {

	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/su", method = RequestMethod.POST)
	public GenericResponse<UserBean> saveUser(@RequestBody GenericRequest<UserBean> request) {
		logger.info("UserController.saveUser()");
		GenericResponse<UserBean> response = new GenericResponse<UserBean>();
		UserBean user = new UserBean();
		user = userService.saveUser(request.getData());
		response.setData(user);
		return response;
	}
	
	@RequestMapping(value = "/gubi", method = RequestMethod.POST)
	public GenericResponse<UserBean> getUserById(@RequestBody GenericRequest<UserBean> request) {
		logger.info("UserController.getUserById()");
		GenericResponse<UserBean> response = new GenericResponse<UserBean>();
		UserBean user = new UserBean();
		user = userService.getUserById(request.getData().getId());
		response.setData(user);
		return response;
	}
	
	
}
