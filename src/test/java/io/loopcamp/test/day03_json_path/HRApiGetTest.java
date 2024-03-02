package io.loopcamp.test.day03_json_path;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HRApiGetTest {

    @BeforeEach
    public void setUp() {
        baseURI = ConfigurationReader.getProperty("hr.api.url");
    }

    /**
     * User should be able to see all regions when sending GET request to /ords/hr/regions
     * Given accept type is json
     * When user send get request to /regions
     * Status code should be 200
     * Content type should be "application/json"
     * And body should contain "Europe"
     */
    @DisplayName("Get regions")
    @Test
    public void getRegionsTest() {
        /*given().log().all()
                .accept(ContentType.JSON)
                .when().get(HR_BASE_URL + "/regions")
                //.when().get("http://44.202.2.68:1000/ords/hr" + "/regions")
                        .then().assertThat().statusCode(HttpStatus.SC_OK);

         */
        Response response =
                given().accept(ContentType.JSON)
                .when().get("/regions");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertTrue(response.body().asString().contains("Europe"));
    }

    /**
     * Given accept type is json
     * And Path param "region_id" value is 10
     * When user send get request to /ords/hr/regions/{region_id}
     * Status code should be 200
     * Content type should be "application/json"
     * And body should contain "Europe"
     */
    @DisplayName("GET /regions/{region_id}")
    @Test
    public void getRegionPathParamTest() {

        Response response =
             given().log().all()
                .and().accept(ContentType.JSON)
                .and().pathParam("region_id", 10)
             .when().get("/regions/{region_id}");

        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        response.prettyPrint();
        //assertTrue(response.body().asString().contains("Europe"));
    }

    /**
     * Given accept type is json
     * And query param q={"region_name": "Americas"}
     * When user send get request to /ords/hr/regions
     * Status code should be 200
     * Content type should be "application/json"
     * And region name should be "Americas"
     * And region id should be "2"
     */
    @DisplayName("GET /regions?q={\"region_name\": \"Americas\"}")
    @Test
    public void getRegionsQueryParamTest() {

        Response response =
                given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_name\": \"Americas\"}")
                .when().get("/regions");

        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertTrue(response.body().asString().contains("Americas"));
        assertTrue(response.body().asString().contains("20"));
    }


}
