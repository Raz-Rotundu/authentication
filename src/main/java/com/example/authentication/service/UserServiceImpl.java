package com.example.authentication.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authentication.dto.UserDto;
import com.example.authentication.entity.UserEntity;
import com.example.authentication.repository.UserRepository;

/**
 * @author Razvan
 * UserService interface implementation that uses JPA repository
 */

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	

	@Override
	public Optional<UserDto> createUser(String email, String password, String userType) {
		// TODO Auto-generated method stub
		if(!userRepository.findByEmail(email).isEmpty()) {
			return Optional.empty();
		}
		
		UserEntity newUser = new UserEntity(UUID.randomUUID(), email, password, userType);
		return Optional.of(userRepository.save(newUser));
	}

	@Override
	public Optional<UserDto> findByEmail(String email) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean validateUser(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
