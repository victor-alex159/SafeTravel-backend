package com.safetravel.taller.project.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.safetravel.taller.project.entity.ProfileEntity;

public interface ProfileJpaRepository extends PagingAndSortingRepository<ProfileEntity, Integer> {

}
