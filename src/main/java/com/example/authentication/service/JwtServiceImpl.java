package com.example.authentication.service;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import com.example.authentication.dto.TokenRequest;
import com.example.authentication.dto.TokenResponse;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class JwtServiceImpl implements JwtService {

	/**
	 * Generates an RSA key pair for future use
	 * Outputs the keys to screen with System.out
	 * @return RSA key pair
	 */
	private static KeyPair generateRsaKey() {
		
		try {
			
			// Configure key factory
			KeyPairGenerator keyPairGenerator = 
					KeyPairGenerator.getInstance("RSA");
			
			// Generate RSA key
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			
			String privateKeyString = 
					Base64.getEncoder().encodeToString(
							keyPair.getPrivate().getEncoded());
			
			String publicKeyString = 
					Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
			
			// Output the keys somewhere
			System.out.println("PRIVATE KEY: "
					+  privateKeyString);
			
			System.out.println("PUBLIC KEY: "
					+ publicKeyString);
			
			return keyPair;
			
		} catch(NoSuchAlgorithmException ex) {
			throw new IllegalStateException();
		}

		
	}
	
	/**
	 * Loads an RSA key pair from persistence into memory
	 * @return RSA key pair
	 */
	private static KeyPair loadRsaKey() {
		
		// Decode Base64 encoded strings
		// TODO place keys in external config file or DB
		byte[] privateKeyBytes = Base64.getDecoder()
				.decode("Insert Base64 private key here");
		
		byte[] publicKeyBytes = Base64.getDecoder()
				.decode("Insert Base64 public key here");
		
		// Generate privateKey from decoded bytes
		PKCS8EncodedKeySpec privateKeySpec = 
				new PKCS8EncodedKeySpec(privateKeyBytes);
		
		KeyFactory keyFactory = null;
		
		try {
			
			keyFactory = KeyFactory.getInstance("RSA");
			
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException();
		}
		
		PrivateKey privateKey = null;
		
		try {
			
			privateKey = keyFactory.generatePrivate(privateKeySpec);
			
		} catch (InvalidKeySpecException ex) {
			throw new RuntimeException();
		}
		
		// Generate publicKey from decoded bytes
		X509EncodedKeySpec publicKeySpec = 
				new X509EncodedKeySpec(publicKeyBytes);
		
		PublicKey publicKey = null;
		
		try {
			
			publicKey = 
					keyFactory.generatePublic(publicKeySpec);
			
		} catch (InvalidKeySpecException ex) {
			
			throw new RuntimeException();
		}
		
		// Create and return the keypair
		return new KeyPair(publicKey, privateKey);
	}
	
	@Override
	public TokenResponse getJwtToken(TokenRequest tokenRequest, String scope, String UserId) {
		
		try {
			// Load keys into memory
			KeyPair keyPair = loadRsaKey();
			RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
			PrivateKey privateKey = keyPair.getPrivate();
			
			// Token expires 60 minutes from when issued
			Date issueTime = new Date();
			Date expiry = new Date(System.currentTimeMillis() + 3600000);
			
			// Set token payload
			JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
					.subject(UserId)
					.issueTime(issueTime)
					.claim("scope", scope)
					.expirationTime(expiry)
					.build();
			
			// If there was an external key generation microservice, it would return the key id here
			String keyId = 
					"A hardcoded key ID";
			
			// Actually building the token
			SignedJWT signedJwt = new SignedJWT(
					new JWSHeader.Builder(JWSAlgorithm.RS256)
					.keyID(keyId)
					.build(), claimsSet);
			
			// Signing
			signedJwt.sign(new RSASSASigner(privateKey));
			
			
			// To String
			String jwtToken = signedJwt.serialize();
			
			return new TokenResponse(jwtToken, "Bearer", "3600", scope);
			
		} catch(Exception e) {
			throw new RuntimeException("Error creating token", e);
		}
	}

}
