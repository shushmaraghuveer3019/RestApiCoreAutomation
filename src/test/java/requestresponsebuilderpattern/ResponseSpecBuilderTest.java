package requestresponsebuilderpattern;


import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ResponseSpecBuilderTest {

    public static ResponseSpecification get_res_spec_200() {
        ResponseSpecification respSpecBuild = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectHeader("Server", "cloudflare")
                .build();
        return respSpecBuild;
    }

    public static ResponseSpecification get_res_spec_401_AuthFail() {
        ResponseSpecification respSpecBuild = new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectHeader("Server", "cloudflare")
                .build();
        return respSpecBuild;
    }

    @Test
    public void getUsersTest() {

        RestAssured.baseURI = "https://gorest.co.in";

        given().log().all().header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
                .when().log().all()
                .get("/public/v2/users").then().log().all().assertThat().spec(get_res_spec_200());
    }

    @Test
    public void getUsers_WithInvalidToken_Test() {

        RestAssured.baseURI = "https://gorest.co.in";
        given().log().all().header("Authorization", "Bearer Invalid Token")
                .when().log().all()
                .get("/public/v2/users").then().log().all().assertThat().spec(get_res_spec_401_AuthFail());
    }
}
