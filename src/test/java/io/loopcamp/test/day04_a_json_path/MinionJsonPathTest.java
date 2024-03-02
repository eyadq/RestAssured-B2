package io.loopcamp.test.day04_a_json_path;

import utilities.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinionJsonPathTest extends MinionTestBase {

    @DisplayName("GET /minions/{id}")
    @Test
    public void readMinionsIdWithPathTest(){
        Response response =
                given().accept(ContentType.JSON)
                .and().pathParam("id", "13")
                .when().get("/minions/{id}");

        System.out.println("Status code: " + response.statusCode());
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        System.out.println("Content Type: " + response.contentType());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertEquals(ContentType.JSON.toString(), response.getHeader("Content-Type"));

        JsonPath jsonPath = response.jsonPath();

        System.out.println("id: " + jsonPath.getInt("id"));
        System.out.println("gender: " + jsonPath.getString("gender"));
        System.out.println("name: " + jsonPath.getString("name"));
        System.out.println("phone: " + jsonPath.getString("phone"));


        assertEquals(13, jsonPath.getInt("id"));
        assertEquals("Female", jsonPath.getString("gender"));
        assertEquals("Jaimie", jsonPath.getString("name"));
        assertEquals("7842554879", jsonPath.getString("phone"));

    }
}
