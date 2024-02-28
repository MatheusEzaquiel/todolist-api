package br.com.mbe.todolist.exception;

public class LoginFailedException extends RuntimeException {
	
	public LoginFailedException(String message) {
		super(message);
	}
}
