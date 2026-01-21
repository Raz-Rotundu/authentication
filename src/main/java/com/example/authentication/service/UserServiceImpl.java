package com.example.authentication.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authentication.dto.UserDto;
import com.example.authentication.dto.utils.UserConverter;
import com.example.authentication.entity.UserEntity;
import com.example.authentication.repository.UserRepository;

/**
 * @author Razvan
 * UserService interface implementation that uses JPA repository
 */

@Service
@Qualifier("jpaImpl")
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

		// If user with email already exists, return empty optional
		if(!userRepository.findByEmail(email).isEmpty()) {
			return Optional.empty();
		}
		
		UserEntity newUser = new UserEntity(UUID.randomUUID(),
				email, 
				passwordEncoder.encode(password), 
				userType);
		
		return Optional.of(userRepository.save(newUser))
				.map(UserConverter::toUserDto);
	}

	@Override
	public Optional<UserDto> findByEmail(String email) {
		
		return userRepository.findByEmail(email)
				.map(UserConverter::toUserDto);
	}

	@Override
	public boolean validateUser(String email, String password) {
		
		return userRepository.findByEmail(email)
				.map(user ->
						passwordEncoder.matches(password, user.getPassword()))
				.orElse(false);

	}

}
