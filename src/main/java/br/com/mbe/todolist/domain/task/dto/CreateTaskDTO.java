package br.com.mbe.todolist.domain.task.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record CreateTaskDTO(
		String title,
		String description,
		String priority,
		LocalDate startAtDate,
		LocalTime startAtTime,
		LocalDate endAtDate,
		LocalTime endAtTime,
		UUID checklistId,
		Boolean enabled) {

}
