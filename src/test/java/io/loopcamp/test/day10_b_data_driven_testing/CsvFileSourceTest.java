package io.loopcamp.test.day10_b_data_driven_testing;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.RestAssured.*;

public class CsvFileSourceTest {

    @BeforeAll
    public static void setUp () {
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }



    @ParameterizedTest
    @CsvFileSource(resources = "/ZipCodes.csv", numLinesToSkip = 1)
    public void zipCodeTest (String state, String city) {

        Map <String, String> data = new HashMap<>();
        data.put("state", state);
        data.put("city", city);

        given().accept(ContentType.JSON)
                .and().pathParams(data)
                .when().get("/US/{state}/{city}")
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON)
                //.and().body("places", hasSize(3))
                .log().all();

    }





}