package br.com.mbe.todolist.exception;

public class ShortPasswordException extends RuntimeException {
	
	public ShortPasswordException(String message) {
		super(message);
	}

}
