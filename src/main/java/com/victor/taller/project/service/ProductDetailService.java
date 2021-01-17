package com.victor.taller.project.service;

import java.util.List;
import java.util.Map;

import com.victor.taller.project.soa.bean.ProductBean;
import com.victor.taller.project.soa.bean.ProductDetailBean;

public interface ProductDetailService {

	abstract ProductDetailBean saveProductDetail(ProductDetailBean productDetailBean);
	abstract List<Map<String, Object>> getProductDetail(ProductBean productBean);
	abstract ProductDetailBean getProductDetailById(Integer productlId);
	abstract ProductDetailBean getProductDetailByProductId(Integer productDetailId);
	
}
