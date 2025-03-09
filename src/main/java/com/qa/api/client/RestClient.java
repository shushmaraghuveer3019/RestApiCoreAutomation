package com.qa.api.client;

import com.qa.api.constants.AuthType;
import com.qa.api.exception.AppFrameworkException;
import com.qa.api.manager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.expect;

import java.io.File;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rhanumantharaya
 */
public class RestClient {

    //Define Response Specs
    private final ResponseSpecification responseSpec200 = expect().statusCode(200);
    private final ResponseSpecification responseSpec200or201 = expect().statusCode(anyOf(equalTo(200), equalTo(201)));
    private final ResponseSpecification responseSpec201 = expect().statusCode(201);
    private final ResponseSpecification responseSpec204or404 = expect().statusCode(anyOf(equalTo(200), equalTo(404)));
    private final ResponseSpecification responseSpec204 = expect().statusCode(204);
    private final ResponseSpecification responseSpec400 = expect().statusCode(400);
    private final ResponseSpecification responseSpec404 = expect().statusCode(404);
    private final ResponseSpecification responseSpec500 = expect().statusCode(500);
    private final ResponseSpecification responseSpec401 = expect().statusCode(401);
    private final ResponseSpecification responseSpec422 = expect().statusCode(422);

    private RequestSpecification setupRequest(String baseUrl, AuthType authType, ContentType contentType) {

        RequestSpecification request = RestAssured.given().log().all().baseUri(baseUrl)
                .contentType(contentType)
                .accept(contentType);

        switch (authType) {

            case BEARER_TOKEN:
                request.header("Authorization", "Bearer " + ConfigManager.getProp("bearerToken"));
                break;
            case CONTACTS_BEARER_TOKEN:
                request.header("Authorization", "Bearer " + ConfigManager.getProp("contacts_bearer_token"));
                break;
            case OAUTH2:
                request.header("Authorization", "Bearer " + generateOauth2Token());
                break;
            case BASIC_AUTH:
                request.header("Authorization", "Basic " + generateBasicAuthToken());
                break;
            case API_KEY:
                request.header("api-key", ConfigManager.getProp("apiKey"));
                break;
            case NO_AUTH:
                System.out.println("Auth is not required");
                break;
            default:
                System.out.println("This auth is not supported..Please pass the right Auth Type");
                throw new AppFrameworkException("NO AUTH SUPPORTED");
        }

        return request;

    }

    private String generateOauth2Token() {
        return RestAssured.given().log().all()
                .formParam("grant_type", ConfigManager.getProp("grantType"))
                .formParam("client_id", ConfigManager.getProp("clientId"))
                .formParam("client_secret", ConfigManager.getProp("clientSecret"))
                .post(ConfigManager.getProp("tokenUrl")).then().log().all().extract().path("access_token");
    }

