package com.safetravel.taller.project.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.safetravel.taller.project.entity.CatalogEntity;

public interface CatalogJpaRepository extends PagingAndSortingRepository<CatalogEntity, Integer> {

}
