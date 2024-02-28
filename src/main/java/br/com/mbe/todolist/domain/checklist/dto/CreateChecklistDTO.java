package br.com.mbe.todolist.domain.checklist.dto;

import java.util.UUID;

public record CreateChecklistDTO(String title, UUID userId) {
	
}
