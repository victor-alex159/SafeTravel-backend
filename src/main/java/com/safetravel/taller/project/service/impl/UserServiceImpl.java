package com.safetravel.taller.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.safetravel.taller.project.entity.ProfileEntity;
import com.safetravel.taller.project.entity.UserEntity;
import com.safetravel.taller.project.repository.jpa.UserJpaRepository;
import com.safetravel.taller.project.service.UserService;
import com.safetravel.taller.project.soa.bean.ProfileBean;
import com.safetravel.taller.project.soa.bean.UserBean;
import com.safetravel.taller.project.util.EncryptUtil;

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
		if(userBean.getId() == null) {
			userEntity.setPassword(bcrypt.encode(userBean.getPassword()));			
		} else {
			userEntity.setPassword((userBean.getPassword()));
		}
		//userEntity.setPassword(EncryptUtil.encryptMD5(userBean.getPassword()));
		userEntity.setProfile(new ProfileEntity());
		userEntity.getProfile().setId(userBean.getProfile().getId());
		
		userEntity = userRepository.save(userEntity);
		userBean.setId(userEntity.getId());
		return userBean;
	}

	@Override
	public UserBean getUserById(Integer id) {
		UserEntity userEntity = userRepository.findById(id).orElse(null);
		UserBean userBean = new UserBean();
		if(userEntity != null) {
			BeanUtils.copyProperties(userEntity, userBean);
			userBean.setProfile(new ProfileBean());
			userBean.getProfile().setId(userEntity.getProfile().getId());
			userBean.getProfile().setLongDescription(userEntity.getProfile().getLongDescription());
			userBean.getProfile().setType(userEntity.getProfile().getType());
			return userBean;
		}
		
		return null;
	}

	@Override
	public UserBean getUserByUsername(String username) {
		UserEntity userEntity = userRepository.findByUsername(username);
		UserBean userBean = new UserBean();
		BeanUtils.copyProperties(userEntity, userBean);
		userBean.setProfile(new ProfileBean());
		userBean.getProfile().setId(userEntity.getProfile().getId());
		return userBean;
	}

	@Override
	public UserBean getUserByEmail(String email) {
		UserEntity userEntity = userRepository.findUserByEmail(email);
		UserBean userBean = new UserBean();
		if(userEntity != null) {
			BeanUtils.copyProperties(userEntity, userBean);
			userBean.setProfile(new ProfileBean());
			userBean.getProfile().setId(userEntity.getProfile().getId());
			return userBean;
		}
		return null;
	}

	@Override
	public UserBean getUserByTokenResetPassword(String tokenResetPassword) {
		UserEntity userEntity = userRepository.findUserByTokenResetPassword(tokenResetPassword);
		UserBean userBean = new UserBean();
		if(userEntity != null) {
			BeanUtils.copyProperties(userEntity, userBean);
			userBean.setProfile(new ProfileBean());
			userBean.getProfile().setId(userEntity.getProfile().getId());
			return userBean;
		}
		return null;
	}

	@Override
	public List<UserBean> getAllUsers() {
		List<UserEntity> listUsersEntity = (List<UserEntity>) userRepository.findAll();
		List<UserBean> listUserBean = new ArrayList<>();
		if(listUsersEntity != null) {
			listUsersEntity.forEach(userEntity -> {
				UserBean userBean = new UserBean();
				BeanUtils.copyProperties(userEntity, userBean);
				if(userEntity.getProfile() != null) {
					userBean.setProfile(new ProfileBean());
					userBean.getProfile().setId(userEntity.getProfile().getId());
					userBean.getProfile().setLongDescription(userEntity.getProfile().getLongDescription());
					userBean.getProfile().setType(userEntity.getProfile().getType());
				}
				listUserBean.add(userBean);
			});
			return listUserBean;
		}
		return null;
	}

}
