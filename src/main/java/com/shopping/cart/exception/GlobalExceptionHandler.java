package com.shopping.cart.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = EmptyCartException.class)
	public ResponseEntity<Object> exception(EmptyCartException exception,WebRequest request) {
		ErrorResponse response=new ErrorResponse();
		response.setErrorCode(HttpStatus.NOT_FOUND.value());
		response.setErrorMessage(exception.getMessage());
		response.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<Object>(response,HttpStatus.NOT_FOUND);
	}

}
