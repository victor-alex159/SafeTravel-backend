package com.victor.taller.project.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.victor.taller.project.entity.UserEntity;

public interface UserJpaRepository extends PagingAndSortingRepository<UserEntity, Integer> {

	UserEntity findOneByUsername(String username);
	
	
}
