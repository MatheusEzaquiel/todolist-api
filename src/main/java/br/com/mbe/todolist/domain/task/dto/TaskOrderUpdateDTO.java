package br.com.mbe.todolist.domain.task.dto;

import java.util.List;
import java.util.UUID;

public record TaskOrderUpdateDTO(String checklistId, List<String> taskListId) {}
