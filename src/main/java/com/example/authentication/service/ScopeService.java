package com.example.authentication.service;

/**
 * @author Razvan
 * Provides the JWT scope tokens based on the user types
 * Determines permissions based on user type
 */
public interface ScopeService {

	/**
	 * Determines the access scope based on the type of the user
	 * @param userType string denoting the user type
	 * @return string representing the access scope of the given user
	 */
	String findScope(String userType);
}
