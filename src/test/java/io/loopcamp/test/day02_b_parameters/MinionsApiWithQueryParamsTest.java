package io.loopcamp.test.day02_b_parameters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utilities.Constants.MINIONS_BASE_URL;

public class MinionsApiWithQueryParamsTest {

    String apiMethod = MINIONS_BASE_URL + "/api/minions/search";

    /**
     Given Accept type is Json
     And query parameter values are:
     gender|Female
     nameContains|e
     When user sends GET request to /api/minions/search
     ---------------------------
     Then response status code should be 200
     And response content-type: application/json
     And "Female" should be in response payload
     And "Janette" should be in response payload
     */
    String endpoint = "http://18.206.236.92:8000/api/minions/search";

    @DisplayName(" GET /api/minions/search with querry params")
    @Test
    public void searchForMinionTest () {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("gender", "Female")
                .and().queryParam("nameContains", "e")
                .when().get(endpoint);

        // verify is the status code is 200
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        // verify response header content-typ
        assertEquals(ContentType.JSON.toString(), response.contentType());

        // And "Female" should be in response payload
        // And "Janette" should be in response payload
        assertTrue(response.body().asString().contains("Female"));
        assertTrue(!response.body().asString().contains("Male")); // true
        assertTrue(response.body().asString().contains("Janette"));

    }


    enum Gender {
        MALE,
        FEMALE,
        NONE
    }

    /**
     Don't parse parameters with way
     */
    @DisplayName("GET /api/minions/{id} with invalid id")
    @Test
    public void wrongWayToParseParameters() {

        /*=========We Set These Two==========*/
        Gender gender = Gender.MALE;
        String nameContains = "C";

        apiMethod = getUrl(gender, nameContains);

        Response response = given().accept(ContentType.JSON)
                .when().get(apiMethod);

        response.prettyPrint();

    }

    /**
     *
     * @param gender Type of gender to be sent, can be set to None
     * @param nameContains Can be empty if you dont want to search for name
     * @return
     */
    public String getUrl(Gender gender, String nameContains){
        if(!nameContains.isEmpty()){
            nameContains = "nameContains=" + nameContains;
        }
        String genderQuery = "";
        switch(gender){
            case MALE:
                genderQuery = "gender=Male";
                break;
            case FEMALE:
                genderQuery = "gender=Female";
                break;
            case NONE:
                genderQuery = "";
        }

        String apiMethod = MINIONS_BASE_URL + "/api/minions/search";
        String firstCharacter = (gender == Gender.NONE && genderQuery.equals("")) ? "" : "?";
        String and = (gender.equals(Gender.NONE)) ? "" : "&";

        apiMethod = apiMethod + firstCharacter + genderQuery + and + nameContains;

        return apiMethod;
    }
}
