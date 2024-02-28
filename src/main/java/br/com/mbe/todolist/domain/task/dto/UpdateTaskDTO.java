package br.com.mbe.todolist.domain.task.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateTaskDTO(String title, String description, Boolean done, String priority,
		LocalDate startAtDate, LocalTime startAtTime,
		LocalDate endAtDate, LocalTime endAtTime,
		Boolean enabled) {
	

	public UpdateTaskDTO(Boolean enabled) {
        this(null, null, null, null, null, null, null, null, enabled);
    }
}
