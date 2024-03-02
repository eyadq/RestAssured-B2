package io.loopcamp.test.day10_a_specifications;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import utilities.DocuportApiTestBase;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocuportSpecTest extends DocuportApiTestBase {



    /**
     Given accept type is json
     And header Authorization is access_token of employee title
     When I send GET request to /api/v1/company/organizer-titles/all
     Then status code is 200
     And content type is json
     And employee title info is presented in payload
     */

    @Test
    public void employeeInfoTest(){
         List<Map<String, Object>>  list =
                 given().spec(requestSpecification)
                .when().get("/api/v1/company/organizer-titles/all")
                .then().spec(responseSpecification)
                .and().extract().body().as(List.class);

        System.out.println("list: " + list);

        assertEquals("1", list.get(0).get("value"));
        assertEquals("President", list.get(0).get("displayText"));

        given().spec(requestSpecification)
                .get("/api/v1/company/organizer-titles/all")
                .then().spec(responseSpecification)
                .and().body("value[0]", is(equalTo("1")))
                .and().body("displayText[0]", is(equalTo("President")))
                .and().body("value", hasItems("1", "2", "3", "4"))
                .and().body("displayText", hasItems("President", "Vice President", "Treasurer", "Secretary"));
    }


}
