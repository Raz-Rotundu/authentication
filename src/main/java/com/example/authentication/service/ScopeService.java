package com.example.authentication.service;

/**
 * @author Razvan
 * Provides the JWT scope tokens based on the user types
 * Determines permissions based on user type
 */
public interface ScopeService {

	String findScope(String userType);
}
