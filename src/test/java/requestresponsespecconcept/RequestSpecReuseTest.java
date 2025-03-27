package requestresponsespecconcept;


import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.specification.RequestSpecification;

public class RequestSpecReuseTest {

    RequestSpecification requestSpecification;

    @BeforeTest
    public void setUp() {
        requestSpecification = given().log().all().baseUri("https://gorest.co.in")
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6");
    }

    @Test
    public void getUserTest() {
        requestSpecification.when().log().all()
                .get("/public/v2/users").then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void getUserTest_Id() {
        requestSpecification.when().log().all()
                .get("/public/v2/users/7738343").then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void getWrongUserTest() {
        requestSpecification.when().log().all()
                .get("/public/v2/users/1").then().log().all()
                .assertThat().statusCode(404);
    }
}

