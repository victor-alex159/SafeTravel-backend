package com.safetravel.taller.project.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.safetravel.taller.project.entity.ServiceEntity;

public interface ServiceJpaRepository extends PagingAndSortingRepository<ServiceEntity, Integer> {

	@Query("select ser from ServiceEntity ser where ser.code=:code")
	public ServiceEntity getServiceByCode(@Param("code") String code);
	
	@Query("select ser from ServiceEntity ser where ser.code in(:code)")
	public List<ServiceEntity> getListServiceByCodes(@Param("code") List<String> code);
	
	@Query("select ser from ServiceEntity ser where ser.id=:id")
	public ServiceEntity getServiceById(@Param("id") Integer id);
	
}
