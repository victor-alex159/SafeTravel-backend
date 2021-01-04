package com.victor.taller.project.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.victor.taller.project.entity.ProductEntity;

public interface ProductJpaRepository extends PagingAndSortingRepository<ProductEntity, Integer>, ProductJpaRepositoryCustom {

	@Query("SELECT o FROM ProductEntity o inner join o.organization org where org.id =:organizationId ")	
	public List<ProductEntity> getProductsByUserPrincipal(@Param("organizationId") Integer organizationId);
	
}
