package com.safetravel.taller.project.service;

import java.util.List;

import com.safetravel.taller.project.soa.bean.OrganizationBean;

public interface OrganizationService {

	abstract OrganizationBean saveOrganization(OrganizationBean orgBean);
	abstract List<OrganizationBean> getAllOrganization();
	abstract List<OrganizationBean> getOrganizationByUserCreateId(Integer userCreateId);
	abstract OrganizationBean getOrganizationById(Integer organizationId);
	abstract void deletedOrganization(Integer organizationId);
	
}
