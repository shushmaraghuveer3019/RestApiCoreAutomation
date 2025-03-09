package com.qa.api.amadeus.test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.groovy.util.Maps;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class AmadeusApiTest extends BaseTest {

    @Test
    public void getFlightDetails() {
        Map<String, String> queryParams = Maps.of("origin", "PAR", "maxPrice", "200");
        Response getFlightResponse = restClient.getApiCall(BASE_URL_AMADEUS, "/v1/shopping/flight-destinations", queryParams, null, AuthType.OAUTH2, ContentType.ANY);
        Assert.assertEquals(getFlightResponse.getStatusCode(), 200);
    }


}
