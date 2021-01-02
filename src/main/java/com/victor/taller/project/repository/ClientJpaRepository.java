package com.victor.taller.project.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.victor.taller.project.entity.ClientEntity;

public interface ClientJpaRepository extends PagingAndSortingRepository<ClientEntity, Integer> {

	@Query("select c from ClientEntity c where c.username = :username")
	ClientEntity findByUsername(@Param("username") String username);
	
}
