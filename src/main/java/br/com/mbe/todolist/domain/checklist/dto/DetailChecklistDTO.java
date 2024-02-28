package br.com.mbe.todolist.domain.checklist.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.mbe.todolist.domain.checklist.Checklist;

public record DetailChecklistDTO(UUID id, String title, Boolean enabled, LocalDateTime createdAt, LocalDateTime updatedAt) {

	public DetailChecklistDTO(Checklist checklist) {
		this(checklist.getId(), checklist.getTitle(), checklist.isEnabled(), checklist.getCreatedAt(), checklist.getUpdatedAt());
	}

}
