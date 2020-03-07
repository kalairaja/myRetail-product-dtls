package com.target.myretail.exception;

import java.util.List;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.target.myretail.model.Product;
import com.target.myretail.utils.ProductUtils;

@Component
@ControllerAdvice
public class CustomExceptionHandler {

	private final org.slf4j.Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public final ResponseEntity<Product> handleArgTypeMiExceptionHandler(MethodArgumentTypeMismatchException ex){
		logger.error("CustomExceptionHandler::handleArgTypeMiExceptionHandler {}", ex.getMessage());
		Product product = new Product();
		ProductUtils.addErrors(product, HttpStatus.BAD_REQUEST.toString(), "Invalid Product Id");
		return new ResponseEntity<>(product,new HttpHeaders(),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Product> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		logger.error("CustomExceptionHandler::handleMethodArgumentNotValid {}", ex.getMessage());
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        Product product = new Product();
        for (FieldError fieldError : fieldErrors) {
        	ProductUtils.addErrors(product, fieldError.getField() , fieldError.getDefaultMessage());
        }
        for (ObjectError objectError : globalErrors) {
        	ProductUtils.addErrors(product, objectError.getObjectName() , objectError.getDefaultMessage());
        }      
		return new ResponseEntity<>(product, HttpStatus.BAD_REQUEST);		    
	} 
	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Product> handleRuntimeException(RuntimeException ex){
		logger.error("CustomExceptionHandler::handleRuntimeException {}", ex.getMessage());
		Product product = new Product();
		ProductUtils.addErrors(product,HttpStatus.INTERNAL_SERVER_ERROR.toString(),ex.getMessage());
		return new ResponseEntity<>(product,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Product> handleGeneralException(Exception ex){
		logger.error("CustomExceptionHandler::handleGeneralException {}", ex.getMessage());
		Product product = new Product();
		ProductUtils.addErrors(product,HttpStatus.INTERNAL_SERVER_ERROR.toString(),ex.getMessage());
		return new ResponseEntity<>(product,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(HystrixRuntimeException.class)
	public final ResponseEntity<Product> handleHystrixRuntimeException(HystrixRuntimeException ex){
		String exception = ex.getCause()!=null?ex.getCause().getMessage():ex.getMessage();
		logger.error("CustomExceptionHandler::HystrixRuntimeException {}", exception);
		Product product = new Product();
		ProductUtils.addErrors(product,HttpStatus.INTERNAL_SERVER_ERROR.toString(),exception);
		return new ResponseEntity<>(product,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(HystrixBadRequestException.class)
	public final ResponseEntity<Product> handleHystrixBadRequestException(HystrixBadRequestException ex){
		String exception = ex.getCause()!=null?ex.getCause().getMessage():ex.getMessage();
		logger.error("CustomExceptionHandler::HystrixBadRequestException {}", exception);
		Product product = new Product();
		ProductUtils.addErrors(product,HttpStatus.BAD_REQUEST.toString(),exception);
		return new ResponseEntity<>(product,new HttpHeaders(),HttpStatus.BAD_REQUEST);
	}
}
