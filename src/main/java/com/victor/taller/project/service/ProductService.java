package com.victor.taller.project.service;

import java.util.List;

import com.victor.taller.project.soa.bean.ProductBean;

public interface ProductService {

	abstract ProductBean saveProduct(ProductBean productBean);
	abstract List<ProductBean> getAllProducts();
	abstract ProductBean getProductById(Integer productId);
	abstract List<ProductBean> getProductsByUserPrincipal(Integer userCreateId);
	abstract List<ProductBean> getProductByType(ProductBean productBean);
}
