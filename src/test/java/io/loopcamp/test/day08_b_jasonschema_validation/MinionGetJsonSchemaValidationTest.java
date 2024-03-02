package io.loopcamp.test.day08_b_jasonschema_validation;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import utilities.MinionTestBase;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionGetJsonSchemaValidationTest extends MinionTestBase {

    /**
     given accept type is json
     and path param id is 15
     when I send GET request to /minions/{id}
     Then status code is 200
     And json payload/body matches SingleMinionSchema.json
     */

    @Test
    public void singleMinionSchemaValidationTest(){
        given().accept(ContentType.JSON)
                .and().pathParam("id", 106)
                .when().get("/minions/{id}")
                .then().statusCode(HttpStatus.SC_OK)
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/test/resources/jsonschemas/SingleMinionSchema.json")
                ))
                .and().log().all();
    }

    /**
     given accept type is json
     when I send GET request to /minions
     Then status code is 200
     And json payload/body matches AllMinionsSchema.json
     */

    @Test
    public void allMinionsSchemaValidationTest(){
        given().accept(ContentType.JSON)
                .when().get("/minions/")
                .then().statusCode(HttpStatus.SC_OK)
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/test/resources/jsonschemas/AllMinionsSchema.json")
                ))
                .and().log().all();
    }

    /**
     given accept type is json
     And query params: nameContains "e" and gender "Female"
     when I send GET request to /Minions/search
     Then status code is 200
     And json payload/body matches SearchMinionsSchema.json
     */
    @Test
    public void minionSearchSchemaValidationTest(){
        given().accept(ContentType.JSON)
                .and().queryParam("nameContains", "e")
                .and().queryParam("gender", "Female")
                .when().get("/minions/search")
                .then().statusCode(HttpStatus.SC_OK)
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/test/resources/jsonschemas/SearchMinionSchema.json")
                ))
                .and().log().all();
    }

}
