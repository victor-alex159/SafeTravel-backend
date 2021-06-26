package com.safetravel.taller.project.service;

import java.util.List;

import com.safetravel.taller.project.soa.bean.ServiceBean;

public interface ServiceService {

	abstract List<ServiceBean> getAllServices();
	
}
