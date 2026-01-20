package com.example.authentication.service;

import java.util.Optional;

import com.example.authentication.dto.UserDto;

public interface UserService {
	
	Optional<UserDto> createUser(
			String email, String password, String userType);

	Optional<UserDto> findByEmail(String email);
	
	boolean validateUser(String email, String password);
}
