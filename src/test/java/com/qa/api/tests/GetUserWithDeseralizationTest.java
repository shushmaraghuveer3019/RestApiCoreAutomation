package com.qa.api.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class GetUserWithDeseralizationTest extends BaseTest {

    @Test
    public void getAllUsersTest() {
        Response getResponse = restClient.getApiCall(BASE_URL_GOREST,"/public/v2/users", null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getResponse.getStatusCode(), 200);
    }

}
