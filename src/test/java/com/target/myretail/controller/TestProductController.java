package com.target.myretail.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myretail.model.Product;
import com.target.myretail.utils.ProductConstant;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.config.AsyncConfig.withTimeout;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestProductController {

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RestAssuredMockMvc.webAppContextSetup(context);
        RestAssuredMockMvc.config().asyncConfig(withTimeout(30000, TimeUnit.SECONDS));
    }


    @Test
    public void testGetProductSuccessResponse() {
        ExtractableResponse<MockMvcResponse> resp = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(ProductConstant.CLIENT_ID, "TGT")
                .when()
                .async()
                .timeout(100, TimeUnit.SECONDS)
                .get("/retail/products/13860428")
                .then()
                .extract();
        assertEquals(HttpStatus.OK.value(), resp.statusCode());
    }


    @Test
    public void testGetProductNotFound() {
        ExtractableResponse<MockMvcResponse> resp = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(ProductConstant.CLIENT_ID, "TGT")
                .when()
                .async()
                .timeout(100, TimeUnit.SECONDS)
                .get("/retail/products/123")
                .then()
                .extract();
        assertEquals(HttpStatus.NOT_FOUND.value(), resp.statusCode());
    }

    @Test
    public void testGetProducInternalServerError() {
        ExtractableResponse<MockMvcResponse> resp = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(ProductConstant.CLIENT_ID, "TGT")
                .when()
                .post("/retail/products/89076555")
                .then()
                .extract();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), resp.statusCode());
    }

    @Test
    public void testGetProducMethodValidationError() {
        ExtractableResponse<MockMvcResponse> resp = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(ProductConstant.CLIENT_ID, "TGT")
                .when()
                .post("/retail/products/test")
                .then()
                .extract();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), resp.statusCode());
    }

    @Test
    public void testUpdateProductSuccessResponse() {
        ExtractableResponse<MockMvcResponse> resp = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(ProductConstant.CLIENT_ID, "TGT")
                .body(Models.getProduct())
                .when()
                .async()
                .timeout(100, TimeUnit.SECONDS)
                .put("/retail/products/13860428")
                .then()
                .extract();
        String response = resp.body().asString();
        Product product = toStringJson(response);
        String priceStr = product != null ? product.getProductPrice().getPrice().toString() : "";
        assertEquals(HttpStatus.OK.value(), resp.statusCode());
        assertEquals("900.00", priceStr);
    }

    @Test
    public void testUpdateProductNotFound() {
        Product product = Models.getProduct();
        product.setProductId(13860828L);
        ExtractableResponse<MockMvcResponse> resp = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(ProductConstant.CLIENT_ID, "TGT")
                .body(product)
                .when()
                .async()
                .timeout(100, TimeUnit.SECONDS)
                .put("/retail/products/13860828")
                .then()
                .extract();
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.statusCode());
    }

    public static Product toStringJson(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString, Product.class);
        } catch (IOException e) {
            return null;
        }
    }
}
