package com.qa.api.schemavalidationtest;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.SchemaValidator;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductsApiSchemaTest extends BaseTest {

    @Test
    public void productApiSchemaTest() {
        RestAssured.given().baseUri("https://fakestoreapi.com").log().all()
                .when().log().all().get("/products").then().log().all()
                .assertThat().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/product-json-schema.json"));
    }

    @Test
    public void productApiSchemaTestUsingFw() {
        Response getProductResponse = restClient.getApiCall(BASE_URL_PRODUCT, "/product", null, null, AuthType.NO_AUTH, ContentType.JSON);
        boolean flag = SchemaValidator.validateApiSchema(getProductResponse, "schema/product-json-schema.json");
        Assert.assertTrue(flag);
    }

}
