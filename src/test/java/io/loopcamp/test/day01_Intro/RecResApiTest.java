package io.loopcamp.test.day01_Intro;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static  org.junit.jupiter.api.Assertions.*;


public class RecResApiTest {
    /**
     * When user sends Get request to
     *      https://reqres.in/api/users/
     *
     * Then RESPONSE STAUS CODE is 200
     * And RESPONDSE BODY should contain "George"
     */

    String endpoint = "https://reqres.in/api/users/";

    @DisplayName("get all users")
    @Test
    public void usersGetTest () {
        //When user sends Get response

        //Since we did static import, we can use get() directly without classname
        //Response response = RestAssured.get(endpoint);
        Response response = when().get(endpoint); //For Gherkin language, we use when() method

        // Then RESPONSE STAUS CODE is 200
        System.out.println("Status Code = " + response.statusCode());
        assertEquals(200, response.statusCode());

        //BDD Syntax - Gherkin syntax
        response.then().statusCode(200); //Same as above validation
        response.then().assertThat().statusCode(200); // " "

        // And RESPONDSE BODY should contain "George"
        System.out.println("Response Body = " + response.asString());
        System.out.println("Response Body = " + response.prettyPrint());
        System.out.println("Response Body = " + response.getContentType());

        //Difference between prettyprint() and asString()
        //Pretty print shows it in json format
        //asString() shows in single line

        assertTrue(response.asString().contains("George"));
    }

    /** NEW TEST CASE
     When User Sends get request to API Endpoint:
     "https://reqres.in/api/users/5"
     ------------------------------------------------------
     Then Response status code should be 200
     And Response body should contain user info "Charles"
     And Response header content is "application/json; charset=utf-8"
     */
    @DisplayName("GET single user")
    @Test
    public void getSingleUserApiTest () {
        Response response = when().get(endpoint + 5);

        assertEquals(200, response.statusCode());

        // And response body should contain "Charles"
        assertTrue(response.asString().contains("Charles"));

        //And content type header is json
        assertTrue(response.getContentType().contains("application/json"));
    }

    /** NEW TEST CASE
     When User Sends get request to API Endpoint:
     "https://reqres.in/api/users/50"
     ------------------------------------------------------
     Then Response status code should be 404
     And Response body should be '{}"
     And Response header content is "application/json; charset=utf-8"
     */
    @DisplayName("GET single user")
    @Test
    public void getSingleUserNegativeApiTest () {
        Response response = when().get(endpoint + 50);

        // Response code is client's error: 400
        assertEquals(400, response.statusCode());

        // And response body is empty
        assertEquals("{}", response.asString());

        //And content type header is json
        assertTrue(response.getContentType().contains("application/json"));
    }
}
