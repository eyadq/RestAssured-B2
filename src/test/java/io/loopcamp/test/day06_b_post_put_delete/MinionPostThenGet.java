package io.loopcamp.test.day06_b_post_put_delete;

import io.loopcamp.pojo.Minion;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import utilities.MinionRestUtils;
import utilities.MinionTestBase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class MinionPostThenGet extends MinionTestBase {

    Minion minion = MinionRestUtils.getNewMinion();

    @Test
    public void testNewMinionThenGetTest(){
        System.out.println("new Minion Info: " + minion);

        System.out.println("Post response:");
        Response response =
                given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(minion)
                .when().post("/minions");

        response.prettyPrint();

        assertThat(response.statusCode(), is(HttpStatus.SC_CREATED));

        int newMinionId = response.jsonPath().getInt("data.id");

        Response response1 =
                given().accept(ContentType.JSON)
                .and().pathParam("id", newMinionId)
                .get("/minions/{id}");

//        String newMinionName = response.jsonPath().getString("data.name");
//        String newMinionGender= response.jsonPath().getString("data.gender");
//        String newMinionPhone = response.jsonPath().getString("data.phone");

        System.out.println();

        System.out.println("Get response");
        response1.prettyPrint();

        //Deserialization
        Minion minionFromGet = response1.as(Minion.class);

        assertThat(minionFromGet.getName(), is(minion.getName()));
        assertThat(minionFromGet.getGender(), is(minion.getGender()));
        assertThat(minionFromGet.getPhone() + "", is(minion.getPhone()+""));

        //Once I am done with the assertions, I can delete
        MinionRestUtils.deleteMinionById(newMinionId);
    }
}
