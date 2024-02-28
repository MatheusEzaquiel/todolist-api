package br.com.mbe.todolist.domain.task.dto;

import java.util.UUID;

public record EnableChecklistDTO(UUID checklistID, boolean enable) {

}
