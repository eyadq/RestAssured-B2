package io.loopcamp.test.day09_a_authorization;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import utilities.DocuportApiTestBase;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DocuportApiTest extends DocuportApiTestBase {

    /**
     Given accept type is json
     And header Authorization is access_token of client
     When I send GET request to  /api/v1/identity/departments/all
     Then status code is 200
     And content type is json
     And departments info is presented in payload
     */

    @Test
    public void getAllDepartmentsTest(){
        String accessToken = getAccessToken("employee");
        System.out.println("Access token for EMPLOYEE: " + accessToken);

        given().accept(ContentType.JSON)
                .and().header("Authorization", accessToken)
                .and().when().get("/api/v1/identity/departments/all")
                .prettyPrint();
    }

    /**
     * Given accept type is Json
     * And header Authorization is access token of supervisor
     * When I send GET request to "/api/v1/company/states/all/"
     * Then status code is 200
     * And content type is json
     * And state "Washington D.C." is in the response body
     */
    @Test
    public void getAllStatesTest(){
        String accessToken = getAccessToken("supervisor");
        System.out.println("Access token for EMPLOYEE: " + accessToken);

                given().accept(ContentType.JSON)
                    .and().header("Authorization", accessToken)
                .and().when().get("/api/v1/company/states/all/")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                    .and().contentType(ContentType.JSON)
                    .and().body("name", hasItem("Washington D.C."))
                .log().all();
    }

    /**
     Given accept type is Json
     And header Authorization is access token for advisor
     When I send GET request to /api/v1/document/clients/all
     Then status code is 200
     And content type is json
     body matches data:
     {
     "id": 31,
     "name": "3tseT",
     "clientType": 1,
     "isActive": true,
     "advisor": null
     }

     */
    @Test
    public void getAllClientsTest(){
        List<Map<String, Object>> clientlistMap =
                given().accept(ContentType.JSON)
                .and().header(getAuthHeader("advisor"))
                .when().get("/api/v1/document/clients/all")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().contentType(ContentType.JSON)
                        .and().extract().body().as(List.class);
                //.and().log().all();

        System.out.println(clientlistMap);
        System.out.println(clientlistMap.size());

        assertThat(clientlistMap.get(0).get("id"), equalTo(31));
        assertThat(clientlistMap.get(0).get("name"), equalTo("3tseT"));
        assertThat(clientlistMap.get(0).get("clientType"), equalTo(1));
        assertThat(clientlistMap.get(0).get("isActive"), equalTo(true));
        assertThat(clientlistMap.get(0).get("advisor"), equalTo(null));
    }


}
