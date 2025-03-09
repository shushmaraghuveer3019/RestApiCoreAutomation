package com.qa.api.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteUserTest extends BaseTest {

    @Test
    public void deleteUserWithBuilderTest() {

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

        //DELETE - Delete the same user
        Response deleteResponse = restClient.deleteApiCall(BASE_URL_GOREST,"/public/v2/users/" + userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(deleteResponse.getStatusCode(), 204);

        //GET - Recheck and Fetch the created user
        Response getResponseAfterDelete = restClient.getApiCall(BASE_URL_GOREST,"/public/v2/users/" + userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getResponseAfterDelete.getStatusCode(), 404);

    }
}
