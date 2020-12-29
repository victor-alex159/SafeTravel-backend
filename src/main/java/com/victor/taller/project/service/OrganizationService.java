package com.victor.taller.project.service;

import java.util.List;

import com.victor.taller.project.soa.bean.OrganizationBean;

public interface OrganizationService {

	abstract OrganizationBean saveOrganization(OrganizationBean orgBean);
	
	abstract List<OrganizationBean> getAllOrganization();
	
	abstract void deletedOrganization(Integer organizationId);
	
}
