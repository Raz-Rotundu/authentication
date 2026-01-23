package com.example.authentication.service;

/**
 * @author Razvan
 * Client service authorizes valid clients
 */
public interface ClientService {
	
	/**
	 * Authorizes client based on provided ID and secret key
	 * @param clientId the client's public key
	 * @param clientSecret the locally stored client secret key
	 * @return True if keys match, false otherwise
	 */
	boolean validateClient(String clientId, String clientSecret);
}
