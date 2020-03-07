package com.target.myretail.dao;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.target.myretail.exception.ProductNotFoundException;
import com.target.myretail.model.Product;
import com.target.myretail.model.ProductPrice;

@Repository
public class UpdateProductPriceDAO {

	private final org.slf4j.Logger logger = LoggerFactory.getLogger(UpdateProductPriceDAO.class);

	@Autowired
	private ProductPriceRepository productPriceRepository;

	public ResponseEntity<Product> updateProductPrice(Product product) {
		ProductPrice productPrice = null;
		logger.debug("UpdateProductPriceDAO::updateProductPrice::productPrice before update : {}",
				product.getProductPrice());
		if (getProduct(product.getProductId())==null) {
			throw new ProductNotFoundException("Product Not Found");
		} else {
			productPrice = productPriceRepository.save(product.getProductPrice());
			product.getProductPrice().setId(null);
			product.setProductId(null);
			logger.debug("UpdateProductPriceDAO::updateProductPrice::productPrice : {}", productPrice);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	private ProductPrice getProduct(Long id) {
		return productPriceRepository.findById(id).orElse(null);
	}
}
