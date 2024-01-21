package io.loopcamp.test.day02_headers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utilities.Constants.MINIONS_BASE_URL;

public class MinionApiWithParamsTest {

    String apiMethod = MINIONS_BASE_URL + "/api/minions";

    /**
     Given accept type is Json
     And Id path parameter value is 5
     When user sends GET request to /api/minions/{id}
     ------------------------
     Then response status code should be 200
     And response content-type: application/json
     And "Blythe" should be in response payload(body)
     */
    @DisplayName("GET /api/minions/{id}")
    @Test
    public void getSingleMinionTest(){
        int id = 5;
        given().accept(ContentType.JSON)
                .when().get(apiMethod + "/" + id);

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", id) //id value here is passed using .pathParam()
                .when().get(apiMethod + "/{id}");

        response.prettyPrint();

        //Then status code should be 200
        assertEquals(HttpStatus.SC_OK, response.statusCode()); //ttpStatus.SC_OK is constant of int 200

        //And response content-type: application/json
        assertEquals(io.restassured.http.ContentType.JSON.toString(), response.getContentType());

        // And "Blythe" should be in response payload(body)
        assertTrue(response.body().asString().contains("Blythe"));
    }

    /**
     *         Given accept type is Json
     *         And Id parameter value is 500
     *         When user sends GET request to /api/minions/{id}
     *          -----------------------------
     *         Then response status code should be 404
     *         And response content-type: application/json
     *         And "Not Found" message should be in response payload
     */
    @DisplayName("GET /api/minions/{id} with invalid id")
    @Test
    public void getSingleMinionNegativeTest() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 500)
                .when().get(apiMethod + "/{id}");

        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertTrue(response.body().asString().contains("Not Found"));

    }
}
