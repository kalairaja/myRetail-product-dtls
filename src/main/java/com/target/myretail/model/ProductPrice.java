package com.target.myretail.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "productprice")
public class ProductPrice implements Serializable {
    private static final long serialVersionUID = -456367892004104223L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProductPrice.class);
    @Id
    @JsonIgnore
    private Long id;

    @JsonProperty("value")
    @NotNull(message = "Invalid Price.")
    @DecimalMin("0.00")
    private BigDecimal price;
    @JsonProperty("currency_code")
    @Size(min = 2, max = 3, message = "Invalid Currency Code.Valid Values:USD")
    private String currencyCode;

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error("Error writing object to string", e);
            return "";
        }
    }
}
