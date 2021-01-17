package com.victor.taller.project.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.taller.project.entity.ProductDetailEntity;
import com.victor.taller.project.entity.ProductEntity;
import com.victor.taller.project.repository.jpa.ProductDetailJpaRepository;
import com.victor.taller.project.service.ProductDetailService;
import com.victor.taller.project.soa.bean.ProductBean;
import com.victor.taller.project.soa.bean.ProductDetailBean;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

	@Autowired
	private ProductDetailJpaRepository productDetailRepository;
	
	@Override
	public ProductDetailBean saveProductDetail(ProductDetailBean productDetailBean) {
		ProductDetailEntity productDetailEntity = new ProductDetailEntity();
		BeanUtils.copyProperties(productDetailBean, productDetailEntity);
		productDetailEntity.setProduct(new ProductEntity());
		productDetailEntity.getProduct().setId(productDetailBean.getProduct().getId());
		
		productDetailEntity = productDetailRepository.save(productDetailEntity);
		productDetailBean.setId(productDetailEntity.getId());
		
		return productDetailBean;
	}

	@Override
	public List<Map<String, Object>> getProductDetail(ProductBean productBean) {
		List<Object[]> listResult = productDetailRepository.getProductDetail(productBean);
		if(listResult != null) {
			List<Map<String, Object>> result = new ArrayList<>();
			listResult.forEach(response -> {
				Map<String, Object> map = new HashMap<>();
				map.put("id", response[0]);
				map.put("productId", response[1]);
				map.put("name", response[2]);
				map.put("imagePath", response[3]);
				map.put("description", response[4]);
				map.put("price", response[5]);
				map.put("address", response[6]);
				map.put("startDate", response[7]);
				map.put("endDate", response[8]);
				result.add(map);
			});
			return result;
		}
		
		return null;
	}

	@Override
	public ProductDetailBean getProductDetailByProductId(Integer productId) {
		ProductDetailEntity productDetailEntity = productDetailRepository.getProductDetailByProductId(productId);
		ProductDetailBean productDetailBean = new ProductDetailBean();
		BeanUtils.copyProperties(productDetailEntity, productDetailBean);
		if(productDetailEntity.getProduct() != null) {
			productDetailBean.setProduct(new ProductBean());
			productDetailBean.getProduct().setId(productDetailEntity.getProduct().getId());
		}
		return productDetailBean;
	}
	@Override
	public ProductDetailBean getProductDetailById(Integer productDetailId) {
		ProductDetailEntity productDetailEntity = productDetailRepository.findById(productDetailId).orElse(null);
		ProductDetailBean productDetailBean = new ProductDetailBean();
		BeanUtils.copyProperties(productDetailEntity, productDetailBean);
		if(productDetailEntity.getProduct() != null) {
			productDetailBean.setProduct(new ProductBean());
			productDetailBean.getProduct().setId(productDetailEntity.getProduct().getId());
		}
		return productDetailBean;
	}

}
