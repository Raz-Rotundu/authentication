package com.example.authentication.service;

import java.util.Optional;

import com.example.authentication.dto.UserDto;

/**
 * @author Razvan
 * Service interface for user authentication service
 */
public interface UserService {
	
	/**
	 * Register a new user and save in database
	 * @param email user's email
	 * @param password user's password
	 * @param userType user's userType
	 * @return a new UserDto if creation was successful
	 */
	Optional<UserDto> createUser(
			String email, String password, String userType);

	/**
	 * Retrieve a user based on their email
	 * @param email the email of the user to be retrieved
	 * @return UserDto representing user, null if user with given email does not exist
	 */
	Optional<UserDto> findByEmail(String email);
	
	/**
	 * Validates a user based on provided email and password
	 * @param email user's email
	 * @param password user's password
	 * @return true if email and password match existing user, false otherwise
	 */
	boolean validateUser(String email, String password);
}
