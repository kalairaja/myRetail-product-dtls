package com.target.myretail.dao;

import java.text.MessageFormat;
import java.util.Optional;

import com.target.myretail.dto.Item;
import com.target.myretail.dto.Product;
import com.target.myretail.dto.ProductDescription;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.target.myretail.dto.ProductResponse;
import com.target.myretail.utils.ProductConstant;

@Repository
public class GetProductNameDAO {

	private final org.slf4j.Logger logger = LoggerFactory.getLogger(GetProductNameDAO.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Environment env;

	public String getProductName(Long id) {
		String productName=null;
		try {
			logger.info("GetProductNameDAO::getProductName URI {}",id);
			final String URI = MessageFormat.format(env.getProperty(ProductConstant.REDSKU_URL), String.valueOf(id));
			logger.info("GetProductNameDAO::getProductName URI {}",URI);
			ResponseEntity<ProductResponse> response = restTemplate.getForEntity(URI, ProductResponse.class);
			Optional<String> opt = Optional.ofNullable(response)
			.map(ResponseEntity<ProductResponse> ::getBody)
			.map(ProductResponse::getProduct)
			.map(Product::getItem)
			.map(Item::getProductDescription)
			.map(ProductDescription::getTitle);
			productName = opt.isPresent()?opt.get():null;
			logger.debug("GetProductNameDAO::getProductName::Response : {}" , response.getBody().getProduct());
		} catch (HttpStatusCodeException e) {
			logger.error("GetProductNameDAO::getProductName::Not Found: {}" , e.getStatusCode());
		}
		return productName;
	}
}
