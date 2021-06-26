package com.safetravel.taller.project.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetravel.taller.project.entity.OrganizationEntity;
import com.safetravel.taller.project.entity.ProductEntity;
import com.safetravel.taller.project.repository.jpa.ProductJpaRepository;
import com.safetravel.taller.project.service.ProductService;
import com.safetravel.taller.project.soa.bean.OrganizationBean;
import com.safetravel.taller.project.soa.bean.ProductBean;

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
		List<ProductEntity> list = productRepository.getAllProducts();
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

	@Override
	public List<ProductBean> getProductByType(ProductBean productBean) {
		List<ProductEntity> list = productRepository.getProductByType(productBean.getType());
		List<ProductBean> result = new ArrayList<ProductBean>();
		if(list != null) {
			list.forEach(productEntity -> {
				ProductBean prodBean = new ProductBean();
				BeanUtils.copyProperties(productEntity, prodBean);
				if(productEntity.getOrganization() != null) {
					prodBean.setOrganization(new OrganizationBean());
					prodBean.getOrganization().setId(productEntity.getOrganization().getId());
				}
				result.add(prodBean);
			});
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getProductByNameAndDates(ProductBean productBean) {
		List<Object[]> listResult = productRepository.getProductByNameAndDates(productBean);
		if(listResult != null) {
			List<Map<String, Object>> result = new ArrayList<>();
			listResult.forEach(response -> {
				Map<String, Object> map = new HashMap<>();
				map.put("id", response[0]);
				map.put("name", response[1]);
				map.put("image", response[2]);
				map.put("description", response[3]);
				map.put("price", response[4]);
				map.put("ubication", response[5]);
				map.put("startDate", response[6]);
				map.put("endDate", response[7]);
				result.add(map);
			});
			return result;
		}
		
		return null;
	}

}
