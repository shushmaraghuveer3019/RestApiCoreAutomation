package com.qa.api.schemavalidationtest;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.SchemaValidator;
import com.qa.api.utils.StringUtility;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserApiSchemaTest extends BaseTest {


    @Test
    public void userApiSchemaTest() {
        RestAssured.given().baseUri("https://gorest.co.in").log().all()
                .header("Authorization", "Bearer 81dcd70d5b3e095c23fba7c8ded79f76f4d78320d52e2b444c7daade8728cc2c")
                .when().log().all().get("/public/v2/users/7756214").then().log().all()
                .assertThat().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/user-json-schema.json"));
    }

    @Test
    public void userApiSchemaTestUsingFw() {
        //POST - Create the USER
        User user = User.builder().name("apiname").email(StringUtility.getRandomEmailId()).status("active").gender("male").build();

        Response postResponse = restClient.posApiCall(BASE_URL_GOREST, "/public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(postResponse.getStatusCode(), 201);
        String userId = postResponse.jsonPath().getString("id");
        System.out.println("User Id " + userId);

        Response getUserResponse = restClient.getApiCall(BASE_URL_GOREST, "/public/v2/users" + userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        boolean flag = SchemaValidator.validateApiSchema(getUserResponse, "schema/user-json-schema.json");
        Assert.assertTrue(flag);
    }

}
