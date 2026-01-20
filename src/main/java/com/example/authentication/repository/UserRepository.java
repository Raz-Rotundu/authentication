package com.example.authentication.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.authentication.entity.UserEntity;

/**
 * @author Razvan
 * Repository interface using JpaRepository to access database
 */

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

	/**
	 * Custom function to find users by email
	 * @param email the email of the user to be found
	 * @return UserEntity representing user with given email, or null if not found
	 */
	Optional<UserEntity> findByEmail(String email);
}
