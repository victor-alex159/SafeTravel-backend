package com.victor.taller.project.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.victor.taller.project.entity.ProductDetailEntity;

public interface ProductDetailJpaRepository extends PagingAndSortingRepository<ProductDetailEntity, Integer>, ProductDetailJpaRepositoryCustom {

	@Query("SELECT pd FROM ProductDetailEntity pd WHERE pd.product.id=:productId AND pd.deleted=false ")
	public ProductDetailEntity getProductDetailByProductId(@Param("productId") Integer productId);
	
}
