package com.safetravel.taller.project.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.safetravel.taller.project.entity.CatalogDetailEntity;

public interface CatalogDetailJpaRepository extends PagingAndSortingRepository<CatalogDetailEntity, Integer> {

	@Query("SELECT cd FROM CatalogDetailEntity cd WHERE cd.catalog.id=:catalogId AND cd.deleted=false ")
	public List<CatalogDetailEntity> getListCatalogDetailByCatalogId(@Param("catalogId") Integer catalogId);
	
}
