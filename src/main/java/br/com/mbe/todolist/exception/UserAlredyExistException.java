package br.com.mbe.todolist.exception;

public class UserAlredyExistException extends RuntimeException {
	
	public UserAlredyExistException(String message) {
		super(message);
	};
}
