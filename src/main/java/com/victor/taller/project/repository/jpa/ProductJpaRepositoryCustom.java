package com.victor.taller.project.repository.jpa;

import java.util.List;

import com.victor.taller.project.soa.bean.ProductBean;

public interface ProductJpaRepositoryCustom {
	
	abstract List<Object[]> getProductByNameAndDates(ProductBean productBean);

}
