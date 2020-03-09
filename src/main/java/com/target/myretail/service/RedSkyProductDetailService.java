package com.target.myretail.service;

import com.target.myretail.dto.Item;
import com.target.myretail.dto.Product;
import com.target.myretail.dto.ProductDescription;
import com.target.myretail.dto.ProductResponse;
import com.target.myretail.utils.ProductConstant;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class RedSkyProductDetailService {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(RedSkyProductDetailService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    public String getProductDetailsById(Long id) {
        ResponseEntity<ProductResponse> response = null;
        try {
            logger.debug("RedSkyProductDetailService::getProductNameById URI {}", id);
            final String URI = MessageFormat.format(env.getProperty(ProductConstant.REDSKU_URL), String.valueOf(id));
            response = restTemplate.getForEntity(URI, ProductResponse.class);
        } catch (HttpStatusCodeException e) {
            logger.error("RedSkyProductDetailService::getProductDetailsById::Not Found");
            throw e;
        }
        return mapProductName(response);
    }

    public String mapProductName(ResponseEntity<ProductResponse> productResponse) {
        Optional<String> opt = Optional.ofNullable(productResponse)
                .map(ResponseEntity<ProductResponse>::getBody)
                .map(ProductResponse::getProduct)
                .map(Product::getItem)
                .map(Item::getProductDescription)
                .map(ProductDescription::getTitle);
        return opt.isPresent() ? opt.get() : null;
    }
}
