package com.target.myretail.dao;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.target.myretail.model.ProductPrice;

public interface ProductPriceRepository extends MongoRepository<ProductPrice, Long> {}

