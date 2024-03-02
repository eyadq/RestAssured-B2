package io.loopcamp.tasks.day04_tasks;

import utilities.HRTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Countries extends HRTestBase {

    @DisplayName("GET /countries/{country_id}")
    @Test
    public void singleCountryWithCountryIdPathParam(){
        //Given by assigment
        String targetCountryId = "US";
        String targetCountryName = "United States of America";
        int regionId = 10; //assigment says 2 which this version of DB has all region_id as multiple of 10

        Response response =
                given().accept(ContentType.JSON)
                .and().pathParam("country_id", targetCountryId)
                .when().get("/countries/{country_id}");
        response.prettyPrint();

        JsonPath jsonPath = response.jsonPath();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertEquals(targetCountryId, jsonPath.getString("country_id"));
        assertEquals(targetCountryId, jsonPath.getString("country_id"));
        assertEquals(regionId, jsonPath.getInt("region_id"));

    }

    @DisplayName("GET /employeesq=?{department_id}")
    @Test
    public void employeestWithDepartmentIDIdQueryParam(){
        //Given by assigment
        int targetDepartmentId = 80;
        String targetJobIDsBeginWith = "SA";
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("department_id", targetDepartmentId);

        Response response =
                given().accept(ContentType.JSON)
                        //.and().queryParam("department_id", targetDepartmentId)
                        .and().queryParams(queryMap)
                        .when().get("/employees");
        response.prettyPrint();

//        JsonPath jsonPath = response.jsonPath();
//        assertEquals(HttpStatus.SC_OK, response.statusCode());
//        assertEquals(ContentType.JSON.toString(), response.contentType());
//        assertEquals(targetCountryId, jsonPath.getString("country_id"));
//        assertEquals(targetCountryId, jsonPath.getString("country_id"));
//        assertEquals(regionId, jsonPath.getInt("region_id"));

    }
}
