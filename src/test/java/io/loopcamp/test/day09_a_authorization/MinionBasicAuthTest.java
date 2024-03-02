package io.loopcamp.test.day09_a_authorization;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import utilities.MinionSecureTestBase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionBasicAuthTest extends MinionSecureTestBase {

    /**
     given accept type is json
     and basic auth credentials are loopacademy , loopacademy
     When user sends GET request to /minions
     Then status code is 200
     And content type is json
     And print response
     */

    @Test
    public void getMinionsWithAuthTest(){
        given().accept(ContentType.JSON)
                .and().auth().basic("loopacademy", "loopacademy")
                .when().get("/minions")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON).log().all();

    }

    /**
     * Negative or Rainy Test
     given accept type is json

     When user sends GET request to /minions
     Then status code is 401
     And content type is json
     And error is "Unauthorized"
     And log response
     */
    @Test
    public void getAlMinionsUnauthorizedTest(){
        given().accept(ContentType.JSON)
                .when().get("/minions")
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().contentType(ContentType.JSON)
                .and().body("error", equalTo("Unauthorized"))
                .log().all();
    }
}
