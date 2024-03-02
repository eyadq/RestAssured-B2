package io.loopcamp.api.minion;

import io.loopcamp.pojo.Minion;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.ConfigurationReader;

import java.io.File;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MinionsNoAuthApiTest extends MinionsApiTestBase {


    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("minions.api.base");
    }


    @Test
    public void testGetMinions() {
        List<Minion> minions =
                given()
                        .headers(getJsonHeaders())
                        .when()
                        .get("/api/minions")
                        .then()
                        .contentType(ContentType.JSON)
                        .statusCode(HttpStatus.SC_OK)
                        .body(JsonSchemaValidator.matchesJsonSchema(new File(allMinionsSchemaFilePath)))
                        .extract().jsonPath().getList("", Minion.class);
        minions.forEach(MinionsNoAuthApiTest::verifyMinionPojo);
    }


    @Test
    public void testGetMinion() {
        int minionId = 10;
        Minion minion =
                given().
                        headers(getJsonHeaders()).
                        when().
                        get("/api/minions/{id}", minionId).
                        then().
                        contentType(ContentType.JSON).
                        statusCode(HttpStatus.SC_OK).body("id", equalTo(minionId)).
                        body(JsonSchemaValidator.matchesJsonSchema(new File(singleMinionSchemaFilePath))).
                        extract().as(Minion.class);

        verifyMinionPojo(minion);
    }


    @Test
    public void testPatchMinion() {
        String newPhoneNumber = "2222222222";

        int minionId = getNewMinionUsingRandomizedPayload().getId();

        //Update minion using patch method with update api
        given()
                .headers(getJsonHeaders())
                .body(Map.of("phone", newPhoneNumber))
                .when()
                .patch("/api/minions/{id}", minionId)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        //Get minion of same id, verify it has same id but the new phone number
        given()
                .headers(getJsonHeaders())
                .when()
                .get("/api/minions/{id}", minionId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(minionId))
                .body("phone", is(newPhoneNumber));

        deleteMinion(minionId);
    }


    @Test
    public void testPutMinion() {
        int minionId = getNewMinionUsingRandomizedPayload().getId();

        given()
                .headers(getJsonHeaders())
                .body(getNonRandomizedPayload())
                .when()
                .put("/api/minions/{id}", minionId)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);


        given()
                .headers(getJsonHeaders())
                .when()
                .get("/api/minions/{id}", minionId)
                .then()
                .body("id", is(minionId))
                .body("name", is("Dixie Kong"))
                .body("gender", is("Female"))
                .body("phone", is("7777777777"));

        deleteMinion(minionId);
    }


    @Test
    public void testSearchMinions() {
        String genderSearch = "Male";
        String nameContains = "ad";

        given()
                .queryParams(getQueryParams(genderSearch, nameContains))
                .headers(getJsonHeaders())
                .when()
                .get("/api/minions/search")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(searchMinionsSchemaFilePath)))
                .extract().jsonPath().getList("content", Minion.class)
                .forEach(each -> {
                    assertThat(each.getGender(), is(genderSearch));
                    assertThat(each.getName(), containsStringIgnoringCase(nameContains));
                });
    }
}
