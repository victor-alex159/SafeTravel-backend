package com.victor.taller.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.taller.project.entity.OrganizationEntity;
import com.victor.taller.project.entity.ProductEntity;
import com.victor.taller.project.repository.ProductJpaRepository;
import com.victor.taller.project.service.ProductService;
import com.victor.taller.project.soa.bean.OrganizationBean;
import com.victor.taller.project.soa.bean.ProductBean;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductJpaRepository productRepository;

	@Override
	public ProductBean saveProduct(ProductBean productBean) {
		ProductEntity productEntity = new ProductEntity();
		BeanUtils.copyProperties(productBean, productEntity);
		if(productBean.getOrganization() != null) {
			productEntity.setOrganization(new OrganizationEntity());
			productEntity.getOrganization().setId(productBean.getOrganization().getId());			
		}
		
		productEntity = productRepository.save(productEntity);
		productBean.setId(productEntity.getId());
		return productBean;
	}

	@Override
	public List<ProductBean> getAllProducts() {
		List<ProductBean> result = new ArrayList<>();
		Iterable<ProductEntity> list = productRepository.findAll();
		if(list != null) {
			list.forEach(productEntity -> {
				ProductBean productBean = new ProductBean();
				BeanUtils.copyProperties(productEntity, productBean);
				if(productEntity.getOrganization() != null) {
					productBean.setOrganization(new OrganizationBean());
					productBean.getOrganization().setId(productEntity.getOrganization().getId());
				}
				result.add(productBean);
			});
		}
		
		return result;
	}

	@Override
	public ProductBean getProductById(Integer productId) {
		ProductEntity productEntity = productRepository.findById(productId).orElse(null);
		ProductBean productBean = new ProductBean();
		BeanUtils.copyProperties(productEntity, productBean);
		if(productEntity.getOrganization() != null) {
			productBean.setOrganization(new OrganizationBean());
			productBean.getOrganization().setId(productEntity.getOrganization().getId());
		}
		
		return productBean;
	}

	@Override
	public List<ProductBean> getProductsByUserPrincipal(Integer organizationId) {
		List<ProductBean> result = new ArrayList<>();
		List<ProductEntity> list = productRepository.getProductsByUserPrincipal(organizationId);
		if(list != null) {
			list.forEach(productEntity -> {
				ProductBean productBean = new ProductBean();
				BeanUtils.copyProperties(productEntity, productBean);
				if(productEntity.getOrganization() != null) {
					productBean.setOrganization(new OrganizationBean());
					productBean.getOrganization().setId(productEntity.getOrganization().getId());
				}
				result.add(productBean);
			});
		}
		
		return result;
	}

}
