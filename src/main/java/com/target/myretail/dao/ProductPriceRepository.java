package com.target.myretail.dao;


import com.target.myretail.model.ProductPrice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductPriceRepository extends MongoRepository<ProductPrice, Long> {
}

