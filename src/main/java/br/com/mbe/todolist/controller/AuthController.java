package br.com.mbe.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mbe.todolist.domain.auth.dto.AuthenticationDTO;
import br.com.mbe.todolist.domain.auth.dto.LoginResponseDTO;
import br.com.mbe.todolist.domain.auth.dto.RegisterDTO;
import br.com.mbe.todolist.domain.user.User;
import br.com.mbe.todolist.domain.user.dto.DetailUserDTO;
import br.com.mbe.todolist.exception.LoginFailedException;
import br.com.mbe.todolist.repository.IUserRepository;
import br.com.mbe.todolist.service.UserService;
import br.com.mbe.todolist.service.auth.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	UserService userService;
	
	@Autowired
	IUserRepository userRepos;
	
	@Autowired
	TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody AuthenticationDTO data) {

		UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.username(),
				data.password());
		
		//var auth = this.authenticationManager.authenticate(usernamePassword);
		
		try {
		    var auth = this.authenticationManager.authenticate(usernamePassword);
		    // Autenticação bem-sucedida, continue o processo
		    System.out.println("Authentication successful");
		    System.out.println("generating token");
			
			var token = tokenService.generateToken((User) auth.getPrincipal());
			
			User user = (User) userRepos.findByUsername(data.username());

			return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO("Login realized with success", token, user.getId()));
			
		} catch (AuthenticationException e) {
			throw new LoginFailedException("The user doesn't exists or the credentials are wrong");
		}
		
		
		

	}

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody RegisterDTO data) {

		DetailUserDTO userCreated = userService.create(data);

		return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);

	}

}
