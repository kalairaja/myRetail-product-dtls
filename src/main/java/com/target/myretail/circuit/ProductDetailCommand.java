package com.target.myretail.circuit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.target.myretail.dao.ProductDetailRepository;
import com.target.myretail.model.ProductPrice;

public class ProductDetailCommand extends HystrixCommand<ProductPrice> {

    private final ProductDetailRepository productDetailRepository;

    private final Long productId;

    public ProductDetailCommand(Long productId, ProductDetailRepository productDetailRepository) {
        super(HystrixCommandGroupKey.Factory.asKey("ProductDetailCommand"));
        this.productId = productId;
        this.productDetailRepository = productDetailRepository;
    }

    @Override
    protected ProductPrice run() {
        return productDetailRepository.getProductPriceByID(productId);
    }

}
