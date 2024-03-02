package io.loopcamp.test.day03_json_path;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utilities.HRTestBase;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HRApiPathMethodTest extends HRTestBase {

    /**
     Given accept is json
     When I send get request to /countries
     And status code is 200
     And first country_id value is AR
     And first country_name value is Argentina
     */
@DisplayName("GET /countries with path")
@Test
public void readCountryUsingPathTest(){
    Response response =
            given().accept(ContentType.JSON)
            .when().get("/countries");

    assertEquals(HttpStatus.SC_OK, response.statusCode());
    System.out.println("1st country id: " + response.path("items[0].country_id"));
    System.out.println("1st country name: " + response.path("items[0].country_name"));

    assertEquals("AR", response.path("items[0].country_id"));
    assertEquals("Argentina", response.path("items[0].country_name"));

    //Can you store all country names into list
    List<String> listAllCountryNames = response.path("items.country_name");
    System.out.println("All country names: " + listAllCountryNames);
}

}
