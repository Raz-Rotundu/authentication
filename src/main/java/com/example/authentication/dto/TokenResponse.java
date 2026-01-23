package com.example.authentication.dto;

/**
 * @author Razvan 
 * Encapsulates information for a token response
 */
public record TokenResponse(
		String access_token,
		String token_type,
		String expires_in,
		String scope) 
{}
