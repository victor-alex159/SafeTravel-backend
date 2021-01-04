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

	@Override
	public List<Map<String, Object>> getProductDetail(ProductBean productBean) {
		List<Object[]> listResult = productDetailJpaRepository.getProductDetail(productBean);
		if(listResult != null) {
			List<Map<String, Object>> result = new ArrayList<>();
			listResult.forEach(response -> {
				Map<String, Object> map = new HashMap<>();
				map.put("name", response[0]);
				map.put("imagePath", response[1]);
				map.put("description", response[2]);
				map.put("price", response[3]);
				map.put("address", response[4]);
				map.put("startDate", response[5]);
				map.put("endDate", response[6]);
				result.add(map);
			});
			return result;
		}
		
		return null;
	}

}
