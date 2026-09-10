package com.mustafaberk.jwt;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.mustafaberk.enums.MessageType;
import com.mustafaberk.exception.BaseException;
import com.mustafaberk.exception.ErrorMessage;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	private static final String SECRET_KEY = "1C+noV8jaktjX38XOXZxnr9Eg2Zv7wbes+XmXMeRLgA=";
	
	public String generateAccessToken(UserDetails userDetails) {
		
		String role = userDetails.getAuthorities()
								  .stream()
								  .map( auth -> auth.getAuthority() )
								  .findFirst()
								  .orElseThrow( () -> new BaseException(new ErrorMessage(MessageType.ROLE_NOT_FOUND, null)) );
								 
		return Jwts.builder()
				.setIssuedAt(new Date())
				.setSubject(userDetails.getUsername())
				.setExpiration( Date.from( Instant.now().plus( Duration.ofMinutes(15) ) ) )
				.claim("role",role)
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Key getKey() {
		byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(bytes);
		
	}
	
	public Claims getClaims(String token) {
		
		return Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public <T> T exportToken(String token,Function<Claims, T> function) {

		Claims claims = getClaims(token);
		
		return function.apply(claims);
	}
	
	public String getUsernameByToken(String token) {
		return exportToken(token, Claims::getSubject ); // Claims::getSubject == x -> x.getSubject()
	}
	
	public String getRoleByToken(String token) {
		return exportToken(token, x -> x.get("role",String.class) );// kendi yazdigin claim'i boyle cagirirsin , ne turde dondurecekse o turu 2. parametre olarak belirt
	}
	
	public Date getExpirationByToken(String token) {
		return exportToken(token, Claims::getExpiration );
	}
	
	public boolean isTokenValid(String token) { // token hala gecerli ise true , token tarihi gecmis ise false doneriz
		return getExpirationByToken(token).after(new Date());
	}
	
}
