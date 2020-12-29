package com.victor.taller.project.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.victor.taller.project.entity.OrganizationEntity;

public interface OrganizationJpaRepository extends PagingAndSortingRepository<OrganizationEntity, Integer> {

}
