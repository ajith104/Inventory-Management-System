package com.inventorymanagement.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	@Value("${jwt.secret}")
	private String secretKey;
	
	@Value("${jwt.expiration}")
	private long jwtExpiration;
	
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(io.jsonwebtoken.io.Decoders.BASE64.decode(secretKey));
	}
	
	public String generateToken(String username) {
		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + jwtExpiration);
		
		return Jwts.builder()
				.subject(username)
				.issuedAt(now)
				.expiration(expirationDate)
				.signWith(getSigningKey())
				.compact();
	}
	
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	public boolean isTokenValid(String token, String username) {
		String tokenUsername = extractUsername(token);
		return tokenUsername.equals(username) && extractAllClaims(token)
																		.getExpiration()
																		.after(new Date());
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
							.verifyWith(getSigningKey())
							.build()
							.parseSignedClaims(token)
							.getPayload();
	}
}


