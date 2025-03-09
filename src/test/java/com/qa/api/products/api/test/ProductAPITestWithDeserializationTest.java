package com.qa.api.products.api.test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Product;
import com.qa.api.utils.JsonUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

public class ProductAPITestWithDeserializationTest extends BaseTest {

    @Test
    public void getAllProductsTest() {
        Response getProductApiResponse = restClient.getApiCall(BASE_URL_PRODUCT,"/products", null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(getProductApiResponse.getStatusCode(), 200);

        Product[] productJsonResponse = JsonUtils.deserialize(getProductApiResponse, Product[].class);

        System.out.println(Arrays.toString(productJsonResponse));

        for (Product product : productJsonResponse){
            System.out.println("Id " + product.getId());
            System.out.println("Title " + product.getTitle());
            System.out.println("Price " + product.getPrice());
            System.out.println("Description " + product.getDescription());
            System.out.println("Category " + product.getCategory());
            System.out.println("Image " + product.getImage());
            System.out.println("Rate " + product.getRating().getRate());
            System.out.println("Count " + product.getRating().getCount());
            System.out.println("**********************");
        }
    }

}
