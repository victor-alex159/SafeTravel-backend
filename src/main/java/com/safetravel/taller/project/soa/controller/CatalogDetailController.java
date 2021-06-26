package com.safetravel.taller.project.soa.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.safetravel.taller.project.service.CatalogDetailService;
import com.safetravel.taller.project.soa.bean.CatalogDetailBean;
import com.safetravel.taller.project.soa.request.GenericRequest;
import com.safetravel.taller.project.soa.response.GenericResponse;

@RestController
@RequestMapping(value = "/cdc")
public class CatalogDetailController {
	
	private static final Logger logger = LogManager.getLogger(CatalogDetailController.class);

	@Autowired
	private CatalogDetailService catalogDetailService;
	

	@RequestMapping(value = "/glcdbci", method = RequestMethod.POST)
	public GenericResponse<CatalogDetailBean> getListCatalogDetailByCatalogId(@RequestBody GenericRequest<CatalogDetailBean> request) {
		logger.info("CatalogDetailController.getListCatalogDetailByCatalogId()");
		GenericResponse<CatalogDetailBean> response = new GenericResponse<>();
		List<CatalogDetailBean> listCatalogDetails = new ArrayList<>();
		listCatalogDetails = catalogDetailService.getListCatalogDetailByCatalogId(request.getData());
		response.setDatalist(listCatalogDetails);
		return response;
	}

}
