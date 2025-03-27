package authapis;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Oauth1APITest {
    //0f4a1d7993ecac89 - permanent-secret

    //Oauth1.0: you need to add extra dependency in your pom.xml
    //name: scribejava-core
    //version: old version: 2.5.3

    @Test
    public void flickerAPITes() {

        RestAssured.baseURI = "https://www.flickr.com";

        Response response = RestAssured.given().log().all()
                .auth().oauth("a110d09788adcf0a3f867e4958a0a3ef", //consumer Key
                        "ecb0b3f9ab7d9f8c", //consumer Secret
                        "72157720929991962-e1c25ed42b7c024c", //permanent-token
                        "0f4a1d7993ecac89")
                .queryParam("nojsoncallback", 1)
                .queryParam("format", "json")
                .queryParam("method", "flickr.test.login")
                .when().log().all()
                .get("/services/rest").then().log().all().assertThat().statusCode(200).extract().response();

        response.prettyPrint();
    }
}
