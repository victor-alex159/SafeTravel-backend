package com.victor.taller.project.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.victor.taller.project.entity.CatalogDetailEntity;

public interface CatalogDetailJpaRepository extends PagingAndSortingRepository<CatalogDetailEntity, Integer> {

}
