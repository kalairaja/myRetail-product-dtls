package com.target.myretail.configuration;

import com.target.myretail.dao.ProductPriceRepository;
import com.target.myretail.model.ProductPrice;
import org.mockito.Mockito;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@EnableAutoConfiguration
@EnableCaching
@AutoConfigureDataMongo
public class TestSpringConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(ProductPriceRepository productPriceRepository) {
        return strings -> {
            productPriceRepository.deleteAll();
            productPriceRepository.save(new ProductPrice(13860428L, new BigDecimal("299.99"), "USD"));
            productPriceRepository.save(new ProductPrice(13860500L, new BigDecimal("399.99"), "USD"));
            productPriceRepository.save(new ProductPrice(13860600L, new BigDecimal("99.99"), "USD"));

        };
    }

    @Bean(value = "restTemplate")
    public RestTemplate restTemplate() {
        return Mockito.mock(RestTemplate.class);
    }

}
