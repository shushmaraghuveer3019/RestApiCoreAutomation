package postwithauthapi;

import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import pojo.Credentials;

import java.io.File;

import static io.restassured.RestAssured.given;

public class AuthApiTest {

    @Test
    public void getAuthTokenTest() {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        String token = given().log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\" : \"password123\"\n" +
                        "}")
                .when().log().all()
                .post("/auth")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract().path("token");

        System.out.println("Token is " + token);
        Assert.assertNotNull(token);
    }

    @Test
    public void getAuthTokenTest_WithJsonFile() {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        String token = given().log().all()
                .contentType(ContentType.JSON)
                .body(new File("./src/test/resources/jsons/auth.json"))
                .when().log().all()
                .post("/auth")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract().path("token");

        System.out.println("Token is " + token);
        Assert.assertNotNull(token);
    }

    //pojo --> json: serialization
    //json--->pojo: de-serialization
    //jackson-databind -> this is the library, this is best
    //gson/yasson
    @Test
    public void getAuthTokenTest_WithPOJOClass() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        Credentials credentials = new Credentials("admin", "password123");

        String token = given().log().all()
                .contentType(ContentType.JSON)
                .body(credentials) //POJO to JSON: serialization: ObjectMapper(Jackson Lib)
                .when().log().all()
                .post("/auth")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().path("token");
        System.out.println("Token is " + token);
        Assert.assertNotNull(token);
    }
}
