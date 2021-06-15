package com.victor.taller.project.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.victor.taller.project.soa.bean.ProductBean;

public class ProductJpaRepositoryImpl implements ProductJpaRepositoryCustom {

	@PersistenceContext()
	private EntityManager em;
	
	@Override
	public List<Object[]> getProductByNameAndDates(ProductBean productBean) {
		StringBuffer queryStr = new StringBuffer("SELECT pd.id, pd.name, pd.image, pd.long_description, pd.price, pd.ubication, pd.start_date, pd.end_date FROM product pd ");
		
		if(productBean.getName() != null && !productBean.getName().isEmpty()) {
			queryStr.append(" WHERE UPPER(pd.name) LIKE CONCAT('%',UPPER(:name),'%') AND ");
		}
		
		if(productBean.getStartDateRequest() != null && productBean.getEndDateRequest() != null) {
			queryStr.append(" pd.start_date BETWEEN :startDate AND :endDate AND ");
			queryStr.append(" pd.end_date BETWEEN :startDate AND :endDate AND");
		}
		
		queryStr.append(" pd.deleted=false AND pd.deleted=false;");
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
