package io.loopcamp.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.loopcamp.api.minion.MinionsApiTestBase;
import io.loopcamp.pojo.Minion;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import utilities.ConfigurationReader;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinionApiTestStepDefs extends MinionsApiTestBase {

    String method;
    int minionId;
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder(); //lets us form a single request step by step
    Response response;
    ValidatableResponse validate;

    /*
     =====================================
        Set baseURI
     =====================================
    */


    @Given("baseURI is {string}")
    public void base_uri_is(String base) {
        baseURI = ConfigurationReader.getProperty(base);
        requestSpecBuilder.setBaseUri(baseURI); //Have to set it twice like this for some of these requests to work; not sure why
    }


    /*
     =====================================
        Set request headers
     =====================================
    */


    @Given("request header Accept is {string}")
    public void request_header_accept_is(String contentType) {
        requestSpecBuilder.setAccept(contentType);
    }


    @Given("request Content-Type Accept is {string}")
    public void request_content_type_accept_is(String contentType) {
        requestSpecBuilder.setContentType(contentType);
    }


    /*
     =====================================
        Set query params
     =====================================
    */


    @Given("query parameters are")
    public void query_parameters_are(Map<String, String> queryParams) {
        requestSpecBuilder.addQueryParams(queryParams);
        //requestSpecBuilder.addQueryParams(Map.of("nameContains", "ad"));
        //requestSpecBuilder.addQueryParams(getQueryParams("Male", "ad"));
    }


    /*
     =====================================
        Set request body
     =====================================
    */


    @When("with request body of randomized payload")
    public void with_request_body_of_randomized_payload() {
        requestSpecBuilder.setBody(getRandomizedPayload());
    }


    @Given("with request body of this non random payload")
    public void with_request_body_of_this_non_random_payload(Map<String, String> nonRandomPayload) {
        requestSpecBuilder.setBody(nonRandomPayload);
    }

    /*
     =====================================
        Set Path parameters
     =====================================
    */


    @When("with path {string}")
    public void with_path_(String path) {
        requestSpecBuilder.setBasePath(path);
    }

    @When("with path params")
    public void with_path_params(Map<String, String> pathParams) {

        String path = pathParams.get("path");
        String key = "";
        for (String eachKey : pathParams.keySet()) {
            if (!eachKey.equals("path"))
                key = eachKey;
        }

        String sub = path.substring(path.indexOf("{"));
        System.out.println(key);
        if(key.isEmpty()){
            //minionId is not in map, so use class variable
            path = path.replace(sub, String.valueOf(minionId));

        } else {
            //minionId will be in the map
            path = path.replace(sub, String.valueOf(pathParams.get(key)));
        }

        System.out.println(path);
        requestSpecBuilder.setBasePath(path);


    }


     /*
     =====================================
        Set HTTP method
     =====================================
    */


    @When("the HTTP method is GET")
    public void the_http_method_is_get() {
        response = given().spec(requestSpecBuilder.build()).when().get();
        validate = response.then();
        validate.log().all();
    }


    @When("the HTTP method is POST")
    public void the_http_method_is_post() {
        response = given().spec(requestSpecBuilder.build()).when().post();
        validate = response.then();
        minionId = validate.extract().path("data.id");
        validate.log().all();
    }


    @When("the HTTP method is PUT")
    public void the_http_method_is_put() {
        response = given().spec(requestSpecBuilder.build()).when().put();
        validate = response.then();
        validate.log().all();
    }


    @When("the HTTP method is PATCH")
    public void the_http_method_is_patch() {
        response = given().spec(requestSpecBuilder.build()).when().patch();
        validate = response.then();
        validate.log().all();
    }


    /*
     =====================================
        Make Assertions
     =====================================
    */


    @Then("status code will be {int}")
    public void status_code_will_be(int statusCode) {
        response.then().statusCode(statusCode);
    }


    @Then("response header Content-Type is {string}")
    public void response_header_content_type_is(String contentType) {
        response.then().contentType(contentType);
    }


    @Then("Json response will be validated with the  {string} Schema file")
    public void json_response_will_be_validated_with_the_schema_file(String schema) {
        String filePath = "";
        switch (schema) {
            case "AllMinionsSchema":
                filePath = allMinionsSchemaFilePath;
                break;
            case "SingleMinionSchema":
                filePath = singleMinionSchemaFilePath;
                break;
            case "MinionPostSchema":
                filePath = postMinionSchemaFilePath;
                break;
        }
        response.then().body(JsonSchemaValidator.matchesJsonSchema(new File(filePath)));
    }


    @Then("get minion and validate minion has these values")
    public void get_minion_and_validate_minion_has_these_values(Map<String, String> minionExpected) {
        Map<String, String> minionActual =
                given().contentType(ContentType.JSON)
                        .when().get("/api/minions/{id}", minionId)
                        .then().log().all().extract().as(Map.class);

        for (String key : minionExpected.keySet()) {
            assertEquals(minionExpected.get(key), minionActual.get(key));
        }

    }


    @Then("validate all minions in response has these values")
    public void validate_all_minions_in_response_has_these_values(List<Map<String, String>> valuesExpected) {
        List<Map<String, String>> minions = response.jsonPath().getList("");
        for (Map<String, String> eachMap : valuesExpected) {
            Set<String> keys = eachMap.keySet();

            String key = eachMap.get("key");
            String value = eachMap.get("value");

            System.out.println("With map: " + eachMap);
            System.out.println("\tKey: " + key);
            System.out.println("\tValue: " + value);
            switch (eachMap.get("match")) {
                case "is":
                    minions.forEach(minion -> {
                        assertThat(minion.get(key), is(value));
                    });
                    break;
                case "contains":
                    minions.forEach(minion -> {
                        assertThat(minion.get(key), containsStringIgnoringCase(minion.get(value)));
                    });
                    break;
                default:
                    throw new IllegalArgumentException("Please implement matcher");
            }
        }
    }


    /*
     =====================================
        New minion and clean up
     =====================================
    */

    @Given("create new minion that will be updated")
    public void create_new_minion_that_will_be_updated() {
        minionId = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(getRandomizedPayload())
                .when().post("/api/minions")
                .then().statusCode(201).extract().path("data.id");
    }


    @Then("delete the new Minion")
    public void delete_the_new_minion() {
        given().when().delete("/api/minions/{id}", minionId).then().statusCode(204);
    }
}
