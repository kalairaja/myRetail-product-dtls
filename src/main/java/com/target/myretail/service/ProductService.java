package com.target.myretail.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.target.myretail.circuit.GetProductNameCommand;
import com.target.myretail.circuit.GetProductPriceCommand;
import com.target.myretail.circuit.UpdateProductPriceCommand;
import com.target.myretail.dao.GetProductNameDAO;
import com.target.myretail.dao.GetProductPriceDAO;
import com.target.myretail.dao.UpdateProductPriceDAO;
import com.target.myretail.model.Product;
import com.target.myretail.model.ProductPrice;
import com.target.myretail.utils.ProductConstant;
import com.target.myretail.utils.ProductUtils;

import rx.Observable;

@Service
public class ProductService {
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(ProductService.class);
	@Autowired
	public GetProductNameDAO productNameDAO;

	@Autowired
	public GetProductPriceDAO productPriceDAO;

	@Autowired
	public UpdateProductPriceDAO updateProductPriceDAO;

	public Observable<ResponseEntity<Product>> getProduct(Long productId) {
		Product product = new Product();
		return Observable // Aggregate product data from multiple sources
				.zip(new GetProductNameCommand(productId, productNameDAO).observe().onErrorReturn(exception -> {
					logger.error("Error during get product name API", exception);
					return null;
				}), new GetProductPriceCommand(productId, productPriceDAO).observe().onErrorReturn(exception -> {
					logger.error("Error during price details lookup from DB", exception);
					return null;
				}), ((productName, priceDetails) -> aggregateProductDetails(productId, productName, priceDetails,
						product)));
	}

	public Observable<ResponseEntity<Product>> updateProductPrice(Long productId, Product product) {
		product.setProductId(productId);
		product.getProductPrice().setId(productId);
		return new UpdateProductPriceCommand(product, updateProductPriceDAO).observe();
	}

	private ResponseEntity<Product> aggregateProductDetails(Long productId, String productName,
			ProductPrice productPrice, Product product) {
		product.setProductId(productId);
		if (!ProductUtils.isProductInfoAvailable(productName, productPrice)) {
			ProductUtils.addErrors(product, ProductConstant.NOT_FOUND, ProductConstant.PRODUCT_NOT_FOUND);
			return new ResponseEntity<>(product, HttpStatus.NOT_FOUND);
		}
		ProductUtils.mapProductPrice(productPrice, product);
		ProductUtils.mapProductName(productName, product);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
}
