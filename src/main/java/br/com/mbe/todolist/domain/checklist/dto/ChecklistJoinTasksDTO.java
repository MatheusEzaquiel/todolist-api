package br.com.mbe.todolist.domain.checklist.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.mbe.todolist.domain.task.dto.DetailTaskDTO;


public record ChecklistJoinTasksDTO(
		UUID id,
		String title,
		Boolean enabled,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		List<DetailTaskDTO> tasks) {

	public ChecklistJoinTasksDTO(DetailChecklistDTO checklist, List<DetailTaskDTO> tasks) {
		this(checklist.id(), checklist.title(), checklist.enabled(), checklist.createdAt(), checklist.updatedAt(), tasks);
	}

	

}
