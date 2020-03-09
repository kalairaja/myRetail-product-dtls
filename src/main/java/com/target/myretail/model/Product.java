package com.target.myretail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter
@RequiredArgsConstructor
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
