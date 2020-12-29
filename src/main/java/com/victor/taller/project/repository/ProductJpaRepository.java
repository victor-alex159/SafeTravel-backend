package com.victor.taller.project.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.victor.taller.project.entity.ProductEntity;

public interface ProductJpaRepository extends PagingAndSortingRepository<ProductEntity, Integer> {

}
