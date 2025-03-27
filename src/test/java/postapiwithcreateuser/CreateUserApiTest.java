package postapiwithcreateuser;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CreateUserApiTest {

    @Test
    public void getAuthTokenTest_WithJsonFile() {

        RestAssured.baseURI = "https://gorest.co.in";

        Integer token = given().log().all()
                .header("Authorization", "Bearer 81dcd70d5b3e095c23fba7c8ded79f76f4d78320d52e2b444c7daade8728cc2c")
                .contentType(ContentType.JSON)
                .body(new File("./src/test/resources/jsons/user.json"))
                .when().log().all()
                .post("/public/v2/users")
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract().path("id");

        System.out.println("User is " + token);
        Assert.assertNotNull(token);
    }

    @Test
    public void getAllUserId() {
        RestAssured.baseURI = "https://gorest.co.in";

        Response response = given().log().all().
                contentType(ContentType.JSON)
                .when().log().all()
                .get("/public/v2/users")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response();

        response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
        List<Integer> userIdList = jsonPath.getList("id");
        System.out.println("User Id List " + userIdList.size());

        for (Integer id : userIdList){
            Assert.assertNotNull(id);
        }

    }

}
