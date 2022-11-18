package com.safetravel.taller.project.soa.controller;

import java.io.IOException;
import java.util.List;

import com.safetravel.taller.project.util.UtilFunctions;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.safetravel.taller.project.service.UserService;
import com.safetravel.taller.project.soa.bean.OrganizationBean;
import com.safetravel.taller.project.soa.bean.ProductBean;
import com.safetravel.taller.project.soa.bean.UserBean;
import com.safetravel.taller.project.soa.request.GenericRequest;
import com.safetravel.taller.project.soa.response.GenericResponse;
import com.safetravel.taller.project.util.MailUtil;

@RestController
@RequestMapping(value = "/uc")
public class UserController {

	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
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
	
	@RequestMapping(value = "/gau", method = RequestMethod.POST)
	public GenericResponse<UserBean> getAllusers(@RequestBody GenericRequest<UserBean> request) {
		logger.info("UserController.getAllusers()");
		GenericResponse<UserBean> response = new GenericResponse<UserBean>();
		List<UserBean> listUsers = userService.getAllUsers();
		response.setDatalist(listUsers);
		return response;
	}
	
	@RequestMapping(value = "/scvfp", method = RequestMethod.POST)
	public GenericResponse<UserBean> sendCodeVerificationForPassword(@RequestBody GenericRequest<UserBean> request) {
		logger.info("UserController.sendCodeVerificationForPassword()");
		GenericResponse response = new GenericResponse();
		UserBean user = userService.getUserByEmail(request.getData().getEmail());
		if(UtilFunctions.noEsNulo(user)) {
			String token = bcrypt.encode(RandomStringUtils.randomAlphanumeric(10)).replace("/", "").replace(".", "");
			user.setTokenResetPassword(token);
			userService.saveUser(user);
			//String code = RandomStringUtils.randomAlphanumeric(8);
			//String textBody = "Ingresar en el siguiente enlace: http://localhost:4200/us/reset_password/" + token;
			StringBuffer bodyHtml = new StringBuffer("<h2>Recuperar Contraseña</h2>");
			bodyHtml.append("<table style='font-family: arial, sans-serif;border-collapse: collapse;width: 100%;'><tr><th style='border: 1px solid #dddddd;text-align: left;padding: 8px;'>Mensaje</th><th style='border: 1px solid #dddddd;text-align: left;padding: 8px;'>Link</th></tr>");
			bodyHtml.append("<tr><td style='border: 1px solid #dddddd;text-align: left;padding: 8px;'>Haga click en el siguiente enlace</td><td style='border: 1px solid #dddddd;text-align: left;padding: 8px;'>");
			//bodyHtml.append("http://localhost:4200/us/reset_password/" + token);
			bodyHtml.append("https://safetravelpe.web.app/us/reset_password/" + token);
			bodyHtml.append("</td></tr></table>");
			
			MailUtil.sendEmail(user.getEmail(),"Restablecer contraseña", null, bodyHtml.toString());
			response.setData(user);
		}
		return response;
	}
	
	@RequestMapping(value = "/cp", method = RequestMethod.POST)
	public GenericResponse<UserBean> changePassword(@RequestBody GenericRequest<UserBean> request) {
		logger.info("UserController.changePassword()");
		GenericResponse response = new GenericResponse();
		UserBean userRequest = request.getData();
		if(UtilFunctions.noEsNulo(userRequest.getId())) {
			UserBean user = userService.getUserById(userRequest.getId());
			user.setPassword(userRequest.getPassword());
			userService.saveUser(user);
			response.setData(user);
		}
		return response;
	}
	
	@RequestMapping(value = "/cpwt/{tokenPassword}", method = RequestMethod.POST)
	public GenericResponse<UserBean> changePasswordWithToken(@PathVariable("tokenPassword") String tokenPassword, @RequestBody GenericRequest<UserBean> request) {
		logger.info("UserController.changePasswordWithToken()");
		GenericResponse response = new GenericResponse();
		UserBean userRequest = request.getData();
		UserBean user = userService.getUserByTokenResetPassword(tokenPassword);
		if(UtilFunctions.noEsNulo(user)) {
			user.setPassword(userRequest.getPassword());
			user.setTokenResetPassword(null);
			logger.info("SaveUser changePasswordWithToken()");
			userService.saveUser(user);
			response.setData(user);
		}
		
		return response;
	}
	
	@RequestMapping(value = "/gubus", method = RequestMethod.POST)
	public GenericResponse<UserBean> getUserByUserSession(@RequestBody GenericRequest<UserBean> request) {
		logger.info("UserController.getUserByUserSession()");
		GenericResponse<UserBean> response = new GenericResponse();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserBean user = userService.getUserByUsername(principal.toString());
		response.setData(user);
		return response;
	}
	
	@RequestMapping(value = "/sp", method = RequestMethod.POST)
	public GenericResponse<UserBean> saveImage(@RequestPart("userPhoto") UserBean userBean, @RequestPart("file") MultipartFile file) throws IOException {
		logger.info("UserController.saveImage()");
		GenericResponse<UserBean> response = new GenericResponse<>();
		UserBean user = new UserBean();
		UserBean userAux = userBean;
		if(file.getBytes().length > 0) {
			userAux.setPhoto(file.getBytes());
		}
		user = userService.saveUser(userAux);
		response.setData(userAux);
		return response;
	}
	
	@RequestMapping(value = "/gi/{userId}", method = RequestMethod.POST)
	public GenericResponse<byte[]> getImage(@PathVariable("userId") Integer userId) {
		logger.info("ProductController.getImage()");
		GenericResponse<byte[]> response = new GenericResponse<>();
		UserBean userBean = new UserBean();
		userBean = userService.getUserById(userId);
		byte[] imageData = userBean.getPhoto();
		response.setData(imageData);
		return response;
	}
	
	
}
