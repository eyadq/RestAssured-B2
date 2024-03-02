package io.loopcamp.test.day05_deserialization;
import io.loopcamp.pojo.Minion;
import utilities.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class AllMinionsPojoTest extends MinionTestBase {

    /**
     Given accept type is application/json
     When i send GET request to /minions
     Then status code is 200
     And content type is json
     And get all minions:
     id
     name
     gender
     phone
     */

    @Test
    public void minionsToPojoTest(){
        Response response =
                given().accept(ContentType.JSON)
                        .when().get("/minions");
        //response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        JsonPath jsonPath = response.jsonPath();
        List<Minion> minions = jsonPath.getList("", Minion.class);
        for (Minion minion : minions){
            System.out.println(minion);
        }



    }


}
