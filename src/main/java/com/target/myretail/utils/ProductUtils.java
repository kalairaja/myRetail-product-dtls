package com.target.myretail.utils;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.target.myretail.model.ErrorMessage;
import com.target.myretail.model.Product;
import com.target.myretail.model.ProductPrice;

public class ProductUtils {

	private ProductUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static void addErrors(Product product, String errorCode, String message) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(errorCode);
		errorMessage.setMessage(message);
		if (product.getErrorMessage() == null) {
			product.setErrorMessage(Arrays.asList(errorMessage));
		} else {
			product.getErrorMessage().add(errorMessage);
		}
	}

	public static void mapProductPrice(ProductPrice productPrice, Product product) {
		if (productPrice != null && productPrice.getPrice() != null) {
			productPrice.setCurrencyCode(ProductConstant.CURRENCY_CODE);
			product.setProductPrice(productPrice);
		}
	}

	public static void mapProductName(String productName, Product product) {
		if (StringUtils.isNotEmpty(productName)) {
			product.setProductName(productName);
		}
	}

	public static boolean isProductInfoAvailable(String productName, ProductPrice productPrice) {
		boolean isPrdAvailable=true;
		if (StringUtils.isEmpty(productName) && (productPrice == null || productPrice.getPrice() == null)) {
			isPrdAvailable= false;
		}
		return isPrdAvailable;
	}
}
