package com.example.authentication.service;

/**
 * Client service implementation using hardcoded public and private keys
 */
public class ClientServiceImpl implements ClientService {
	
	// TODO Add valid ids and secrets to external config file 
	//and load them into a map using a @Configuration bean class
	private final String clientId = "A client id";
	private final String clientSecret = "A client secret";

	@Override
	public boolean validateClient(String clientId, String clientSecret) {
	
		return this.clientId == clientId
				&& this.clientSecret == clientSecret;
	}

}
