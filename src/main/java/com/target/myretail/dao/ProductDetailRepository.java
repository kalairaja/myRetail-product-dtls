package com.target.myretail.dao;

import com.target.myretail.exception.ProductNotFoundException;
import com.target.myretail.model.Product;
import com.target.myretail.model.ProductPrice;
import com.target.myretail.utils.ProductConstant;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDetailRepository {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(ProductDetailRepository.class);
    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Cacheable(value = "productPriceCache", key = "#id")
    public ProductPrice getProductPriceByID(Long id) {
        logger.debug("ProductDetailRepository::getProductPriceByID::{}", id);
        return getProductPrice(id);
    }

    public ResponseEntity<Product> updateProductPrice(Product product) {
        logger.debug("ProductDetailRepository::updateProductPrice:: {}",
                product.getProductPrice().getId());
        if (getProductPrice(product.getProductId()) == null) {
            throw new ProductNotFoundException(ProductConstant.PRODUCT_NOT_FOUND);
        }
        productPriceRepository.save(product.getProductPrice());
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    private ProductPrice getProductPrice(Long id) {
        return productPriceRepository.findById(id).orElse(null);
    }
}
