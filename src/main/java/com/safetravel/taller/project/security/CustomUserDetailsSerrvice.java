package com.safetravel.taller.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.safetravel.taller.project.entity.UserEntity;
import com.safetravel.taller.project.repository.jpa.UserJpaRepository;

@Service
public class CustomUserDetailsSerrvice implements UserDetailsService {

	@Autowired
	private UserJpaRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username);
		//ClientEntity clientUser = clientRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(String.format("Usuario no existe", username));
		}
		
		/*List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(user.getProfile().getLongDescription()));
		
		UserDetails userDetails = new User(user.getUsername(), user.getPassword(), roles);*/
		
		return UserPrincipal.create(user);
	}

	
}
