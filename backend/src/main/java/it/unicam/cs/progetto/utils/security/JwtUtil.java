package it.unicam.cs.progetto.utils.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.unicam.cs.progetto.user.UserModel;
import it.unicam.cs.progetto.user.dto.UserToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

	@Value("${jwt.expiration}")
	private int jwtExpiration;
	@Value("${jwt.secret}")
	private String secret;

	public String generateToken(UserModel user){
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, user.getEmail());
	}

	private String createToken(Map<String, Object> claims, String email) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(email)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (this.jwtExpiration * 60 * 60)))
				.signWith(SignatureAlgorithm.HS256, this.secret)
				.compact();
	}
	
	public boolean validateToken(String token, UserDetails user){
		final String email = this.extractUsername(token);
		return email.equals(user.getUsername()) && !this.isTokenExpired(token);
	}

	public Date extractExpiration(String token) {
		return this.extractClaim(token, Claims::getExpiration);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public UserToken getUserToken(String userToken){
		return new UserToken(userToken.substring(7));
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token){
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		return this.extractExpiration(token).before(new Date());
	}
}
