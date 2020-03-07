package com.target.myretail.circuit;

import org.springframework.http.ResponseEntity;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.target.myretail.dao.UpdateProductPriceDAO;
import com.target.myretail.model.Product;

public class UpdateProductPriceCommand extends HystrixCommand<ResponseEntity<Product>> {
	private final UpdateProductPriceDAO updateProductPriceDAO;
	private final Product product;

	public UpdateProductPriceCommand(Product product, UpdateProductPriceDAO updateProductPriceDAO) {
		super(HystrixCommandGroupKey.Factory.asKey("UpdateProductPriceCommand"));
		this.product = product;
		this.updateProductPriceDAO = updateProductPriceDAO;
	}

	@Override
	protected ResponseEntity<Product> run() throws Exception {
		return updateProductPriceDAO.updateProductPrice(product);
	}
}
