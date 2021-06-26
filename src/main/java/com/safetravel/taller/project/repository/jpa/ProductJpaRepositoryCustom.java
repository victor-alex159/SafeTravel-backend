package com.safetravel.taller.project.repository.jpa;

import java.util.List;

import com.safetravel.taller.project.soa.bean.ProductBean;

public interface ProductJpaRepositoryCustom {
	
	abstract List<Object[]> getProductByNameAndDates(ProductBean productBean);

}
