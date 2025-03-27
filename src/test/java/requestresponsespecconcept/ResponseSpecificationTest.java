package requestresponsespecconcept;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;


import org.testng.annotations.Test;

import io.restassured.specification.ResponseSpecification;


public class ResponseSpecificationTest {

    @Test
    public void reqSpecTest() {

        ResponseSpecification responseSpecification = expect().statusCode(200).header("Content-Type", "application/json; charset=utf-8")
                .body("userId", equalTo(1));

        given().log().all().baseUri("https://jsonplaceholder.typicode.com")
                .when().log().all().get("/posts/1").then().log().all().spec(responseSpecification);

    }
}
