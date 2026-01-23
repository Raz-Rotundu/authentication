package com.example.authentication.service;

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
