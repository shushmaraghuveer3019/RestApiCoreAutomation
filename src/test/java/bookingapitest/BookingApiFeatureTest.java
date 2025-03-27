package bookingapitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BookingApiFeatureTest {

    //AAA Pattern -> Arrange Act Assert

    String token;

    @BeforeMethod
    public void getAuthTokenTest_WithJsonFile() {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        token = given().log().all()
                .contentType(ContentType.JSON)
                .body(new File("./src/test/resources/jsons/auth.json"))
                .when().log().all()
                .post("/auth")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract().path("token");

        System.out.println("Token is " + token);
    }

    @Test
    public void getBookingIdsTest() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        Response response = given().log().all().contentType(ContentType.JSON)
                .when().log().all().get("/booking")
                .then().log().all().assertThat()
                .statusCode(200)
                .and().extract().response();

        response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
        List<Integer> allBookingIds = jsonPath.getList("bookingid");
        System.out.println("total Booking ids are " + allBookingIds.size());

        //Select count(*) from Booking; --> X

        for (Integer id : allBookingIds) {
            Assert.assertNotNull(id);
        }
    }

    @Test
    public void getBookingId() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        int newBookingId = createBookingId();

        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .pathParams("bookingId", newBookingId).when().log().all()
                .get("/booking/{bookingId}")
                .then().log().all()
                .assertThat().statusCode(200)
                .and().body("firstname", equalTo("API"))
                .and().body("bookingdates.checkin", equalTo("2018-01-01"))
                .and().body("lastname", equalTo("Automation")).extract().response();
        response.prettyPrint();
    }

    @Test
    public void updateBookingId() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        int newBookingId = createBookingId();

        //Perform Get Call
        verifyBookingId(newBookingId);
        //Update the details
        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .header("Cookie", "token=" + token)
                .body("{\n" +
                        "    \"firstname\" : \"Naveen\",\n" +
                        "    \"lastname\" : \"Automation\",\n" +
                        "    \"totalprice\" : 111,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Dinner\"\n" +
                        "}")
                .pathParams("bookingId", newBookingId)
                .put("/booking/{bookingId}")
                .then().log().all()
                .assertThat().statusCode(200).and().body("firstname", equalTo("Naveen"))
                .and().body("additionalneeds", equalTo("Dinner"))
                .and().body("lastname", equalTo("Automation")).extract().response();
        response.prettyPrint();

        //same will be applied for patch call
    }

    @Test
    public void deleteBookingId() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        int newBookingId = createBookingId();

        //Perform Get Call
        verifyBookingId(newBookingId);

        //Update the details
        given().log().all()
                .contentType(ContentType.JSON)
                .header("Cookie", "token=" + token)
                .pathParams("bookingId", newBookingId)
                .delete("/booking/{bookingId}")
                .then().log().all()
                .assertThat().statusCode(201);

    }

    public int createBookingId() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        int bookingId = given().log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"firstname\" : \"API\",\n" +
                        "    \"lastname\" : \"Automation\",\n" +
                        "    \"totalprice\" : 111,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}")
                .when().log().all()
                .post("/booking")
                .then().assertThat().statusCode(200)
                .extract().path("bookingid");
        System.out.println("Booking Id is " + bookingId);
        return bookingId;
    }

    public void verifyBookingId(int bookingId) {
        //Perform Get Call
        given().log().all()
                .contentType(ContentType.JSON)
                .pathParams("bookingId", bookingId).when().log().all()
                .get("/booking/{bookingId}")
                .then().log().all()
                .assertThat().statusCode(200);
    }
}
