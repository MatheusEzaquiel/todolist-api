package br.com.mbe.todolist.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mbe.todolist.domain.checklist.Checklist;
import br.com.mbe.todolist.domain.checklist.dto.CreateChecklistDTO;
import br.com.mbe.todolist.domain.checklist.dto.DetailChecklistDTO;
import br.com.mbe.todolist.domain.checklist.dto.UpdateChecklistDTO;
import br.com.mbe.todolist.exception.ChecklistNotFoundedException;
import br.com.mbe.todolist.service.ChecklistService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/checklists")
public class ChecklistController {

	@Autowired
	ChecklistService checklistService;

	
	@GetMapping
	public ResponseEntity list() {

		try {

			var checklists = checklistService.list();

			return ResponseEntity.status(HttpStatus.OK).body(checklists);

		} catch (Exception e) {

			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to list all lists");

		}

	}

	
	@GetMapping("/user/{userId}")
	public ResponseEntity listByUser(@PathVariable UUID userId) {
		
		try {
			var checklists = checklistService.listByUser((UUID) userId);

			return ResponseEntity.status(HttpStatus.OK).body(checklists);
		} catch (Exception e) {
			System.out.println("ex: " + e.getMessage());
			return null;
		}
		
			

	}
	
	@GetMapping("/archiveds/user/{userId}")
	public ResponseEntity listDisabledByUser(@PathVariable UUID userId) {
		
		//System.out.println("uuid: " + userId);
	
			var checklists = checklistService.listDisabledByUser(userId);

			return ResponseEntity.status(HttpStatus.OK).body(checklists);
		
	}

	
	@GetMapping("/{id}")
	public ResponseEntity get(@PathVariable UUID id) {
			
		var checklists = checklistService.getById(id);
			
		return ResponseEntity.status(HttpStatus.OK).body(checklists);
		
	}
	

	@PostMapping
	public ResponseEntity create(@RequestBody CreateChecklistDTO data) {

			DetailChecklistDTO checklistCreated = checklistService.create(data);

			return ResponseEntity.status(HttpStatus.CREATED).body(checklistCreated);

	}
	
	@PutMapping("/{id}")
	public ResponseEntity<DetailChecklistDTO> update(@RequestBody UpdateChecklistDTO data, @PathVariable UUID id) throws Exception {
		
		DetailChecklistDTO checklistUpdated = checklistService.update(data, id);
			
		return ResponseEntity.status(HttpStatus.OK).body(checklistUpdated);

	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity delete(@PathVariable UUID id) {
		
		try {
			
			DetailChecklistDTO chekilistDeleted = checklistService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(chekilistDeleted);
			
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to create the list");
		}
		
	}
	
	@PutMapping("/unarchive/{id}")
	@Transactional
	public ResponseEntity unarchive(@PathVariable UUID id) {
		
		try {
			
			checklistService.unarchive(id);
			
			return ResponseEntity.status(HttpStatus.OK).body("List unarchived");
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to unarchive the list");
		}
		
	}

}