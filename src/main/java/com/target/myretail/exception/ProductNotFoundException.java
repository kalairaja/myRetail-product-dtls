package com.target.myretail.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;

public class ProductNotFoundException extends HystrixBadRequestException {
    private static final long serialVersionUID = -1483991355281974507L;

    public ProductNotFoundException(String message) {
        super(message);
    }

}
