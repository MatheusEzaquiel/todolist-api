package br.com.mbe.todolist.exception;

public class UserNotFoundedException extends RuntimeException {
	
	public UserNotFoundedException(String message) {
		super(message);
	}
	
}
