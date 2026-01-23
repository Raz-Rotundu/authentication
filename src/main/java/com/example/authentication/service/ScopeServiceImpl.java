package com.example.authentication.service;

/**
 * @author Razvan
 * A hardcoded implementation of scoping based on user type. Could have it configured on external file in the future
 */
public class ScopeServiceImpl implements ScopeService {

	@Override
	public String findScope(String userType) {
		
		if(userType.equals("tenant")) {
			
			return "rental_properties:read";
			
		} else if(userType.equals("landlord")) {
			
			return "rental_properties:read rental_properties:write";
			
		} else {
			
			return null;
		}		
	}

}
