package com.target.myretail.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.target.myretail.model.Product;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestCustomExceptionHandler {
	@Mock
	MethodArgumentTypeMismatchException exp;
	@Mock
	HystrixRuntimeException hystrxexp;
	@Test
	public void testHandleArgTypeMiExceptionHandler() {
		CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
		ResponseEntity<Product> response= customExceptionHandler.handleArgTypeMiExceptionHandler(exp);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	}
	
	@Test
	public void testHandleRuntimeException() {
		CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
		ResponseEntity<Product> response= customExceptionHandler.handleRuntimeException(new RuntimeException());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
	}
	
	@Test
	public void testHandleException() {
		CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
		ResponseEntity<Product> response= customExceptionHandler.handleGeneralException(new Exception());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
	}
	
	@Test
	public void testHystrixException() {
		CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
		ResponseEntity<Product> response= customExceptionHandler.handleHystrixRuntimeException(hystrxexp);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
	}

}
