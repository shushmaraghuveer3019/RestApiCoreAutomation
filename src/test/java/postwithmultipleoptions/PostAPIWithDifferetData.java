package postwithmultipleoptions;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PostAPIWithDifferetData {

    @Test
    public void bodyWithTextTest() {

        RestAssured.baseURI = "https://postman-echo.com";

        String payload = "hi, this is Naveen";

        given().log().all()
                .contentType(ContentType.TEXT)
                .body(payload)
                .when().log().all()
                .post("/post")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void bodyWithJavaScriptTest() {
        RestAssured.baseURI = "https://postman-echo.com";

        String payload = "<script>\n"
                + "var x, y, z;  \n"
                + "x = 5;    \n"
                + "y = 6;    \n"
                + "z = x + y;  \n"
                + "document.getElementById(\"demo\").innerHTML =\n"
                + "\"The value of z is \" + z + \".\";\n"
                + "</script>";

        given().log().all()
                .contentType("application/javascript")
                .body(payload)
                .when().log().all()
                .post("/post")
                .then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void bodyWithHTMLTest() {
        RestAssured.baseURI = "https://postman-echo.com";

        String payload = "<html>\n"
                + "<body>\n"
                + "    <h2>Demo JavaScript in Head</h2>\n"
                + "    <p id=\"demo\">A Paragraph.</p>\n"
                + "    <button type=\"button\" onclick=\"myFunction()\">Try it</button>\n"
                + "</body>\n"
                + "</html>";

        given().log().all()
                .contentType(ContentType.HTML)
                .body(payload)
                .when().log().all()
                .post("/post")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void bodyWithXMLTest() {

        RestAssured.baseURI = "https://postman-echo.com";

        String payload = "<dependency>\n" +
                "            <groupId>com.fasterxml.jackson.dataformat</groupId>\n" +
                "            <artifactId>jackson-dataformat-xml</artifactId>\n" +
                "            <version>2.17.2</version>\n" +
                "        </dependency>";

        given().log().all()
                .contentType("application/xml;charset=utf-8")
                .body(payload)
                .when().log().all()
                .post("/post")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void bodyWithMultiPartTest() {
        RestAssured.baseURI = "https://postman-echo.com";

        given().log().all()
                .contentType(ContentType.MULTIPART)
                .multiPart("filename", new File("src/test/resources/image/test.jpeg"))
                .multiPart("name", new File("src/test/resources/image/test1.jpeg"))
                .when().log().all()
                .post("/post")
                .then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void bodyWithPdfFileTest() {

        RestAssured.baseURI = "https://postman-echo.com";

        given().log().all()
                .contentType("application/pdf")
                .body(new File("src/test/resources/image/policy.pdf"))
                .when().log().all()
                .post("/post")
                .then().log().all()
                .assertThat().statusCode(200);


    }

}
