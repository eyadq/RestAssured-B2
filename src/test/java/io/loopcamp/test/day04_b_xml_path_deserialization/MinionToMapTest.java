package io.loopcamp.test.day04_b_xml_path_deserialization;

import utilities.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionToMapTest extends MinionTestBase {

    /**
     Given accept type is application/json
     And Path param id = 10
     When I send GET request to /minions
     --------
     Then status code is 200
     And content type is json
     And minion data matching:
     id > 10
     name>Lorenza
     gender >Female
     phone >3312820936
     */
    @DisplayName("Get /minions/{minion_id} -- with XML")
    @Test
    public void minionXmlPathWithPathParamTest(){
        Response response =
                given().accept(ContentType.JSON)
                .and().pathParam("minion_id", 10)
                .when().get("/minions/{minion_id}");
        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        //Convert Json Response Body to Java Collection [Deserialization]
        //Map<String, Object> responseAsMap = response.as(Map.class); // same as below
        Map<String, Object> responseAsMap = response.body().as(Map.class);
        System.out.println(responseAsMap);

        /*
        {
             "id": 10,
             "gender": "Female",
             "name": "Lorenza",
              "phone": "3312820936"
        }
        {id=10, gender=Female, name=Lorenza, phone=3312820936}
         */

        System.out.println("All the keys: " + responseAsMap.keySet()); //Is a linked hash map b/c insertion order kept
        System.out.println("All the values: " + responseAsMap.values());

        assertEquals(10, responseAsMap.get("id"));
        assertEquals("Female", responseAsMap.get("gender"));
        assertEquals("Lorenza", responseAsMap.get("name"));
        assertEquals("3312820936", responseAsMap.get("phone"));

        System.out.println();
        for (Object element : responseAsMap.values()){
            System.out.println(element + ": " + element.getClass());
        }
    }


}
