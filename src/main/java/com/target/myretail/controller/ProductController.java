package com.target.myretail.controller;

import javax.validation.Valid;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.target.myretail.model.Product;
import com.target.myretail.service.ProductService;

import rx.Observable;

@RestController
@RequestMapping("/retail")
@Api(tags = "Products APIs")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@ApiOperation(value = "Display Product Information")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Product Details. Ex ProductID.13860428"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	@GetMapping(value = "/products/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public DeferredResult<ResponseEntity<Product>> getProductDetails(@ApiParam @RequestHeader("X-CLIENT-ID") String clientId,
																	 @ApiParam @PathVariable(value = "id", required = true) @Valid Long productId) {
		DeferredResult<ResponseEntity<Product>> deferredResult = new DeferredResult<>();
		Observable<ResponseEntity<Product>> o = productService.getProduct(productId);
		o.subscribe(deferredResult::setResult, deferredResult::setErrorResult);
		return deferredResult;
	}

	@ApiOperation(value = "Update Product Price")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Updated Product Details. Ex ProductID.13860428"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping(value = "/products/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public DeferredResult<ResponseEntity<Product>> updateProductDetails(@ApiParam @RequestHeader("X-CLIENT-ID") String clientId,
																		@ApiParam @PathVariable(value = "id", required = true) Long productId,
																		@ApiParam @Valid @RequestBody Product product) {
		DeferredResult<ResponseEntity<Product>> deferredResult = new DeferredResult<>();
		Observable<ResponseEntity<Product>> o = productService.updateProductPrice(productId, product);
		o.subscribe(deferredResult::setResult, deferredResult::setErrorResult);
		return deferredResult;
	}
}
