package com.victor.taller.project.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.victor.taller.project.entity.CatalogEntity;

public interface CatalogJpaRepository extends PagingAndSortingRepository<CatalogEntity, Integer> {

}
