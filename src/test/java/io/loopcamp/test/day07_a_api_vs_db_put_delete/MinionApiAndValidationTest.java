package io.loopcamp.test.day07_a_api_vs_db_put_delete;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import utilities.ConfigurationReader;
import utilities.DBUtils;
import utilities.MinionRestUtils;
import utilities.MinionTestBase;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class MinionApiAndValidationTest extends MinionTestBase {

    /**
     given accept is json
     and content type is json
     and body is:
     {
     "gender": "Male",
     "name": "PostVSDatabase"
     "phone": 1234567425
     }
     When I send POST request to /minions
     Then status code is 201
     And content type is json
     And "success" is "A Minion is Born!"
     When I send database query
     SELECT name, gender, phone
     FROM minions
     WHERE minion_id = newIdFrom Post request;
     Then name, gender , phone values must match with POST request details
     */


    @Test
    public void postNewMinionThenValidateInDBTest(){
        Map<String, Object> newMinion = new HashMap<>();
        newMinion.put("gender", "Male");
        newMinion.put("name", "PostVSDatabase");
        newMinion.put("phone", 1234567425);


        Response response =
                given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(newMinion)
                .when().post("/minions");

        response.prettyPrint();

        assertThat(response.statusCode(), is(HttpStatus.SC_CREATED));
        assertThat(response.contentType(), is("application/json;charset=UTF-8"));
        assertThat(response.contentType(), is("application/json;charset=UTF-8"));

        //assertThat(response.path("success"), is("A Minion is Born!"));

        JsonPath jsonPath = response.jsonPath();

        assertThat(jsonPath.getString("success"), is("A Minion is Born!"));

        int newMinionId = jsonPath.getInt("data.id");
        System.out.println("Newly created minion's ID: " + newMinionId);

        String query = "SELECT name, gender, phone FROM minions WHERE minion_id = " + newMinionId;

        String dbUrl = ConfigurationReader.getProperty("minions.db.url");
        String username = ConfigurationReader.getProperty("minions.db.username");
        String password = ConfigurationReader.getProperty("minions.db.password");

        //create connection

        DBUtils.createConnection(dbUrl, username, password);
        Map<String, Object> minionFromDB = DBUtils.getRowMap(query);
        System.out.println("Minion info from DB: " + minionFromDB);

        assertThat(minionFromDB.get("GENDER"), is(newMinion.get("gender")));
        assertThat(minionFromDB.get("NAME"), is(newMinion.get("name")));
        assertThat(minionFromDB.get("PHONE")+"", is(newMinion.get("phone")+""));

        MinionRestUtils.deleteMinionById(newMinionId);



    }

}
