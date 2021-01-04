package com.victor.taller.project.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.victor.taller.project.entity.ClientEntity;
import com.victor.taller.project.entity.UserEntity;
import com.victor.taller.project.repository.jpa.ClientJpaRepository;
import com.victor.taller.project.repository.jpa.UserJpaRepository;

@Service
public class CustomUserDetailsSerrvice implements UserDetailsService {

	@Autowired
	private UserJpaRepository userRepository;
	
	@Autowired
	private ClientJpaRepository clientRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//UserEntity user = userRepository.findByUsername(username);
		ClientEntity clientUser = clientRepository.findByUsername(username);
		if(clientUser == null) {
			throw new UsernameNotFoundException(String.format("Usuario no existe", username));
		}
		
		/*List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(user.getProfile().getLongDescription()));
		
		UserDetails userDetails = new User(user.getUsername(), user.getPassword(), roles);*/
		
		return UserPrincipal.create(clientUser);
	}

	
}
