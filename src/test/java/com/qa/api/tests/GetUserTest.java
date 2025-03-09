package com.qa.api.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class GetUserTest extends BaseTest {

    @Test
    public void getAllUsersTest() {
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("name", "Giri Verma");
        queryParam.put("status", "active");
        Response getResponse = restClient.getApiCall(BASE_URL_GOREST,"/public/v2/users", queryParam, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getResponse.getStatusCode(), 200);
    }

    @Test(enabled = false)
    public void getSingleUserTest() {
        Response getResponse = restClient.getApiCall(BASE_URL_GOREST,"/public/v2/users/7752428", null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getResponse.getStatusCode(), 200);
    }


}
