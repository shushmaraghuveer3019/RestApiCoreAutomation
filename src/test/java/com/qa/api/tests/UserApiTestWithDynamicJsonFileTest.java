package com.qa.api.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.StringUtility;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserApiTestWithDynamicJsonFileTest extends BaseTest {

    @Test
    public void createUserWithJsonFileTest() {

        String jsonFile = "./src/test/resources/jsons/user.json";

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode userNode = objectMapper.readTree(Files.readAllBytes(Paths.get(jsonFile)));

            //Create the Unique Email Id
            String uniqueEmailId = StringUtility.getRandomEmailId();

            //Update the Email Id
            ObjectNode obj = ((ObjectNode) userNode);
            obj.put("email", uniqueEmailId);

            //Convert JsonNode to Json String
            String updatedJsonString = objectMapper.writeValueAsString(userNode);
            System.out.println("Updated Json String " + updatedJsonString);

            Response createUserResponse = restClient.posApiCall(BASE_URL_GOREST, "/public/v2/users", updatedJsonString, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
            createUserResponse.prettyPrint();
            Assert.assertEquals(createUserResponse.getStatusCode(), 201);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
