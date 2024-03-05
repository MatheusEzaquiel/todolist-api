package br.com.mbe.todolist.service.auth;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mbe.todolist.domain.user.User;
import br.com.mbe.todolist.exception.TokenValidationException;
import br.com.mbe.todolist.exception.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletResponse;



@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	public String secret;
	
	public String issuer = "auth-api";
	
	public String generateToken(User user) {
		
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			String token = JWT.create()
					.withIssuer(issuer)
					.withSubject(user.getUsername())
					.withExpiresAt(genExpirationDate())
					.sign(algorithm);
			
			return token;
			
		} catch (JWTCreationException e) {
			throw new RuntimeException("Error to generate a token", e);
		}
		
	}
	
	
	public String validateToken(String token, HttpServletResponse response) throws IOException {
		
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			return JWT.require(algorithm)
					.withIssuer(issuer)
					.build()
					.verify(token)
					.getSubject();
		
		} catch (JWTVerificationException ex) {
			
			response.setStatus(403);
			response.setContentType("application/json");
			
			ErrorDTO errorDTO = new ErrorDTO("The token is invalid");
	        String errorJson = new ObjectMapper().writeValueAsString(errorDTO);
	        response.getWriter().write(errorJson);
	        
			throw new TokenValidationException("The token is invalid or expired");
		}
	}
	
	
	private Instant genExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	
	
	
}
