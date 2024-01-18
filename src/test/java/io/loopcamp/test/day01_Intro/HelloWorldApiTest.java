package io.loopcamp.test.day01_Intro;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class HelloWorldApiTest {
    String url = "https://sandbox.api.service.nhs.uk/hello-world/hello/world";

    @DisplayName("Hello World GET request")
    @Test
    public void helloWorldGetRequestTest(){
        //send a get request and save response inside the Response object
        Response response = RestAssured.get(url);

        //Print response body in a formatted way - RESPONSE BODY
        response.prettyPrint();

        //Print status code - RESPONSE CODE
        System.out.println("Status Code = " + response.statusCode());
        System.out.println("Status Line = " + response.statusLine());

        //assert that response code is 200
        Assertions.assertEquals(200, response.statusCode(), "Status code did not match");

        // .asString() will return response body as a String
        String actualResponseBody = response.asString();
        System.out.println(actualResponseBody);

        Assertions.assertTrue( actualResponseBody.contains("Hello World!") );

    }

}