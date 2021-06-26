package com.safetravel.taller.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetravel.taller.project.entity.ServiceEntity;
import com.safetravel.taller.project.repository.jpa.ServiceJpaRepository;
import com.safetravel.taller.project.service.ServiceService;
import com.safetravel.taller.project.soa.bean.ServiceBean;

@Service
public class ServiceServiceImpl implements ServiceService {

	@Autowired
	private ServiceJpaRepository serviceJpaRepository;
	
	@Override
	public List<ServiceBean> getAllServices() {
		List<ServiceEntity> listResult = (List<ServiceEntity>) serviceJpaRepository.findAll();
		List<ServiceBean> result = new ArrayList<>();
		if(listResult != null) {
			listResult.forEach(serviceEntity -> {
				ServiceBean serviceBean = new ServiceBean();
				BeanUtils.copyProperties(serviceEntity, serviceBean);
				result.add(serviceBean);
			});
			return result;
		}
		return null;
	}

}
