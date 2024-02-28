package br.com.mbe.todolist.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.mbe.todolist.controller.ExceptionHandlerController;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class FilterChainExceptionHandler extends OncePerRequestFilter {
	
	@Autowired
	private ExceptionHandlerController resolver;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			
			filterChain.doFilter(request, response);
			
		} catch (Exception e) {
			resolver.handleException(e, request, response);
		}
	}
	

}
