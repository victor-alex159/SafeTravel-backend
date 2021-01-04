package com.victor.taller.project.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.victor.taller.project.service.ProductDetailService;
import com.victor.taller.project.soa.bean.ProductBean;
import com.victor.taller.project.soa.bean.ProductDetailBean;
import com.victor.taller.project.soa.request.GenericRequest;
import com.victor.taller.project.soa.response.GenericResponse;

@RestController
@RequestMapping(value = "/pdc")
public class ProductDetailController {

	private static final Logger logger = LogManager.getLogger(ProductDetailController.class);

	@Autowired
	private ProductDetailService productDetailService;
	
	@RequestMapping(value = "/spd", method = RequestMethod.POST)
	public GenericResponse<ProductDetailBean> saveProductDetail(@RequestBody GenericRequest<ProductDetailBean> request) {
		logger.info("ProductDetailController.saveProductDetail()");
		GenericResponse<ProductDetailBean> response = new GenericResponse<>();
		ProductDetailBean productDetail = new ProductDetailBean();
		productDetail = productDetailService.saveProducctDetail(request.getData());
		response.setData(productDetail);
		return response;
	}
	
	@RequestMapping(value = "/gpd", method = RequestMethod.POST)
	public GenericResponse<List<Map<String, Object>>> getProductDetail(@RequestBody GenericRequest<ProductBean> request) {
		logger.info("ProductDetailController.getProductDetail()");
		GenericResponse<List<Map<String, Object>>> response = new GenericResponse<>();
		List<Map<String, Object>> productDetailList = productDetailService.getProductDetail(request.getData());
		response.setData(productDetailList);
		return response;
	}
	
}
