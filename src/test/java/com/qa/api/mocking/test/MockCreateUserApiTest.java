package com.qa.api.mocking.test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.mocking.APIMocks;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class MockCreateUserApiTest extends BaseTest {

    @Test
    public void createDummyUserTest() {
        APIMocks.createDummyUser();
        String dummyJson = "{\n" +
                "    \"name\": \"Tom\"\n" +
                "}";

        Response response = restClient.posApiCall(BASE_URL_LOCALHOST_PORT, "/api/users", dummyJson, null, null, AuthType.NO_AUTH, ContentType.JSON);
        response.then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    public void createDummyUserWithJsonFileTest() {
        APIMocks.createDummyUserWithJson();
        String dummyJson = "{\n" +
                "    \"name\": \"Tom\"\n" +
                "}";
        Response response = restClient.posApiCall(BASE_URL_LOCALHOST_PORT, "/api/users", dummyJson, null, null, AuthType.NO_AUTH, ContentType.JSON);
        response.then()
                .assertThat()
                .statusCode(201);
    }
}
