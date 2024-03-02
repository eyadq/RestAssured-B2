package io.loopcamp.test.day03_json_path;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utilities.MinionTestBase;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MinionPathMethodTest extends MinionTestBase {



    /**
     * Given accept is json
     * And path param id is 13
     * When I send get request to /api/minions/{id}
     * ----------
     * Then status code is 200
     * And content type is json
     * And id value is 13
     * And name is "Jaimie"
     * And gender is "Female"
     * And phone is "7842554879"
     */

    @DisplayName("GET /minions/{id}")
    @Test
    public void readMinionJsonPath(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 13)
                .when().get("/minions/{id}");

        response.prettyPrint();

        /*
        {
             "id": 13,
              "gender": "Female",
              "name": "Jaimie",
              "phone": "7842554879"
         }
         */

        assertEquals(HttpStatus.SC_OK, response.statusCode());

        System.out.println("id:  = " + response.path("id"));
        System.out.println("gender:  = " + response.path("gender"));
        System.out.println("name:  = " + response.path("name"));
        System.out.println("phone:  = " + response.path("phone"));

        assertEquals("13","" + response.path("id"));
        assertEquals("Female", response.path("gender"));
        assertEquals("Jaimie", response.path("name"));
        assertEquals("7842554879", response.path("phone"));
    }

    /**
     Given accept is json
     When I send get request to /api/minions
     Then status code is 200
     And content type is json
     And I can navigate json array object
     */
    @DisplayName("GET /minions with path()")
    @Test
    public void readMinionJsonUsingPathTest(){
        Response response =
                given().accept(ContentType.JSON)
                .when().get("/minions");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        /**
         [
         {
         "id": 1,
         "gender": "Male",
         "name": "Meade",
         "phone": "9994128233"
         },
         {
         "id": 2,
         "gender": "Male",
         "name": "Nels",
         "phone": "4218971348"
         }
         ]
         */

        //Print all minion for each key
        System.out.println("ids: " + response.path("id"));
        System.out.println("names: " + response.path("name"));

        //Print first minion
        System.out.println("1st minion id: " + response.path("[0].id"));
        System.out.println("1st minion id: " + response.path("id[0]"));
        System.out.println("1st minion name: " + response.path("name[0]"));

        //Print last minion -- > we just need to provide -1
        System.out.println("last minion id: " + response.path("id[-1]"));
        System.out.println("last minion name: " + response.path("name[-1]"));

        List<String> listId = response.path("id");
        System.out.println("All ids: " + listId);
        System.out.println("Total minions: " + listId.size());
        System.out.println();
        List<String> listName = response.path("name");
        for (int i = 0; i < listName.size(); i++) {
            System.out.println("Hi " + listName.get(i) + "!");
        }
        System.out.println();
        for (String minion: listName){
            System.out.println("Hi " + minion + "!");
        }
        System.out.println();
        listName.forEach(minion-> System.out.println("Hi " + minion + "!"));

    }

}
