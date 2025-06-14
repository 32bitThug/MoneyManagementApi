package com.rs.money.management.api.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtServices {
	
	private static final String SECRET_KEY = "78214125442A472D4B6150645267556B58703273357638792F423F4528482B4D";
	
	
	public Key getSigningKey() {
		byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(decode);
	}

	public Claims extractAllClaims(String jwtToken) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(jwtToken)
				.getBody();
	}

	// extracting expiration and email  from token
	public <T> T extractClaims(String token, Function<Claims, T> claimsResolver ) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String extractUserName(String jwtToken) {
		return extractClaims(jwtToken, Claims::getSubject);
	}
	
	public Date extractExpiration(String jwtToken) {
		return extractClaims(jwtToken, Claims::getExpiration);
	}

	private boolean isTokenExpired(String jwtToken) {
		return extractExpiration(jwtToken).before(new Date());
	}
	
	// checks the username aswell as token expiration
	public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
		final String username = extractUserName(jwtToken);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
	}
	
	/*Creates a new JWT for a user and adds extra data (extraClaims) into the token.
	Useful if you want to store more information inside the token (example: user roles).*/
	public String generateToken(  UserDetails userDetails) {
		return Jwts
				.builder()
				.setClaims(new HashMap<String,Object>())
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*6000*24))
				.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)),SignatureAlgorithm.HS256)
				.compact();
				
}



}
