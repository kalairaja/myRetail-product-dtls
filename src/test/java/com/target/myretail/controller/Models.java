package com.target.myretail.controller;

import com.target.myretail.model.Product;
import com.target.myretail.model.ProductPrice;

import java.math.BigDecimal;

public class Models {

    public static Product getProduct() {
        Product product = new Product();
        ProductPrice price = new ProductPrice();
        product.setProductId(13860428L);
        price.setCurrencyCode("USD");
        price.setPrice(new BigDecimal("900.00"));
        product.setProductPrice(price);
        return product;
    }
}
