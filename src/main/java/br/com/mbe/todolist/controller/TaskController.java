package br.com.mbe.todolist.controller;

import java.util.List;
import java.util.UUID;

import br.com.mbe.todolist.domain.task.dto.*;
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

import br.com.mbe.todolist.domain.task.Task;
import br.com.mbe.todolist.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;


@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;

	@GetMapping
	public ResponseEntity list(HttpServletRequest request) {

		try {

			List<ListTaskDTO> tasks = taskService.list(request);
			
			return ResponseEntity.status(HttpStatus.OK).body(tasks);

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to list task: " + ex.getMessage());
		}

	}
	
	@GetMapping("/{id}")
	public ResponseEntity get(@PathVariable UUID id) {
		
			DetailTaskDTO taskSelected = taskService.getById(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(taskSelected);
		
	}

	@PostMapping
	public ResponseEntity create(@RequestBody CreateTaskDTO data, HttpServletRequest request) {
		Task taskCreated = taskService.create(data);
		return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity update(@RequestBody UpdateTaskDTO data, @PathVariable UUID id) throws Exception {
		DetailTaskDTO taskUpdated = taskService.update(data, id);
		return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);
	}
	
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable UUID id) {
		
		try {

			DetailTaskDTO taskUpdated = taskService.delete(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to create task");
		}
		
	}

	@PutMapping("/reorder")
	public List<Task> reorderTasks(@RequestBody TaskOrderUpdateDTO data) {
		return taskService.updateTaskOrder(data);
	}

}