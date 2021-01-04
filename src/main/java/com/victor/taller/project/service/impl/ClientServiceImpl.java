package com.victor.taller.project.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.victor.taller.project.entity.ClientEntity;
import com.victor.taller.project.entity.OrganizationEntity;
import com.victor.taller.project.entity.ProfileEntity;
import com.victor.taller.project.entity.UserEntity;
import com.victor.taller.project.repository.jpa.ClientJpaRepository;
import com.victor.taller.project.service.ClientService;
import com.victor.taller.project.soa.bean.ClientBean;
import com.victor.taller.project.soa.bean.OrganizationBean;
import com.victor.taller.project.soa.bean.ProfileBean;
import com.victor.taller.project.soa.bean.UserBean;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientJpaRepository clientRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	
	@Override
	public ClientBean saveClient(ClientBean clientBean) {
		ClientEntity clientEntity = new ClientEntity();
		BeanUtils.copyProperties(clientBean, clientEntity);
		clientEntity.setPassword(bcrypt.encode(clientBean.getPassword()));
		clientEntity.setProfile(new ProfileEntity());
		clientEntity.getProfile().setId(clientBean.getProfile().getId());
		clientEntity.setOrganization(new OrganizationEntity());
		clientEntity.getOrganization().setId(clientBean.getOrganization().getId());
		
		clientEntity = clientRepository.save(clientEntity);
		clientBean.setId(clientEntity.getId());
		return clientBean;
	}

	@Override
	public ClientBean getClientById(Integer id) {
		ClientEntity clientEntity = clientRepository.findById(id).orElse(null);
		ClientBean clientBean = new ClientBean();
		BeanUtils.copyProperties(clientEntity, clientBean);
		clientBean.setProfile(new ProfileBean());
		clientBean.getProfile().setId(clientEntity.getProfile().getId());
		clientBean.setOrganization(new OrganizationBean());
		clientBean.getOrganization().setId(clientEntity.getOrganization().getId());
		return clientBean;
	}

	@Override
	public ClientBean getClientByUsername(String username) {
		ClientEntity clientEntity = clientRepository.findByUsername(username);
		ClientBean clientBean = new ClientBean();
		BeanUtils.copyProperties(clientEntity, clientBean);
		clientBean.setProfile(new ProfileBean());
		clientBean.getProfile().setId(clientEntity.getProfile().getId());
		clientBean.setOrganization(new OrganizationBean());
		clientBean.getOrganization().setId(clientEntity.getOrganization().getId());
		return clientBean;
	}

}
