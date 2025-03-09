package com.qa.api.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PatchUserTest extends BaseTest {

    @Test
    public void patchUserWithBuilderTest() {

        //POST - Create the USER
        User user = User.builder().name("apiname").email(StringUtility.getRandomEmailId()).status("active").gender("male").build();

        Response postResponse = restClient.posApiCall(BASE_URL_GOREST,"/public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(postResponse.getStatusCode(), 201);
        String userId = postResponse.jsonPath().getString("id");
        System.out.println("User Id " + userId);

        //GET - Fetch the created user
        Response getResponse = restClient.getApiCall(BASE_URL_GOREST,"/public/v2/users/" + userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        Assert.assertEquals(getResponse.jsonPath().getString("id"), userId);

        //Update the user details using setter
        user.setName("apiname124");
        user.setStatus("active");
        user.setGender("female");
        user.setEmail(StringUtility.getRandomEmailId());
        //PUT - Update the same user
        Response putResponse = restClient.patchApiCall(BASE_URL_GOREST,"/public/v2/users/" + userId, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(putResponse.getStatusCode(), 200);
        Assert.assertEquals(putResponse.jsonPath().getString("name"), user.getName());
        Assert.assertEquals(putResponse.jsonPath().getString("status"), user.getStatus());
        Assert.assertEquals(putResponse.jsonPath().getString("gender"), user.getGender());
    }
}
