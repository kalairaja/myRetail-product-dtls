package com.target.myretail.circuit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.target.myretail.dao.ProductDetailRepository;
import com.target.myretail.model.Product;
import org.springframework.http.ResponseEntity;

public class UpdateProductDetailsCommand extends HystrixCommand<ResponseEntity<Product>> {
    private final ProductDetailRepository productDetailRepository;
    private final Product product;

    public UpdateProductDetailsCommand(Product product, ProductDetailRepository productDetailRepository) {
        super(HystrixCommandGroupKey.Factory.asKey("UpdateProductDetailsCommand"));
        this.product = product;
        this.productDetailRepository = productDetailRepository;
    }

    @Override
    protected ResponseEntity<Product> run() throws Exception {
        return productDetailRepository.updateProductPrice(product);
    }
}
