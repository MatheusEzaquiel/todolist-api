package br.com.mbe.todolist.domain.task.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.mbe.todolist.domain.task.Task;

public record DetailTaskDTO(
		UUID id,
		String title,
		String description,
		Boolean done,
		String priority,
		String startAtDate,
		String startAtTime,
		String endAtDate,
		String endAtTime,
		Boolean enabled,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		Integer position) {
	


	public DetailTaskDTO(Task task, String priority, String startAtDate, String startAtTime, String endAtDate, String endAtTime, Integer position) {

		this(task.getId(), task.getTitle(), task.getDescription(), task.isDone() ,priority,
				startAtDate, startAtTime,
				endAtDate, endAtTime,
				task.isEnabled(), task.getCreatedAt(), task.getUpdatedAt(), position);
	}
	
}
