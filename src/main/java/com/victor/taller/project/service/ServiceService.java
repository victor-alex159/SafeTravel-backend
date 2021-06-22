package com.victor.taller.project.service;

import java.util.List;

import com.victor.taller.project.soa.bean.ServiceBean;

public interface ServiceService {

	abstract List<ServiceBean> getAllServices();
	
}
