package deserailizationprocess;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserAPITestWithJsonArrayresponse {

    @Test
    public void createUserWith_Lombok() {
        RestAssured.baseURI = "https://gorest.co.in";


        //1 Get all the users
//
        Response getResponse = given().log().all()
                .header("Authorization", "Bearer 81dcd70d5b3e095c23fba7c8ded79f76f4d78320d52e2b444c7daade8728cc2c")
                .when().log().all()
                .get("/public/v2/users/")
                .then().log().all().extract().response();

        //De-Serialization: JSON to POJO
        ObjectMapper mapper = new ObjectMapper();
        try {
            User[] userResponse = mapper.readValue(getResponse.getBody().asString(), User[].class);

            for (User user : userResponse) {
                System.out.println("Id " + user.getId());
                System.out.println("Name " + user.getName());
                System.out.println("Status " + user.getStatus());
                System.out.println("Gender" + user.getGender());
                System.out.println("Email " + user.getEmail());

                System.out.println("******************");
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
