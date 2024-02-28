package br.com.mbe.todolist.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.mbe.todolist.repository.IUserRepository;

@Service
public class AuthorizationService implements UserDetailsService {
	
	@Autowired
	IUserRepository userRepos;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepos.findByUsername(username);
	}
	
}
