package com.qa.api.contacts;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.Credentials;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContactsApiTest extends BaseTest {

    @BeforeMethod
    public void getToken() {

        Credentials credentials = Credentials.builder()
                .email("naveenanimation20@gmail.com").password("test@123").build();
        Response response = restClient.posApiCall(BASE_URL_CONTACTS, "/users/login", credentials, null, null, AuthType.NO_AUTH, ContentType.JSON);
        String tokenId = response.jsonPath().getString("token");
        System.out.println("Token Id " + tokenId);
        ConfigManager.setProp("contacts_bearer_token", tokenId);
    }

    @Test
    public void getContactsApi() {

        Response getResponse = restClient.getApiCall(BASE_URL_CONTACTS, "/contacts", null, null, AuthType.CONTACTS_BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getResponse.getStatusCode(), 200);
    }
}
