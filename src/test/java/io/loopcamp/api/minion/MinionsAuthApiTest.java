package io.loopcamp.api.minion;

import io.loopcamp.pojo.Minion;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.ConfigurationReader;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MinionsAuthApiTest extends MinionsApiTestBase {


    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("minions.secure.api.base");
    }


    @Test
    public void testGetMinions() {
        List<Minion> minions =
            given().spec(getAuthAndJsonHeadersRequestSpecification())
                .when().get("/api/minions")
                .then().spec(getResponseSpecification(HttpStatus.SC_OK, allMinionsSchemaFilePath))
                .extract().jsonPath().getList("", Minion.class);

        minions.forEach(MinionsAuthApiTest::verifyMinionPojo);
    }


    @Test
    public void testGetMinions2() {
        List<Minion> minions =
                given()
                        .headers(getBasicAuthHeader())
                        .headers(getJsonHeaders())
                        .body(getRandomizedPayload())
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
                given().spec(getAuthAndJsonHeadersRequestSpecification())
                        .when().get("/api/minions/{id}", minionId)
                        .then().spec(getResponseSpecification(HttpStatus.SC_OK, singleMinionSchemaFilePath))
                            .body("id", equalTo(minionId))
                            .extract().as(Minion.class);

        verifyMinionPojo(minion);
    }

    @Test
    public void testPostMinion(){
        int minionId =
                given().spec(getAuthAndJsonHeadersRequestSpecification())
                        .body(getRandomizedPayload())
                    .when().post("/api/minions")
                    .then().spec(getResponseSpecification(HttpStatus.SC_CREATED, postMinionSchemaFilePath))
                        .extract().jsonPath().getObject("data", Minion.class).getId();

        deleteMinion(minionId);
    }


    @Test
    public void testPatchMinion() {
        Map<String, String> newPhoneNumberPayload = Map.of("phone",  "2222222222");

        int minionId = getNewMinionUsingRandomizedPayload().getId();

        given().spec(getAuthAndJsonHeadersRequestSpecification())
                    .body(newPhoneNumberPayload)
                .when().patch("/api/minions/{id}", minionId)
                .then().statusCode(HttpStatus.SC_NO_CONTENT);

        verifyMinionPojo(getMinion(minionId), minionId, newPhoneNumberPayload);

        deleteMinion(minionId);
    }


    @Test
    public void testPutMinion() {
        int minionId = getNewMinionUsingRandomizedPayload().getId();

        given().spec(getAuthAndJsonHeadersRequestSpecification())
                    .body(getNonRandomizedPayload())
                .when().put("/api/minions/{id}", minionId)
                .then().statusCode(HttpStatus.SC_NO_CONTENT);

        verifyMinionPojo(getMinion(minionId),minionId, getNonRandomizedPayload());

        deleteMinion(minionId);
    }


    @Test
    public void testSearchMinions() {
        String genderSearch = "Male";
        String nameContains = "ad";

        assertThat(genderSearch, is(in(Arrays.asList("Male", "Female")))); //Make sure genderSearch has proper term

        given().spec(getAuthAndJsonHeadersRequestSpecification())
                    .queryParams(getQueryParams(genderSearch, nameContains))
                .when().get("/api/minions/search")
                .then().statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getList("content", Minion.class)
                .forEach(each -> {
                    assertThat(each.getGender(), is(genderSearch));
                    assertThat(each.getName(), containsStringIgnoringCase(nameContains));
                });
    }


}
