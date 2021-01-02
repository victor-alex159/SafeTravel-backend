package com.victor.taller.project.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.victor.taller.project.soa.bean.ProductBean;
import com.victor.taller.project.soa.bean.ProductDetailBean;

public class ProductDetailJpaImpl implements ProductDetailJpaRepositoryCustom {

	@PersistenceContext()
	private EntityManager em;

}
