package com.target.myretail.circuit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.target.myretail.service.RedSkyProductDetailService;

public class RedSkyProductDetailCommand extends HystrixCommand<String> {

    private final RedSkyProductDetailService redSkyProductDetailService;

    private final Long productId;

    public RedSkyProductDetailCommand(Long productId, RedSkyProductDetailService redSkyProductDetailService) {
        super(HystrixCommandGroupKey.Factory.asKey("GetProductNameCommand"));
        this.productId = productId;
        this.redSkyProductDetailService = redSkyProductDetailService;
    }

    @Override
    protected String run() {
        return redSkyProductDetailService.getProductDetailsById(productId);
    }
}
