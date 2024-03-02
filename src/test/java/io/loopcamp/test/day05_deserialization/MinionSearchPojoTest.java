package io.loopcamp.test.day05_deserialization;
import io.loopcamp.pojo.Minion;
import io.loopcamp.pojo.MinionSearch;
import utilities.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionSearchPojoTest extends MinionTestBase {

    @Test
    public void minionSearchPojoTest(){
//        Response response =
//                given().accept(ContentType.JSON)
//                        .and().queryParam("nameContains", "e")
//                        .and().queryParam("gender", "Female")
//                        .when().get("/minions/search");

//        Response response =
//                given().accept(ContentType.JSON)
//                        .and().queryParam("nameContains", "e", "gender", "Female")
//                        .when().get("/minions/search");


        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("nameContains", "e");
        queryParams.put( "gender", "Female");
        Response response =
                given().accept(ContentType.JSON)
                .and().queryParams(queryParams)
                .when().get("/minions/search");

        response.prettyPrint();


        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        //Desearlize json response to MinionSearch
       MinionSearch minionSearch = response.body().as(MinionSearch.class);

       //how can you get total elements
        System.out.println("Total Elements " + minionSearch.getContent());

        //get me the first minion info
        System.out.println("Total Elements " + minionSearch.getContent().get(0));

        //Can you print all the names
        for (Minion each : minionSearch.getContent()){
            System.out.println(each.getName());
        }





    }
}
