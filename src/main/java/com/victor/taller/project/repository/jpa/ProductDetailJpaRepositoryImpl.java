package com.victor.taller.project.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.victor.taller.project.soa.bean.ProductBean;

public class ProductDetailJpaRepositoryImpl implements ProductDetailJpaRepositoryCustom {

	@PersistenceContext()
	private EntityManager em;
	
	@Override
	public List<Object[]> getProductDetail(ProductBean productBean) {
		StringBuffer queryStr = new StringBuffer("SELECT pd.id, pd.product_id, pro.name, pd.image, pd.description, pd.price, pd.address, pro.start_date, pro.end_date FROM product_detail pd ");
		queryStr.append(" INNER JOIN product pro ON pd.product_id=pro.id WHERE ");
		
		if(productBean.getName() != null && !productBean.getName().isEmpty()) {
			queryStr.append(" UPPER(pro.name) LIKE CONCAT('%',UPPER(:name),'%') AND ");
		}
		
		if(productBean.getStartDateRequest() != null && productBean.getEndDateRequest() != null) {
			queryStr.append(" pro.start_date BETWEEN :startDate AND :endDate AND ");
			queryStr.append(" pro.end_date BETWEEN :startDate AND :endDate AND");
		}
		
		queryStr.append(" pro.deleted=false AND pd.deleted=false");
		Query query = em.createNativeQuery(queryStr.toString());
		
		if(productBean.getName() != null && !productBean.getName().isEmpty()) {
			query.setParameter("name", "%" + productBean.getName().toUpperCase() + "%");
		}
		
		if(productBean.getStartDateRequest() != null && productBean.getEndDateRequest() != null) {
			query.setParameter("startDate", productBean.getStartDateRequest());
			query.setParameter("endDate", productBean.getEndDateRequest());
		}
		
		
		return query.getResultList();
	}

}
