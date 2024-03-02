package io.loopcamp.api.minion;

import io.loopcamp.pojo.Minion;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import net.datafaker.Faker;
import org.apache.http.HttpStatus;
import utilities.ConfigurationReader;

import java.io.File;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MinionsApiTestBase {

    //Schema files to use for validation
    protected String allMinionsSchemaFilePath = "src/test/resources/jsonschemas/AllMinionsSchema.json";
    protected String singleMinionSchemaFilePath = "src/test/resources/jsonschemas/SingleMinionSchema.json";
    protected String postMinionSchemaFilePath = "src/test/resources/jsonschemas/MinionPostSchema.json";
    protected String searchMinionsSchemaFilePath = "src/test/resources/jsonschemas/SearchMinionSchema.json";

    //For Minion Authorization
    static String username = ConfigurationReader.getProperty("minions.secure.username");
    static String password = ConfigurationReader.getProperty("minions.secure.password");


    public static Map<String, String> getRandomizedPayload(){
        Faker faker = new Faker();
        Map<String, String> minion = new HashMap<>();
        minion.put("gender", faker.gender().binaryTypes());
        minion.put("name", faker.name().firstName());
        minion.put("phone", faker.numerify("##########"));
        return minion;
    }


    public static Map<String, String> getNonRandomizedPayload(){
        Map<String, String> minion = new HashMap<>();
        minion.put("gender", "Female");
        minion.put("name", "Dixie Kong");
        minion.put("phone", "7777777777");
        return minion;
    }


    public static Map<String, String> getQueryParams(String genderSearch, String nameContains){
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("gender", genderSearch);
        queryParams.put("nameContains", nameContains);
        return queryParams;
    }


    public static Minion getMinion(int minionId){
        return given().spec(getAuthAndJsonHeadersRequestSpecification())
                .when().get("/api/minions/{id}", minionId)
                .then().extract().as(Minion.class);
    }


    public static Minion getNewMinionUsingRandomizedPayload(){
        return given()
                .headers(getBasicAuthHeader())
                .headers(getJsonHeaders())
                .body(getRandomizedPayload())
                .when()
                .post("/api/minions")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .contentType(ContentType.JSON)
                .extract().jsonPath().getObject("data", Minion.class);
    }


    public static void deleteMinion(int minionId){
        given()
                .headers(getBasicAuthHeader())
                .when()
                .delete("/api/minions/{id}", minionId)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }


    public static void verifyMinionPojo(Minion minion){
        assertThat(minion.getName(), is(not(isEmptyOrNullString())));
        assertThat(minion.getId(), is(greaterThan(0)));
        assertThat(minion.getGender(), is(in(Arrays.asList("Male", "Female"))));
        assertThat(minion.getPhone().length(), is(10));
    }


    public static void verifyMinionPojo(Minion minion, int minionId, Map<String, String> payload){

        assertThat(minion.getId(), is(minionId));
        if(payload.containsKey("name"))
            assertThat(minion.getName(), is(payload.get("name")));
        if(payload.containsKey("gender"))
            assertThat(minion.getGender(), is(payload.get("gender")));
        if(payload.containsKey("phone"))
            assertThat(minion.getPhone(), is(payload.get("phone")));
    }


    public static Map<String, String> getJsonHeaders(){
        return Map.of("Content-type", ContentType.JSON.toString(), "Accept", ContentType.JSON.toString());
    }


    public static Map<String, String> getBasicAuthHeader(){
        String credentials = username + ":" + password;
        String accessToken = Base64.getEncoder().encodeToString(credentials.getBytes());
        return Map.of("Authorization", "Basic " + accessToken);
    }


    public static RequestSpecification getAuthAndJsonHeadersRequestSpecification(){
        return given().headers(getJsonHeaders()).and().headers(getBasicAuthHeader());
    }

    public static ResponseSpecification getResponseSpecification(int httpStatusCode){
        return expect().contentType(ContentType.JSON)
                .statusCode(httpStatusCode);
    }

    public static ResponseSpecification getResponseSpecification(int httpStatusCode, String schemaFilePath){
        return expect().contentType(ContentType.JSON)
                .statusCode(httpStatusCode)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(schemaFilePath)));
    }
}
