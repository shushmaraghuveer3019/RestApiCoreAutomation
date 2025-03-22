package com.qa.api.mocking;

import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;

public class APIMocks {

    //*********************** create stub/mock for GET CALLs**************************:
    public static void getDummyUser() {
        //http://localhost:8089/api/users
        stubFor(get(urlEqualTo("/api/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n"
                                + "    \"name\": \"raghu\"\n"
                                + "}")
                )
        );
    }

    public static void getDummyUserWithJsonFile() {
        //http://localhost:8089/api/users
        stubFor(get(urlEqualTo("/api/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("user.json")
                )
        );

        //Note: In order to work with the files, we need to create one package in the src/resources called __files and copy paste the json and it will work
    }

    public static void getDummyUserWithQueryParams() {
        //http://localhost:8089/api/users
        stubFor(get(urlPathEqualTo("/api/users"))
                .withQueryParam("name", equalTo("Tom"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("user.json")
                )
        );
    }

    public static void getDummyProductsWithJsonFile() {
        //http://localhost:8089/api/users
        stubFor(get(urlEqualTo("/api/products"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("products.json")
                )
        );

        //Note: In order to work with the files, we need to create one package in the src/resources called __files and copy paste the json and it will work
    }

   //*********************** create stub/mock for POST CALLs**************************:

    public static void createDummyUser() {
        //http://localhost:8089/api/users
        stubFor(post(urlEqualTo("/api/users"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withStatusMessage("User is created")
                        .withBody("{\n" +
                                "    \"id\": 1, \"name\": \"Tom\"\n" +
                                "}")
                )
        );
    }

    public static void createDummyUserWithJson() {
        //http://localhost:8089/api/users
        stubFor(post(urlEqualTo("/api/users"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withStatusMessage("User is created")
                        .withBodyFile("user.json")
                )
        );
    }

    //*********************** create stub/mock for DELETE CALLs**************************:
    public static void deleteDummyUser() {
        //http://localhost:8089/api/users
        stubFor(get(urlEqualTo("/api/users/1"))
                .willReturn(aResponse()
                        .withStatus(204)
                        .withStatusMessage("USER DELETED")
                        .withHeader("server", "NALServer")
                )
        );
    }
}
