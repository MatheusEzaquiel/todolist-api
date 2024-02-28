package br.com.mbe.todolist.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mbe.todolist.domain.taskPriority.TaskPriority;
import br.com.mbe.todolist.domain.taskPriority.TaskPriorityDTO;
import br.com.mbe.todolist.repository.ITaskPriorityRepository;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/task-priority")
public class TaskPriorityController {
	
	@Autowired
	ITaskPriorityRepository taskPriorityRepos;
	
	@GetMapping
	public List<TaskPriority> list() {
		return taskPriorityRepos.findAll();//findByEnabled(1);
	}
	//.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
	@PostMapping
	public ResponseEntity create(@RequestBody TaskPriorityDTO data) {
		
		TaskPriority taskPriority = new TaskPriority(data);
		
		TaskPriority prioritySaved = taskPriorityRepos.save(taskPriority);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(prioritySaved);
	}
	
	@Transactional
	@PutMapping("/{priorityTaskId}")
	public ResponseEntity update(@PathVariable String priorityTaskId, @RequestBody TaskPriority data) {
		
		UUID id = UUID.fromString(priorityTaskId);
		
		Optional<TaskPriority> priorityToUpdate = taskPriorityRepos.findById(id);
		
		if(priorityToUpdate.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Priority was not founded!"); 
		}
		
		if(data.getPriority() != null) {
			priorityToUpdate.get().setPriority(data.getPriority());
		}
		
		if(data.getEnabled() != null) {
			priorityToUpdate.get().setEnabled(data.getEnabled());
		}
		
		priorityToUpdate.get().setUpdatedAt(LocalDateTime.now());
		
		taskPriorityRepos.save(priorityToUpdate.get());
	
		
		return ResponseEntity.status(HttpStatus.OK).body("updated!");
	}
}
