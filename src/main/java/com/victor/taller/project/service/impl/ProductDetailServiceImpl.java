package com.victor.taller.project.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.taller.project.entity.ProductDetailEntity;
import com.victor.taller.project.entity.ProductEntity;
import com.victor.taller.project.repository.ProductDetailJpaRepository;
import com.victor.taller.project.service.ProductDetailService;
import com.victor.taller.project.soa.bean.ProductDetailBean;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

	@Autowired
	private ProductDetailJpaRepository productDetailJpaRepository;
	
	@Override
	public ProductDetailBean saveProducctDetail(ProductDetailBean productDetailBean) {
		ProductDetailEntity productDetailEntity = new ProductDetailEntity();
		BeanUtils.copyProperties(productDetailBean, productDetailEntity);
		productDetailEntity.setProduct(new ProductEntity());
		productDetailEntity.getProduct().setId(productDetailBean.getProduct().getId());
		
		productDetailEntity = productDetailJpaRepository.save(productDetailEntity);
		productDetailBean.setId(productDetailEntity.getId());
		
		return productDetailBean;
	}

}
