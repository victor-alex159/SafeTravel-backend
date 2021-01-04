package com.victor.taller.project.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.victor.taller.project.entity.UserEntity;

public interface UserJpaRepository extends PagingAndSortingRepository<UserEntity, Integer> {

	@Query("select u from UserEntity u where u.username = :username")
	UserEntity findByUsername(@Param("username") String username);
	
	
}
