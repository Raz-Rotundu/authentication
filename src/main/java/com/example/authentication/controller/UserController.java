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
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(
			@RequestBody UserDto userDto) {
		
		return userService.createUser(userDto.getEmail(), userDto.getPassword(), userDto.getUserType())
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.badRequest().build());
		
	}
	
	
	@PostMapping(
			value = "/token",
			produces = "application/json",
			consumes = "application/json")
	public ResponseEntity<TokenResponse> createToken(
			@RequestBody TokenRequest tokenRequest) {
		
		if(!"password".equals(tokenRequest.grant_type())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		if(!clientService.validateClient(tokenRequest.client_id(), tokenRequest.client_secret())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		if(!userService.validateUser(tokenRequest.username(), tokenRequest.password())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		UserDto foundUser = userService.findByEmail(tokenRequest.username()).get();
		
		return ResponseEntity.ok(jwtService.getJwtToken(tokenRequest, 
				scopeService.findScope(foundUser.getUserType()), 
				foundUser.id()));
		
	}

}
