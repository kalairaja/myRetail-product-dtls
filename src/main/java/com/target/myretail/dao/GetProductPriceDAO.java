package com.target.myretail.dao;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.target.myretail.model.ProductPrice;

@Repository
public class GetProductPriceDAO {
	
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(GetProductPriceDAO.class);
	@Autowired
	private ProductPriceRepository productPriceRepository;	 
	@Cacheable(value = "productPriceCache", key = "#id")
	public ProductPrice getProductPrice(Long id){
		logger.info("GetProductPriceDAO::getProductPrice:ProductId::{}",id);
		ProductPrice prodPrice=productPriceRepository.findById(id).orElse(null);
		logger.debug("GetProductPriceDAO::getProductPrice::prodPrice : {}" , prodPrice);
		return prodPrice;
	}	
}
