package com.example.authentication.service;

import com.example.authentication.dto.TokenRequest;
import com.example.authentication.dto.TokenResponse;

/**
 * @author Razvan
 * Service to generate JWT tokens from provided information
 */
public interface JwtService {
	
	/**
	 * Generates JWT token from information provided
	 * @param tokenRequest a record encapsulating information from a token request
	 * @param scope the scope to issue to the token
	 * @param UserId the userId with which to issue the token
	 * @return a valid JWT token for the given user
	 */
	TokenResponse getJwtToken(
			TokenRequest tokenRequest,
			String scope,
			String UserId);

}
