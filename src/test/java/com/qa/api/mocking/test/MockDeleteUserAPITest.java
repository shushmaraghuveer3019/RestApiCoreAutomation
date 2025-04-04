package com.qa.api.mocking.test;

import com.qa.api.base.BaseTest;

import com.qa.api.constants.AuthType;
import com.qa.api.mocking.APIMocks;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class MockDeleteUserAPITest extends BaseTest {

    @Test
    public void getDummyUserWithJsonFileTest() {
        APIMocks.deleteDummyUser();
        Response response = restClient.deleteApiCall(BASE_URL_LOCALHOST_PORT, "/api/users/1", null, null, AuthType.NO_AUTH, ContentType.ANY);
        response.then()
                .assertThat()
                .statusCode(204);
        //get dummy json
    }
}
