package com.victor.taller.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.victor.taller.project.service.OrganizationService;
import com.victor.taller.project.soa.bean.OrganizationBean;
import com.victor.taller.project.soa.request.GenericRequest;
import com.victor.taller.project.soa.response.GenericResponse;

@RestController
@RequestMapping(value = "/oc")
public class OrganizationController {

	private static final Logger logger = LogManager.getLogger(OrganizationController.class);
	
	@Autowired
	private OrganizationService organizationService;
	
	@RequestMapping(value = "/so", method = RequestMethod.POST)
	public GenericResponse<OrganizationBean> saveOrganization(@RequestBody GenericRequest<OrganizationBean> request) {
		logger.info("OrganizationController.saveOrganization()");
		GenericResponse<OrganizationBean> response = new GenericResponse<>();
		OrganizationBean org = new OrganizationBean();
		org = request.getData();
		organizationService.saveOrganization(org);
		response.setData(org);
		return response;
	}
	
	@RequestMapping(value = "/gao", method = RequestMethod.POST)
	public GenericResponse<OrganizationBean> getAllOrganization(@RequestBody GenericRequest<OrganizationBean> request) {
		logger.info("OrganizationController.getAllOrganization()");
		GenericResponse<OrganizationBean> response = new GenericResponse<>();
		List<OrganizationBean> organizationList = new ArrayList<OrganizationBean>();
		organizationList = organizationService.getAllOrganization();
		response.setDatalist(organizationList);
		return response;
	}
	
}
