package com.example.authentication.service;

import com.example.authentication.dto.TokenRequest;
import com.example.authentication.dto.TokenResponse;

public interface JwtService {
	
	TokenResponse getJwtToken(
			TokenRequest tokenRequest,
			String scope,
			String UserId);

}
