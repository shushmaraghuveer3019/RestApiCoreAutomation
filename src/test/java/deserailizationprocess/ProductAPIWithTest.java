package deserailizationprocess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class ProductAPIWithTest {

    @Test
    public void getProductsTest_With_POJO_Desierlization() throws JsonProcessingException {

        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given().log().all()
                .when().log().all()
                .get("/products").then().log().all().assertThat().statusCode(200)
                .extract().response();

        response.prettyPrint();

        ObjectMapper mapper = new ObjectMapper();
        Product[] productResponse = mapper.readValue(response.body().asString(), Product[].class);
        System.out.println(Arrays.toString(productResponse));

        for (Product product : productResponse) {
            System.out.println("Id " + product.getId());
            System.out.println("Title " + product.getTitle());
            System.out.println("Price " + product.getPrice());
            System.out.println("Description " + product.getDescription());
            System.out.println("Category " + product.getCategory());
            System.out.println("Image " + product.getImage());
            System.out.println("Rate " + product.getRating().getRate());
            System.out.println("Count " + product.getRating().getCount());

            System.out.println("**********************");
        }
    }
}
