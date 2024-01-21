package io.loopcamp.test.day02_headers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static  org.junit.jupiter.api.Assertions.*;
import static utilities.Constants.MINIONS_BASE_URL;

public class MinionApiHelloWorld {

    String apiMethod = MINIONS_BASE_URL + "/api/hello";

    /**
     When I send GET request to http://your_ip:8000/api/hello
     ---------------------
     Then status code should be 200
     And response body should be equal to "Hello from Minion"
     And content type is "text/plain;charset=ISO-8859-1"
     */

    @DisplayName("Get api/hello")
    @Test
    public void helloTest(){
        Response response = when().get(apiMethod);
        assertEquals(200, response.statusCode());
    }

    /**
     BDD version of When I send GET request to http://your_ip:8000/api/hello
     ---------------------
     Then status code should be 200
     And response body should be equal to "Hello from Minion"
     And content type is "text/plain;charset=ISO-8859-1"
     */

    @DisplayName("Get api/hello - bdd")
    @Test
    public void helloBDDTest(){
        when().get(apiMethod)
                .then().assertThat().statusCode(200)
                .and().contentType("text/plain;charset=ISO-8859-1");
    }



}
