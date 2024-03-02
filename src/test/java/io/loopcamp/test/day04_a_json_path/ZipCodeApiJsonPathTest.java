package io.loopcamp.test.day04_a_json_path;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZipCodeApiJsonPathTest {

    @BeforeEach
    public void setup(){
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    /**
     ***Zip code task.  http://api.zippopotam.us/us/22031
     Given Accept application/json
     And path zipcode is 22031
     When I send a GET request to /us endpoint
     Then status code must be 200
     And content type must be application/json
     And Server header is cloudflare
     And Report-To header exists
     And body should contains following information
     post code is 22031
     country  is United States
     country abbreviation is US
     place name is Fairfax
     state is Virginia
     latitude is 38.8604
     */
    @DisplayName("GET /zipcode/us/")
    @Test
    public void zipcodeJsonPathTest(){
        Response response =
                given().accept(ContentType.JSON)
                .and().pathParam("country", "us")
                .and().pathParam("zipcode", "22031")
                .when().get("/{country}/{zipcode}");

        //response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        //assigning response json payload/body to jsonPath
        JsonPath jsonPath = response.jsonPath();

        System.out.println("post code: " + jsonPath.getString("'post code'")); //Note '' for spaces
        System.out.println("country: " + jsonPath.getString("country"));
        System.out.println("country abbreviation: " + jsonPath.getString("'country abbreviation'"));

        System.out.println("state abbreviation: " + jsonPath.getString("places[0].'state abbreviation'"));


        assertEquals("22031", jsonPath.getString("'post code'"));
        verifyZipCode("22301", jsonPath);
        assertEquals("United States", jsonPath.getString("country"));
        assertEquals("US", jsonPath.getString("'country abbreviation'"));
        assertEquals("Fairfax", jsonPath.getString("places[0].'place name'"));
        assertEquals("Virginia", jsonPath.getString("places[0].state"));
        assertEquals("VA", jsonPath.getString("places[0].'state abbreviation'"));
        assertEquals("38.8604", jsonPath.getString("places[0].latitude"));

    }

    //Since we can to all with .path() method as well, why we do it with jsonPath
    // 1. jsonPath has more methods will helps us filter directly in assertion
    // 2. we can use json path when we want to call a reusable method
    public void verifyZipCode(String expZipCode, JsonPath jsonPath){
        assertEquals(expZipCode, jsonPath.getString("'post code'"));

    }
}
