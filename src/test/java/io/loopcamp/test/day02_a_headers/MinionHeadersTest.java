package io.loopcamp.test.day02_a_headers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utilities.Constants.MINIONS_BASE_URL;

public class MinionHeadersTest {

    String requestUrl = MINIONS_BASE_URL + "/api/minions";

    /**
     • When I send a GET request to --- > minions_base_url/api/minions -- > your_id:8000 = minions_base_url
     -------------
     • Then Response STATUS CODE must be 200
     • And I Should see all Minions data in XML format
     */

    @DisplayName("GET /api/minions and expect default XML format")
    @Test
    public void getAllMinionsHeadersTest () {
        when().get(requestUrl)
                .then().assertThat().statusCode(200)
                .and().contentType((ContentType.XML)); //More dynamic than hardcoding the type such as next line
                //.and().contentType(("application/xml"));
    }

    @DisplayName("GET /api/minions with request header")
    public void acceptTypeHeaderTest(){
        //given().accept("application/json")
        given().accept(ContentType.JSON)    //More dynamic using ENUMS
                .when().get(requestUrl)
                .then().assertThat().statusCode(200)
                //.and().contentType("application/json");
                .and().contentType(ContentType.JSON);
    }

    @DisplayName("GET /api/minions with requested header JSON - read headers")
    @Test
    public void readResponseHeaders() {
        Response response =given().accept(ContentType.JSON)
                .get(requestUrl);

        assertEquals(200, response.statusCode());

        //How to read each header: .getHeader(String key)
        System.out.println("Date Header: " + response.getHeader("Date"));
        System.out.println("Content-Type Header: " + response.getHeader("Content-Type"));
        System.out.println("Connection Header: " + response.getHeader("Connection"));
        System.out.println();

        //How to read each header: .getHeader(String key)
        System.out.println("response.getHeaders() = " + response.getHeaders());
        //assertTrue(response.);


    }
}
