package com.target.myretail.utils;

public class ProductConstant {
    private ProductConstant() {
        throw new IllegalStateException("Constant class");
    }

    public static final String CURRENCY_CODE = "USD";
    public static final String NOT_FOUND = "404";
    public static final String PRODUCT_NOT_FOUND = "Product Not found";
    public static final String REDSKU_URL = "redsky.target.url";
    public static final String CLIENT_ID = "X-CLIENT-ID";
}
