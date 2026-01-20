package com.example.authentication.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.authentication.dto.UserDto;

/**
 * @author Razvan
 * UserService interface implementation that uses JPA repository
 */

@Service
public class UserServiceImpl implements UserService {

	@Override
	public Optional<UserDto> createUser(String email, String password, String userType) {
		// TODO Auto-generated method stub
		return Optional.empty();
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
