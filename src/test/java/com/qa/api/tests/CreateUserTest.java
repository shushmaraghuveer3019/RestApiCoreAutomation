package com.qa.api.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static com.qa.api.utils.StringUtility.getRandomEmailId;

public class CreateUserTest extends BaseTest {

    @Test
    public void createUserTest() {
        User user = new User(null, "raghu", StringUtility.getRandomEmailId(), "Male", "active");

        Response getResponse = restClient.posApiCall(BASE_URL_GOREST,"/public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getResponse.getStatusCode(), 201);
    }

    @Test
    public void createUserWithBuilderTest() {

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
        Assert.assertEquals(getResponse.jsonPath().getString("name"), user.getName());
        Assert.assertEquals(getResponse.jsonPath().getString("email"), user.getEmail());
        Assert.assertEquals(getResponse.jsonPath().getString("gender"), user.getGender());
        Assert.assertEquals(getResponse.jsonPath().getString("status"), user.getStatus());
    }

    @Test(enabled = false)
    public void createUserWithJsonFileTest() {
        File userFileJson = new File("./src/test/resources/jsons/user.json");
        Response getResponse = restClient.posApiCall(BASE_URL_GOREST,"/public/v2/users", userFileJson, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getResponse.getStatusCode(), 201);
    }


}
