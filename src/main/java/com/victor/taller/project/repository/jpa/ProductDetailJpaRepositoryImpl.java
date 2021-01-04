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
		StringBuffer queryStr = new StringBuffer("SELECT pro.name, pd.image_path, pd.description, pd.price, pd.address, pro.start_date, pro.end_date FROM product_detail pd ");
		queryStr.append(" INNER JOIN product pro ON pd.product_id=pro.id WHERE ");
		
		if(productBean.getStartDateRequest() != null && productBean.getEndDateRequest() != null) {
			queryStr.append(" pro.start_date BETWEEN :startDate AND :endDate ");
			queryStr.append(" AND pro.end_date BETWEEN :startDate AND :endDate ");
		}
		
		queryStr.append(" AND pro.deleted=false AND pd.deleted=false");
		Query query = em.createNativeQuery(queryStr.toString());
		
		if(productBean.getStartDateRequest() != null && productBean.getEndDateRequest() != null) {
			query.setParameter("startDate", productBean.getStartDateRequest());
			query.setParameter("endDate", productBean.getEndDateRequest());
		}
		
		return query.getResultList();
	}

}
