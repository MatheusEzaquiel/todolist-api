package br.com.mbe.todolist.exception;

public class ChecklistNotFoundedException extends RuntimeException {
	
	public ChecklistNotFoundedException(String messager) {
		super(messager);
	}

}