    /**
     * This method is used to generate the Base 64 Encoded
     */
    private String generateBasicAuthToken() {
        String credentials = ConfigManager.getProp("basicUserName") + ":" + ConfigManager.getProp("basicPassword");
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    private RequestSpecification setUpAuthAndContentType(String baseUrl, AuthType authType, ContentType contentType) {
        return setupRequest(baseUrl, authType, contentType);
    }

    private void applyParams(RequestSpecification requestSpecification, Map<String, String> queryParam, Map<String, String> pathParam) {
        if (queryParam != null) {
            requestSpecification.queryParams(queryParam);
        }

        if (pathParam != null) {
            requestSpecification.pathParams(pathParam);
        }
    }
    //**************** ALL CRUD Methods ************

    /**
     * This method is used to call the GET APIs
     *
     * @param endPoint
     * @param queryMap
     * @param pathMap
     * @param authType
     * @param contentType
     * @return it returns the get api response
     */
    public Response getApiCall(String baseUrl, String endPoint, Map<String, String> queryParam, Map<String,
            String> pathParam, AuthType authType, ContentType contentType) {

        RequestSpecification requestSpecification = setUpAuthAndContentType(baseUrl, authType, contentType);

        applyParams(requestSpecification, queryParam, pathParam);

        Response response = requestSpecification.get(endPoint).then().log().all()
                .spec(responseSpec204or404).extract().response();
        response.prettyPrint();
        return response;
    }

    /**
     * This method is used to call the POST APIs
     *
     * @param endpoint
     * @param body
     * @param queryParam
     * @param pathParam
     * @param authType
     * @param contentType
     * @return it returns the Post api Response
     */
    public <T> Response posApiCall(String baseUrl, String endpoint, T body, Map<String, String> queryParam, Map<String, String> pathParam,
                                   AuthType authType, ContentType contentType) {

        RequestSpecification requestSpecification = setUpAuthAndContentType(baseUrl, authType, contentType);
        applyParams(requestSpecification, queryParam, pathParam);
        Response response = requestSpecification.body(body).post(endpoint).then().log().all().spec(responseSpec200or201).extract().response();
        response.prettyPrint();
        return response;
    }

    /**
     * This method is used to call the POST APIs for Json File body
     *
     * @param endpoint
     * @param body
     * @param queryParam
     * @param pathParam
     * @param authType
     * @param contentType
     * @return it returns the Post api Response
     */
    public Response posApiCall(String baseUrl, String endpoint, File file, Map<String, String> queryParam, Map<String, String> pathParam,
                               AuthType authType, ContentType contentType) {

        RequestSpecification requestSpecification = setUpAuthAndContentType(baseUrl, authType, contentType);
        applyParams(requestSpecification, queryParam, pathParam);
        Response response = requestSpecification.body(file).post(endpoint).then().log().all().spec(responseSpec201).extract().response();
        response.prettyPrint();
        return response;
    }

    /**
     * This method is used to call the Put APIs
     *
     * @param endpoint
     * @param body
     * @param queryParam
     * @param pathParam
     * @param authType
     * @param contentType
     * @return it returns the Put api Response
     */
    public <T> Response putApiCall(String baseUrl, String endpoint, T body, Map<String, String> queryParam, Map<String, String> pathParam,
                                   AuthType authType, ContentType contentType) {

        RequestSpecification requestSpecification = setUpAuthAndContentType(baseUrl, authType, contentType);
        applyParams(requestSpecification, queryParam, pathParam);
        Response response = requestSpecification.body(body).put(endpoint).then().log().all().spec(responseSpec200).extract().response();
        response.prettyPrint();
        return response;
    }

    /**
     * This method is used to call the Patch APIs
     *
     * @param endpoint
     * @param body
     * @param queryParam
     * @param pathParam
     * @param authType
     * @param contentType
     * @return it returns the Patch api Response
     */
    public <T> Response patchApiCall(String baseUrl, String endpoint, T body, Map<String, String> queryParam, Map<String, String> pathParam,
                                     AuthType authType, ContentType contentType) {

        RequestSpecification requestSpecification = setUpAuthAndContentType(baseUrl, authType, contentType);
        applyParams(requestSpecification, queryParam, pathParam);
        Response response = requestSpecification.body(body).patch(endpoint).then().log().all().spec(responseSpec200).extract().response();
        response.prettyPrint();
        return response;
    }

    /**
     * This method is used to call the Delete APIs
     *
     * @param endpoint
     * @param body
     * @param queryParam
     * @param pathParam
     * @param authType
     * @param contentType
     * @return it returns the Delete api Response
     */
    public Response deleteApiCall(String baseUrl, String endpoint, Map<String, String> queryParam, Map<String, String> pathParam,
                                  AuthType authType, ContentType contentType) {

        RequestSpecification requestSpecification = setUpAuthAndContentType(baseUrl, authType, contentType);
        applyParams(requestSpecification, queryParam, pathParam);
        Response response = requestSpecification.delete(endpoint).then().log().all().spec(responseSpec204).extract().response();
        response.prettyPrint();
        return response;
    }


}
