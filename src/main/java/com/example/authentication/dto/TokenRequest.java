package com.example.authentication.dto;

/**
 * @author Razvan
 * Encapsulates the information from a token request
 */
public record TokenRequest(
		String grant_type,
		String username,
		String password,
		String client_id,
		String client_secret) 

{}
