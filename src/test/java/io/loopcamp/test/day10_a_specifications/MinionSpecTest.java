package io.loopcamp.test.day10_a_specifications;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import utilities.MinionSecureTestBase;
import utilities.MinionTestBase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
public class MinionSpecTest extends MinionSecureTestBase {

    RequestSpecification requestSpecification = given().accept(ContentType.JSON)
            .and().auth().basic("loopacademy", "loopacademy");

    ResponseSpecification responseSpecification = expect().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON);
    @Test
    public void allMinionTest(){

        //given().accept(ContentType.JSON)
                //.and().auth().basic("loopacademy", "loopacademy")
        given().spec(requestSpecification)
                .when().get("/minions")
                //.then().assertThat().statusCode(HttpStatus.SC_OK)
                //.and().contentType(ContentType.JSON)
                .then().spec(responseSpecification)
                .log().all();

    }

    @Test
    public void singleMinionTest(){

        //given().accept(ContentType.JSON)
                //.and().auth().basic("loopacademy", "loopacademy")
        given().spec(requestSpecification)
                .and().pathParam("id", 10)
                .when().get("/minions/{id}")
                //.then().assertThat().statusCode(HttpStatus.SC_OK)
                //.and().contentType(ContentType.JSON)
                .then().spec(responseSpecification)
                .and().body("name", equalTo("Lorenza"))
                .log().all();

    }
}
