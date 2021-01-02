package com.victor.taller.project.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.victor.taller.project.entity.ProductDetailEntity;

public interface ProductDetailJpaRepository extends PagingAndSortingRepository<ProductDetailEntity, Integer>, ProductDetailJpaRepositoryCustom {

}
