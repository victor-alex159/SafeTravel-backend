package com.victor.taller.project.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.victor.taller.project.entity.ProfileEntity;

public interface ProfileJpaRepository extends PagingAndSortingRepository<ProfileEntity, Integer> {

}
