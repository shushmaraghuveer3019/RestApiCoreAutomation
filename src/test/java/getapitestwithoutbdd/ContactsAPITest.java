package getapitestwithoutbdd;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ContactsAPITest {

    @Test
    public void getProductList() {

        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2IwMzRjNjRmNTU4MzAwMTMzNDY0ZTgiLCJpYXQiOjE3Mzk2MDEwOTR9.mP1XsHP0N4OnB1iC6eQX24zaW05kX_ej0XTrceo9sVA");
        Response response = requestSpecification.get("/contacts");
        int statusCode = response.statusCode();
        System.out.println("Status Code " + statusCode);
        Assert.assertEquals(statusCode, 200);
        String statusLine = response.statusLine();
        System.out.println("Status Line " + statusLine);
        Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");

        String responseBody = response.body().prettyPrint();
        System.out.println(responseBody);
    }
}
