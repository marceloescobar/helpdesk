package com.mescobar.helpdesk.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex,
			HttpServletRequest request) {
		StandardError error = new StandardError("Object not Found", ex.getMessage(), HttpStatus.NOT_FOUND.value(),
				request.getRequestURI(), System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(DuplicateCPForEmailException.class)
	public ResponseEntity<StandardError> duplicateCPForEmailException(DuplicateCPForEmailException ex,
			HttpServletRequest request) {
		StandardError error = new StandardError("Violação de dados", ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
				request.getRequestURI(), System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationErrors(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		ValidationError errors = new ValidationError("Validador campos", "Erro na validação dos campos",
				HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), System.currentTimeMillis());

		
		ex.getBindingResult().getFieldErrors().stream().forEach((k) -> errors.addError(k.getField(), k.getDefaultMessage()));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
}
