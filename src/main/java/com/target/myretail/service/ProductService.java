package com.target.myretail.service;

import com.target.myretail.circuit.ProductDetailCommand;
import com.target.myretail.circuit.RedSkyProductDetailCommand;
import com.target.myretail.circuit.UpdateProductDetailsCommand;
import com.target.myretail.dao.ProductDetailRepository;
import com.target.myretail.model.Product;
import com.target.myretail.model.ProductPrice;
import com.target.myretail.utils.ProductConstant;
import com.target.myretail.utils.ProductUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rx.Observable;

@Service
public class ProductService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    public RedSkyProductDetailService redSkyProductDetailService;

    @Autowired
    public ProductDetailRepository productDetailRepository;

    public Observable<ResponseEntity<Product>> getProductByID(Long productId) {
        Product product = new Product();
        Observable<String> productName=
                new RedSkyProductDetailCommand(productId, redSkyProductDetailService).observe().onErrorReturn(exception -> {
                    logger.error("Error during get product name API", exception);
                    return null;
                });
        Observable<ProductPrice> productPrice = new ProductDetailCommand(productId, productDetailRepository).observe();
        return Observable // Aggregate product data from DB + External API.
                .zip(productName, productPrice,
                        ((prodName, priceDetails) -> aggregateProductDetails(productId, prodName, priceDetails,
                        product)));
    }

    public Observable<ResponseEntity<Product>> updateProductDetails(Long productId, Product product) {
        product.setProductId(productId);
        product.getProductPrice().setId(productId);
        return new UpdateProductDetailsCommand(product, productDetailRepository).observe();
    }

    private ResponseEntity<Product> aggregateProductDetails(Long productId, String productName,
                                                            ProductPrice productPrice, Product product) {
        product.setProductId(productId);
        if (!ProductUtils.isProductInfoAvailable(productName, productPrice)) {
            ProductUtils.addErrors(product, ProductConstant.NOT_FOUND, ProductConstant.PRODUCT_NOT_FOUND);
            return new ResponseEntity<>(product, HttpStatus.NOT_FOUND);
        }
        ProductUtils.buildProductDetails(productName,productPrice, product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
