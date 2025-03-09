package com.qa.api.products.api.test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class ProductsApiTest extends BaseTest {

    @Test
    public void getProductsTest() {
        Response getProductApiResponse = restClient.getApiCall(BASE_URL_PRODUCT, "/products", null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(getProductApiResponse.getStatusCode(), 200);
    }

    @Test
    public void getProductsApiLimitTest() {

        Map<String, String> queryLimit = Map.of("limit", "5");
        Response getProductApiResponse = restClient.getApiCall(BASE_URL_PRODUCT, "/products", queryLimit, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(getProductApiResponse.getStatusCode(), 200);
    }
}
