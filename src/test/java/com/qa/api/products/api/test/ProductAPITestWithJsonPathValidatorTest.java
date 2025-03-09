package com.qa.api.products.api.test;

import com.jayway.jsonpath.JsonPath;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidator;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class ProductAPITestWithJsonPathValidatorTest extends BaseTest {

    @Test
    public void getAllProductsTest() {
        Response getProductApiResponse = restClient.getApiCall(BASE_URL_PRODUCT, "/products", null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(getProductApiResponse.getStatusCode(), 200);

        List<Number> prices = JsonPathValidator.readList(getProductApiResponse, "$[?(@.price > 50)].price");
        prices.forEach(System.out::println);
        System.out.println("****************");

        List<Number> ids = JsonPathValidator.readList(getProductApiResponse, "$[?(@.price > 50)].id");
        ids.forEach(System.out::println);
        System.out.println("****************");

        List<Double> ratingRate = JsonPathValidator.readList(getProductApiResponse, "$[?(@.price > 50)].rating.rate");
        ratingRate.forEach(System.out::println);
        System.out.println("****************");

        List<Integer> count = JsonPathValidator.readList(getProductApiResponse, "$[?(@.price > 50)].rating.count");
        count.forEach(System.out::println);
        System.out.println("****************");

        //Get MAP
        List<Map<String, Object>> jewelleryList = JsonPathValidator.readList(getProductApiResponse, "$[?(@.category == 'jewelery')]..['title','price']");
        System.out.println(jewelleryList.size());
        for (Map<String, Object> product : jewelleryList) {
            String title = (String) product.get("title");
            Number price = (Number) product.get("price");
            System.out.println("title:" + title);
            System.out.println("price:" + price);
            System.out.println("***************");
        }

        //Get Minimum Price
        Double minPrice = JsonPathValidator.read(getProductApiResponse, "min($[*].price)");
        System.out.println("Minimum Price is " + minPrice);

        //Get Maximum Price
        Double maxPrice = JsonPathValidator.read(getProductApiResponse, "max($[*].price)");
        System.out.println("Maximum Price is " + maxPrice);

        //Get Average Price
        Double avgPrice = JsonPathValidator.read(getProductApiResponse, "avg($[*].price)");
        System.out.println("Average Price is " + avgPrice);

        //Get stddev() - Provides the standard deviation value of an array of numbers
        Double stddev = JsonPathValidator.read(getProductApiResponse, "stddev($[*].price)");
        System.out.println("Average Price is " + stddev);

        // length() - Provides the length of an array
        Integer arrayLength = JsonPathValidator.read(getProductApiResponse, "length($)");
        System.out.println("Array length: " + arrayLength);
        System.out.println("-----------");

    }
}


