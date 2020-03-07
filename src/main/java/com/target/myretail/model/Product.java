package com.target.myretail.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter@Setter
public class Product {
	@JsonProperty("id")
	private Long productId;
	@JsonProperty("name")
	private String productName;
	@NotNull
    @Valid
    @JsonProperty("current_price")
	private ProductPrice productPrice;
	@JsonProperty("errors")
	private List<ErrorMessage> errorMessage;
}
