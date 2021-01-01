package com.victor.taller.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.taller.project.entity.OrganizationEntity;
import com.victor.taller.project.entity.ServiceEntity;
import com.victor.taller.project.entity.UserEntity;
import com.victor.taller.project.repository.OrganizationJpaRepository;
import com.victor.taller.project.service.OrganizationService;
import com.victor.taller.project.soa.bean.OrganizationBean;
import com.victor.taller.project.soa.bean.ServiceBean;
import com.victor.taller.project.soa.bean.UserBean;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationJpaRepository organizationRepository;
	
	@Override
	public OrganizationBean saveOrganization(OrganizationBean orgBean) {
		OrganizationEntity orgEntity = new OrganizationEntity();
		BeanUtils.copyProperties(orgBean, orgEntity);
		
		if(orgBean.getAdminUserId() != null && orgBean.getAdminUserId().getId() != null) {
			orgEntity.setAdminUserId(new UserEntity());
			orgEntity.getAdminUserId().setId(orgBean.getAdminUserId().getId());
		}
		if(orgBean.getService() != null && orgBean.getService().getId() != null) {
			orgEntity.setService(new ServiceEntity());
			orgEntity.getService().setId(orgBean.getService().getId());
		}
		orgEntity = organizationRepository.save(orgEntity);
		orgBean.setId(orgEntity.getId());
		return orgBean;
	}

	@Override
	public List<OrganizationBean> getAllOrganization() {
		List<OrganizationBean> result = new ArrayList<OrganizationBean>();
		Iterable<OrganizationEntity> list = organizationRepository.findAll();
		if(list != null) {
			list.forEach(organizationEntity -> {
				OrganizationBean orgBean = new OrganizationBean();
				BeanUtils.copyProperties(organizationEntity, orgBean);
				if(organizationEntity.getAdminUserId() != null) {
					orgBean.setAdminUserId(new UserBean());
					orgBean.getAdminUserId().setId(organizationEntity.getAdminUserId().getId());					
				}
				if(organizationEntity.getService() != null) {
					orgBean.setService(new ServiceBean());
					orgBean.getService().setId(organizationEntity.getService().getId());
				}
				result.add(orgBean);
			});
		}
		
		return result;
	}

	@Override
	public void deletedOrganization(Integer organizationId) {
		// TODO Auto-generated method stub
		
	}

}
