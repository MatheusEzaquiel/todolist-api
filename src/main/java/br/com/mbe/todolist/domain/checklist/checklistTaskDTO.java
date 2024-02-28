package br.com.mbe.todolist.domain.checklist;

import java.util.List;

import br.com.mbe.todolist.domain.task.Task;

public record checklistTaskDTO(String checklist, List<String> tasks) {

}
