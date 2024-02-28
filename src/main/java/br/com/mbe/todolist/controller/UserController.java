package br.com.mbe.todolist.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mbe.todolist.domain.user.dto.DetailUserDTO;
import br.com.mbe.todolist.domain.user.dto.ListUserDTO;
import br.com.mbe.todolist.domain.user.dto.UpdateUserDTO;
import br.com.mbe.todolist.domain.user.dto.UserActivityDTO;
import br.com.mbe.todolist.domain.user.dto.UserWithToken;
import br.com.mbe.todolist.service.UserService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity list() {
		
		try {

			List<ListUserDTO> users = userService.list();

			return ResponseEntity.status(HttpStatus.OK).body(users);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to list all users");
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity get(@PathVariable UUID id) {
		
		DetailUserDTO userSelected = userService.getById(id);
			
		return ResponseEntity.status(HttpStatus.OK).body(userSelected);
		
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity update(@PathVariable UUID id, @RequestBody UpdateUserDTO data) {

		UserWithToken userUpdated = userService.updateById(data, id);
			
		return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
		
	}
	

	@GetMapping("/activity/{id}")
	public ResponseEntity activity(@PathVariable UUID id) {
		
		UserActivityDTO userActivity = userService.getMyActivity(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(userActivity);
	}
	
}
