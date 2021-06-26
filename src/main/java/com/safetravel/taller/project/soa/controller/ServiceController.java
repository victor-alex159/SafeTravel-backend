package com.safetravel.taller.project.soa.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetravel.taller.project.service.ServiceService;
import com.safetravel.taller.project.soa.bean.ServiceBean;
import com.safetravel.taller.project.soa.request.GenericRequest;
import com.safetravel.taller.project.soa.response.GenericResponse;

@RestController
@RequestMapping(value = "/sc")
public class ServiceController {

	private static final Logger logger = LogManager.getLogger(ServiceController.class);
	
	@Autowired
	private ServiceService serviceService;
	
	@RequestMapping(value = "/gas")
	public GenericResponse<ServiceBean> getAllServiceBean(@RequestBody GenericRequest<ServiceBean> request) {
		logger.info("ServiceController.getAllServiceBean()");
		GenericResponse<ServiceBean> response = new GenericResponse<ServiceBean>();
		List<ServiceBean> listServices = serviceService.getAllServices();
		response.setDatalist(listServices);
		return response;
	}
}
