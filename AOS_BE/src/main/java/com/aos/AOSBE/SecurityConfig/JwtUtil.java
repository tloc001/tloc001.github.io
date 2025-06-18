package com.aos.AOSBE.SecurityConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.aos.AOSBE.Entity.Authorities;
import com.aos.AOSBE.Repository.AuthoritiesRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
	@Autowired
	private AuthoritiesRepository authorityRepository;

	@Value("${jwt.secret}")
	private String SECRET_KEY;

	public String generateToken(String username) {
//        get role cho token
		List<Authorities> auths = authorityRepository.findAllByEmail(username);
		System.err.println("generate token " + auths);
		List<String> roles = new ArrayList<>();
		for (Authorities auth : auths) {
			roles.add(auth.getRoles().getName());
		}
		return Jwts.builder().setSubject(username).setIssuedAt(new Date()).claim("roles", roles)
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 30 * 60)) // 30 min
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256).compact();
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token).getBody()
				.getSubject();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		Date expiration = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token)
				.getBody().getExpiration();
		return expiration.before(new Date());
	}
}
