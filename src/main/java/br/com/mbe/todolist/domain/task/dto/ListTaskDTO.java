package br.com.mbe.todolist.domain.task.dto;

import java.util.UUID;

import br.com.mbe.todolist.domain.task.Task;

public record ListTaskDTO(
		UUID id,
		String title,
		String description,
		Boolean done,
		String priority,
		String startAtDate, String startAtTime,
		String endAtDate, String endAtTime
		) {

	public ListTaskDTO(Task task, String priority, String startAtDate, String startAtTime, String endAtDate, String endAtTime) {
		this(
				task.getId(),
				task.getTitle(),
				task.getDescription(),
				task.isDone(),
				priority,
				startAtDate, startAtTime,
				startAtDate, startAtTime
		);
	}

	public ListTaskDTO(ListTaskDTO task, String priority) {
		this(task.id, task.title, task.description, task.done, priority, task.startAtDate, task.startAtTime,
				task.endAtDate, task.endAtTime);
	}
	
	
}
