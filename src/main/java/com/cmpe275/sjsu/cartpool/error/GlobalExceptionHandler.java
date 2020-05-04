package com.cmpe275.sjsu.cartpool.error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cmpe275.sjsu.cartpool.responsepojo.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(NotFoundException ex){
		ErrorResponse err=new ErrorResponse();
		err.setMessage(ex.getMessage());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<ErrorResponse>(err, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleException(AlreadyExistsException ex){
		ErrorResponse err=new ErrorResponse();
		err.setMessage(ex.getMessage());
		err.setStatus(HttpStatus.CONFLICT.value());
		err.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<ErrorResponse>(err, HttpStatus.CONFLICT);
	}
	
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleException(BadRequestException ex){
		ErrorResponse err=new ErrorResponse();
		err.setMessage(ex.getMessage());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<ErrorResponse>(err, HttpStatus.BAD_REQUEST);
	}
	
//	@ExceptionHandler
//	public ResponseEntity<ErrorResponse> handleException(Exception ex){
//		ErrorResponse err=new ErrorResponse();
//		err.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
//		err.setTimestamp(System.currentTimeMillis());
//		return new ResponseEntity<ErrorResponse>(err, HttpStatus.SERVICE_UNAVAILABLE);
//	}
	
}