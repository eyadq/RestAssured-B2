package io.loopcamp.test.day09_a_authorization;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class DocuportAccessToken {
    /**
     Given accept type is Json
     And content type is Json
     And body is:
     {
     "usernameOrEmailAddress": "b1g1_client@gmail.com",
     "password": "Group1"
     }
     When I send POST request to
     Url: https://beta.docuport.app/api/v1/authentication/account/authenticate
     Then status code is 200
     And accessToken should be present and not empty
     */

    @DisplayName("POST /authentication/account/authenticate - request authorization api")
    @Test
    public void docuportLoginTest () {
        String jsonBody = "{\n" +
                "\"usernameOrEmailAddress\": \"b1g1_client@gmail.com\",\n" +
                "\"password\": \"Group1\"\n" +
                "}";


        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(jsonBody)
                .when().post("https://beta.docuport.app/api/v1/authentication/account/authenticate");


        //response.prettyPrint();
        System.out.println("Response Status Code: " + response.statusCode());
        assertThat(response.statusCode(), is(200));


        String accessToken = response.path("user.jwtToken.accessToken");
        System.out.println("Access Token: " + accessToken);


        //  And accessToken should be present and not empty
        assertTrue(accessToken != null && !accessToken.isEmpty());


    }



}