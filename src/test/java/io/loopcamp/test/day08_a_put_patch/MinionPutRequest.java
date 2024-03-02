package io.loopcamp.test.day08_a_put_patch;

import io.loopcamp.pojo.Minion;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import utilities.MinionRestUtils;
import utilities.MinionTestBase;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class MinionPutRequest extends MinionTestBase {

    @Test
    public void putMinionTest(){
        int id = 106;

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("gender", "Male");
        requestBody.put("name", "Post7");
        requestBody.put("phone", 1234567812);

        Response postResponse = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().pathParam("id", id)
                .and().body(requestBody)
                .when().put("/minions/{id}");

        postResponse.prettyPrint(); // No response body so it will not print anything

        assertThat(postResponse.statusCode(), is(HttpStatus.SC_NO_CONTENT));
    }

    @Test
    public void patchMinionTest(){
        int id = 106;

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("phone", 1333333333);

        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().pathParam("id", id)
                .and().body(requestBody)
                .when().patch("/minions/{id}")
                .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
                .and().assertThat().body(emptyString());

//        Response getResponse = given().accept(ContentType.JSON)
//                .and().pathParam("id", id)
//                .when().get("/minions/{id}");

        Minion minion = MinionRestUtils.getMinionById(id);

        assertThat(minion.getPhone(), is ("1333333333"));

    }

}
