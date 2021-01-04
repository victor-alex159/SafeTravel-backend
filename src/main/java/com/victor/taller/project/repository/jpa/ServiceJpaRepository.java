package com.victor.taller.project.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.victor.taller.project.entity.ServiceEntity;

public interface ServiceJpaRepository extends PagingAndSortingRepository<ServiceEntity, Integer> {

}
