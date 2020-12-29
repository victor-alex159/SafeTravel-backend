package com.victor.taller.project.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.victor.taller.project.entity.ProfileEntity;
import com.victor.taller.project.entity.UserEntity;
import com.victor.taller.project.repository.UserJpaRepository;
import com.victor.taller.project.service.UserService;
import com.victor.taller.project.soa.bean.UserBean;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserJpaRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Override
	public UserBean saveUser(UserBean userBean) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userBean, userEntity);
		userEntity.setPassword(bcrypt.encode(userBean.getPassword()));
		userEntity.setProfile(new ProfileEntity());
		userEntity.getProfile().setId(userBean.getProfile().getId());
		
		userEntity = userRepository.save(userEntity);
		userBean.setId(userEntity.getId());
		return userBean;
	}

}
