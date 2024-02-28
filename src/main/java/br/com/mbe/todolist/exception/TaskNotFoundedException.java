package br.com.mbe.todolist.exception;

public class TaskNotFoundedException extends RuntimeException {
	
	public TaskNotFoundedException(String message) {
		super(message);
	}

}
