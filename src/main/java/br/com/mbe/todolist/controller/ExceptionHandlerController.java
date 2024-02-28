package br.com.mbe.todolist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;

import br.com.mbe.todolist.exception.ChecklistNotFoundedException;
import br.com.mbe.todolist.exception.Date1BiggerThanDate2Exception;
import br.com.mbe.todolist.exception.LoginFailedException;
import br.com.mbe.todolist.exception.ShortPasswordException;
import br.com.mbe.todolist.exception.TaskNotFoundedException;
import br.com.mbe.todolist.exception.TokenValidationException;
import br.com.mbe.todolist.exception.UserAlredyExistException;
import br.com.mbe.todolist.exception.UserNotFoundedException;
import br.com.mbe.todolist.exception.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHandlerController {

	public ResponseEntity<ErrorDTO> handleException(Exception ex, HttpServletRequest request,
			HttpServletResponse response) {
		ErrorDTO error = new ErrorDTO(ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
	
	@ResponseBody
	@ExceptionHandler(TokenValidationException.class)
	public ResponseEntity<String> handleTokenValidationException(TokenValidationException ex) {
		String errorMessage = "Erro ao validar o token: " + ex.getMessage();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMostSpecificCause().getMessage());
	}

	@ResponseStatus(code = HttpStatus.EARLY_HINTS)
	@ResponseBody
	@ExceptionHandler(ChecklistNotFoundedException.class)
	public ResponseEntity<ErrorDTO> checklistNotFounded(ChecklistNotFoundedException ex) {

		ErrorDTO error = new ErrorDTO(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

	}

	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(UserAlredyExistException.class)
	public ResponseEntity<ErrorDTO> userAlredyExist(UserAlredyExistException ex) {

		ErrorDTO error = new ErrorDTO(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);

	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ResponseBody
	@ExceptionHandler(UserNotFoundedException.class)
	public ResponseEntity<ErrorDTO> userNotFounded(UserNotFoundedException ex) {

		ErrorDTO error = new ErrorDTO(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(TaskNotFoundedException.class)
	public ResponseEntity<ErrorDTO> taskNotFounded(TaskNotFoundedException ex) {

		ErrorDTO error = new ErrorDTO(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	@ExceptionHandler(LoginFailedException.class)
	public ResponseEntity<ErrorDTO> loginFailed(LoginFailedException ex) {

		System.out.println("login failed");

		ErrorDTO error = new ErrorDTO(ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ShortPasswordException.class)
	public ResponseEntity<ErrorDTO> shortPassword(ShortPasswordException ex) {

		ErrorDTO error = new ErrorDTO(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

	}
	
	
	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	@ExceptionHandler(Date1BiggerThanDate2Exception.class)
	public ResponseEntity<ErrorDTO> dateTaskFailed(Date1BiggerThanDate2Exception ex) {
		ErrorDTO error = new ErrorDTO(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
	}

}
