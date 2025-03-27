package updateuser;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateUserTestUsingLombok {

    public String getRandomEmailId() {
        return "apiAutomation" + System.currentTimeMillis() + "@gmail.com";
    }

    @Test
    public void updateUserWith_Pojo() {

        RestAssured.baseURI = "https://gorest.co.in";

        UserLombok userLombok = new UserLombok("Pooja456", "Pooja456@gmail.com", "female", "active");

        Response postResponse = given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer 81dcd70d5b3e095c23fba7c8ded79f76f4d78320d52e2b444c7daade8728cc2c")
                .body(userLombok)
                .when().log().all()
                .post("/public/v2/users");

        postResponse.prettyPrint();
        Integer userId = postResponse.jsonPath().get("id");
        System.out.println("User Id " + userId);
        System.out.println("*******************");

    }

    @Test
    public void updateUserWith_Builder() {

        RestAssured.baseURI = "https://gorest.co.in";

        //Creating user Object using Lombok Builder Pattern
        UserLombok userLombok = new UserLombok.UserLombokBuilder()
                .name("Pooja Sharma123")
                .status("active")
                .email(getRandomEmailId())
                .gender("female").build();

        Response postResponse = given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer 81dcd70d5b3e095c23fba7c8ded79f76f4d78320d52e2b444c7daade8728cc2c")
                .body(userLombok)
                .when().log().all()
                .post("/public/v2/users");

        postResponse.prettyPrint();
        Integer userId = postResponse.jsonPath().get("id");
        System.out.println("User Id " + userId);
        System.out.println("*******************");

    }
}