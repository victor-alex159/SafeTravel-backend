package com.victor.taller.project.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.victor.taller.project.service.ClientService;
import com.victor.taller.project.soa.bean.ClientBean;
import com.victor.taller.project.soa.bean.UserBean;
import com.victor.taller.project.soa.request.GenericRequest;
import com.victor.taller.project.soa.response.GenericResponse;

@RestController
@RequestMapping(value = "/cc")
public class ClientController {

	private static final Logger logger = LogManager.getLogger(ClientController.class);
	
	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value = "/sc", method = RequestMethod.POST)
	public GenericResponse<ClientBean> saveClient(@RequestBody GenericRequest<ClientBean> request) {
		logger.info("ClientController.saveClient()");
		GenericResponse<ClientBean> response = new GenericResponse<ClientBean>();
		ClientBean client = new ClientBean();
		client = clientService.saveClient(request.getData());
		response.setData(client);
		return response;
	}
	
}
