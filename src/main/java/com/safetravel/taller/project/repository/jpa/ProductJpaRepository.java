package com.safetravel.taller.project.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.safetravel.taller.project.entity.ProductEntity;

public interface ProductJpaRepository extends PagingAndSortingRepository<ProductEntity, Integer>, ProductJpaRepositoryCustom {

	@Query("SELECT p FROM ProductEntity p inner join p.organization org where org.id =:organizationId and p.deleted=false and org.deleted=false")	
	public List<ProductEntity> getProductsByUserPrincipal(@Param("organizationId") Integer organizationId);
	
	@Query("SELECT p FROM ProductEntity p inner join p.organization org WHERE p.type=:type and p.deleted=false and org.deleted=false")
	public List<ProductEntity> getProductByType(@Param("type") String type);
	
	@Query("SELECT p FROM ProductEntity p inner join p.organization org WHERE p.deleted=false and org.deleted=false")
	public List<ProductEntity> getAllProducts();
}
