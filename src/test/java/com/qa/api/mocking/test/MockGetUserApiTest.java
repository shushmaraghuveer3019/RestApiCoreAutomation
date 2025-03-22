package com.qa.api.mocking.test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.mocking.APIMocks;
import com.qa.api.mocking.WireMockSetup;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class MockGetUserApiTest extends BaseTest {

    @Test
    public void getDummyUserTest() {
        APIMocks.getDummyUser();
        Response response = restClient.getApiCall(BASE_URL_LOCALHOST_PORT, "/api/users", null, null, AuthType.NO_AUTH, ContentType.ANY);
        response.then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo("Tom"));
    }

    @Test
    public void getDummyUserWithJsonFileTest() {
        APIMocks.getDummyUserWithJsonFile();
        Response response = restClient.getApiCall(BASE_URL_LOCALHOST_PORT, "/api/users", null, null, AuthType.NO_AUTH, ContentType.ANY);
        response.then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo("Tom"));
    }

    @Test
    public void getDummyUserWithQueryParamTest() {
        APIMocks.getDummyUserWithQueryParams();
       Map<String, String> queryParam=  Map.of("name", "Tom");
        Response response = restClient.getApiCall(BASE_URL_LOCALHOST_PORT, "/api/users", queryParam, null, AuthType.NO_AUTH, ContentType.ANY);
        response.then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo("Tom"));
    }


}
