package io.loopcamp.test.day08_b_jasonschema_validation;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import utilities.MinionRestUtils;
import utilities.MinionTestBase;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;


public class MinionPostJsonSchemaValidationTest extends MinionTestBase {

    /**
     given accept is json and content type is json
     and body is:
     {
     "gender": "Male",
     "name": "TestPost1"
     "phone": 1234567425
     }
     When I send POST request to /minions
     Then status code is 201
     And body should match MinionPostSchema.json schema
     */
    @Test
    public void postMinionSchemaValidationTest(){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gender", "Male");
        requestMap.put("name", "TestPost100");
        requestMap.put("phone", 1987654321);

        int newMinionId =
                given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(requestMap)
                .when().post("/minions")
                .then().statusCode(HttpStatus.SC_CREATED)
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/test/resources/jsonschemas/MinionPostSchema.json")
                ))
                .log().all()
                .and().extract().jsonPath().getInt("data.id");


        MinionRestUtils.deleteMinionById(newMinionId);
    }
}
