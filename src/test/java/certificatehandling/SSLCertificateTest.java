package certificatehandling;


import io.restassured.response.Response;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;

public class SSLCertificateTest {

    @Test
    public void sslTest() {
        Response response = RestAssured.given().log().all()
                .relaxedHTTPSValidation() //we need off the cert validation
                .when().log().all()
                .get("https://untrusted-root.badssl.com/")
                .then().log().all().extract().response();
        response.prettyPrint();
    }

    @Test
    public void sslTest_With_Config() {
        RestAssured.config = RestAssuredConfig.config()
                .sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation());
        RestAssured.given().log().all()
                .when()
                .get("https://untrusted-root.badssl.com/")
                .then().log().all()
                .statusCode(200);
    }

}
