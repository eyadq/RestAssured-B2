package io.loopcamp.test.day01_Intro;


import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
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

        //Difference between prettyprint() and asString()
        //Pretty print shows it in json format
        //asString() shows in single line

        assertTrue(response.asString().contains("George"));
    }
}
