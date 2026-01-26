package com.example.authentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authentication.dto.TokenRequest;
import com.example.authentication.dto.TokenResponse;
import com.example.authentication.dto.UserDto;
import com.example.authentication.service.ClientService;
import com.example.authentication.service.JwtService;
import com.example.authentication.service.ScopeService;
import com.example.authentication.service.UserService;

/**
 * @author Razvan
 * Controller for the authentication microservice
 * Registers users and issues JWT tokens
 */
@RestController
@RequestMapping("/users")
public class UserController {
	
	// Services
	private final JwtService jwtService;
	private final UserService userService;
	private final ScopeService scopeService;
	private final ClientService clientService;
	
	public UserController(
			JwtService jwtService,
			UserService userService,
			ScopeService scopeService,
			ClientService clientService) {
		
		this.jwtService = jwtService;
		this.userService = userService;
		this.scopeService = scopeService;
		this.clientService = clientService;
		
	}
	
	/**
	 * Registers a user using information from the request
	 * @param userDto DTO representing the user to be created
	 * @return a response entity with OK or BAD_REQUEST status
	 */
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(
			@RequestBody UserDto userDto) {
		
		return userService.createUser(userDto.getEmail(), userDto.getPassword(), userDto.getUserType())
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.badRequest().build());
		
	}
	
	/**
	 * Issues a token based on a tokenRequest
	 * @param tokenRequest the token request
	 * @return a 200 response with the token, or an UNAUTHORIZED response otherwise
	 */
	@PostMapping(
			value = "/token",
			produces = "application/json",
			consumes = "application/json")
	public ResponseEntity<TokenResponse> createToken(
			@RequestBody TokenRequest tokenRequest) {
		
		// Invalid clients, nonexistent users, incorrect passwords are considered invalid
		if(!"password".equals(tokenRequest.grant_type())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		} 
		
		else if(!clientService.validateClient(tokenRequest.client_id(), tokenRequest.client_secret())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		else if(!userService.validateUser(tokenRequest.username(), tokenRequest.password())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		} 
		
		
		else {
			UserDto foundUser = userService.findByEmail(tokenRequest.username()).get();
			
			return ResponseEntity.ok(jwtService.getJwtToken(tokenRequest, 
					scopeService.findScope(foundUser.getUserType()), 
					foundUser.getEmail()));
		}
		
		

		
	}

}
