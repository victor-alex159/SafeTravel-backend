package com.safetravel.taller.project.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.safetravel.taller.project.soa.bean.ProductBean;

public interface ProductService {

	abstract ProductBean saveProduct(ProductBean productBean);
	abstract List<ProductBean> getAllProducts();
	abstract ProductBean getProductById(Integer productId);
	abstract List<ProductBean> getProductsByUserPrincipal(Integer organizationId);
	abstract List<ProductBean> getProductByType(ProductBean productBean);
	abstract List<Map<String, Object>> getProductByNameAndDates(ProductBean productBean);
	abstract List<ProductBean> getProductsDisabled();
	abstract void deleteProduct(Integer productId);
	abstract void updateStatus(Integer productId);
	abstract XSSFWorkbook generateReportExcel() throws Exception;
}
