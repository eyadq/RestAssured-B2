package io.loopcamp.test.day06_b_post_put_delete;

import io.loopcamp.pojo.Minion;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
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
public class MinionPostTest extends MinionTestBase {

    /**
     given accept is json
     and content type is json
     and body is:
     {
     "gender": "Male",
     "name": "TestPost4",
     "phone": 1234567425
     }
     When I send POST request to /minions
     ---------------
     Then status code is 201
     And content type is "application/json;charset=UTF-8"
     And "success" is "A Minion is Born!"
     Data name, gender , phone matches my request details
     */

    @Test
    public void addNewMinionAsJsonTest () {
        String jsonBody = "{\n" +   // alwasy put the double quote first and then copy past the body
                "\"gender\": \"Male\",\n" +
                "\"name\": \"TestPostString\",\n" +
                "\"phone\": 1234567425\n" +
                "}";

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body( jsonBody) // SERIALIZATION --- >  FROM String Object to JSON
                .when().post("/minions");

        // Verify status code
        response.prettyPrint();
        assertEquals(HttpStatus.SC_CREATED, response.statusCode()); // JUnit
        assertThat(response.statusCode(), is(201)); // Hamcrest


        // Verify Header
        assertThat(response.contentType(), is("application/json;charset=UTF-8"));


        // Converted response to JsonPath
        JsonPath jsonPath = response.jsonPath();

        // Verify body
        assertThat(jsonPath.getString("success"), equalTo("A Minion is Born!"));

        assertThat(jsonPath.getString("data.name"), equalTo("TestPost"));
        assertThat(jsonPath.getString("data.gender"), equalTo("Male"));
        assertThat(jsonPath.getString("data.phone"), equalTo("1234567425"));


        int id = jsonPath.getInt("data.id");
        System.out.println("minion id: " + id);
        MinionRestUtils.deleteMinionById(id);



    }

    /**
     {
     "gender": "Male",
     "name": "TestPostMap"
     "phone": 1234567425
     }
     */
    @Test
    public void addNewMinionAsMapTest() {

        Map <String, Object> minionPostMap = new HashMap<>();
        minionPostMap.put("gender", "Male");
        minionPostMap.put("name", "TestPostMap");
        minionPostMap.put("phone", 1234567425);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body( minionPostMap) // SERIALIZATION --- >  FROM Map Collection Object to JSON
                .when().post("/minions");


        // Verify status code
        response.prettyPrint();
        assertEquals(HttpStatus.SC_CREATED, response.statusCode()); // JUnit
        assertThat(response.statusCode(), is(201)); // Hamcrest


        // Verify Header
        assertThat(response.contentType(), is("application/json;charset=UTF-8"));


        // Converted response to JsonPath
        JsonPath jsonPath = response.jsonPath();

        // Verify body
        assertThat(jsonPath.getString("success"), equalTo("A Minion is Born!"));

        assertThat(jsonPath.getString("data.name"), equalTo("TestPostMap"));
        assertThat(jsonPath.getString("data.gender"), equalTo("Male"));
        assertThat(jsonPath.getString("data.phone"), equalTo("1234567425"));


        int id = jsonPath.getInt("data.id");
        System.out.println("minion id: " + id);
        MinionRestUtils.deleteMinionById(id);

    }


    /**
     {
     "gender": "Female",
     "name": "TestPostPojo"
     "phone": 1234567425
     }
     */
    @Test
    public void addNewMinionAsPOJOTest () {
        // Minion object
        Minion newMinion = new Minion();
        newMinion.setGender("Female");
        newMinion.setName("TestPostPojo");
        newMinion.setPhone("1234567425");

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(newMinion) // SERIALIZATION --- >  FROM Minion POJO Class Object to JSON
                .when().post("/minions");


        // Verify status code
        response.prettyPrint();
        assertEquals(HttpStatus.SC_CREATED, response.statusCode()); // JUnit
        assertThat(response.statusCode(), is(201)); // Hamcrest


        // Verify Header
        assertThat(response.contentType(), is("application/json;charset=UTF-8"));


        // Converted response to JsonPath
        JsonPath jsonPath = response.jsonPath();

        // Verify body
        assertThat(jsonPath.getString("success"), equalTo("A Minion is Born!"));

        assertThat(jsonPath.getString("data.name"), equalTo("TestPostPojo"));
        assertThat(jsonPath.getString("data.gender"), equalTo("Female"));
        assertThat(jsonPath.getString("data.phone"), equalTo("1234567425"));


        int id = jsonPath.getInt("data.id");
        System.out.println("minion id: " + id);
        MinionRestUtils.deleteMinionById(id);

    }



}
