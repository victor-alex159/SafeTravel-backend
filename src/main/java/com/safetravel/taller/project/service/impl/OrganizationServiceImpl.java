package com.safetravel.taller.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.safetravel.taller.project.util.UtilFunctions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.safetravel.taller.project.entity.OrganizationEntity;
import com.safetravel.taller.project.repository.jpa.OrganizationJpaRepository;
import com.safetravel.taller.project.service.OrganizationService;
import com.safetravel.taller.project.soa.bean.OrganizationBean;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationJpaRepository organizationRepository;
	
	@Override
	public OrganizationBean saveOrganization(OrganizationBean orgBean) {
		OrganizationEntity orgEntity = new OrganizationEntity();
		BeanUtils.copyProperties(orgBean, orgEntity);
		orgEntity = organizationRepository.save(orgEntity);
		orgBean.setId(orgEntity.getId());
		return orgBean;
	}

	@Override
	public List<OrganizationBean> getAllOrganization() {
		List<OrganizationBean> result = new ArrayList<OrganizationBean>();
		Iterable<OrganizationEntity> list = organizationRepository.findAll();
		if(UtilFunctions.noEsNulo(list)) {
			list.forEach(organizationEntity -> {
				OrganizationBean orgBean = new OrganizationBean();
				BeanUtils.copyProperties(organizationEntity, orgBean);
				result.add(orgBean);
			});
		}
		
		return result;
	}

	@Override
	public void deletedOrganization(Integer organizationId) {
		if(!UtilFunctions.esNulo(organizationId)) {
			organizationRepository.delete(organizationId);
		}
		
	}

	@Override
	public List<OrganizationBean> getOrganizationByUserCreateId(Integer userCreateId) {

		return null;
	}

	@Override
	public OrganizationBean getOrganizationById(Integer organizationId) {
		OrganizationEntity organizationEntity = organizationRepository.findById(organizationId).orElse(null);
		OrganizationBean organizationBean = new OrganizationBean();
		BeanUtils.copyProperties(organizationEntity, organizationBean);
		
		return organizationBean;
	}

}
