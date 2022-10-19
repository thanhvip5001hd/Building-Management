package com.laptrinhjavaweb.controlleradvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.laptrinhjavaweb.bean.ErrorResponseBean;
import com.laptrinhjavaweb.customexception.FieldRequiredException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ErrorResponseBean> handleArithmeticException(ArithmeticException ex, WebRequest request) {	
		ErrorResponseBean result = new ErrorResponseBean();
		result.setError(ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add("1 không chia được cho 0");
		result.setDetails(details); 
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FieldRequiredException.class)
    public ResponseEntity<ErrorResponseBean> handleFieldRequiredException(FieldRequiredException ex, WebRequest request) {
		ErrorResponseBean result = new ErrorResponseBean();
		result.setError(ex.getMessage());
		List<String> details = new ArrayList<>();
		//details.add("1 không chia được cho 0");
		result.setDetails(details);
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
}
