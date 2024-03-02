package io.loopcamp.test.day06_a_hamcrest;

import utilities.MinionTestBase;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MinionHamcrestTest extends MinionTestBase {

    /**
     given accept type is json
     and path id is 24
     When i send get request to /minions/{id}
     then status code is 200
     and content type is application/json
        and id" is 24,
        "name" is "Julio",
        "gender" is "Male",
        "phone" is 9393139934
     */

    @DisplayName("GET /minions{id}")
    @Test
    public void singleMinionTest(){
        given().accept(ContentType.JSON)
        .and().pathParam("id", 24)
        .when().get("/minions/{id}")
        .then().statusCode(HttpStatus.SC_OK)
        .and().assertThat()
                .body("id", is(24))
                .body("name", is("Julio"))
                .body("gender", is("Male"))
                .body("phone", is("9393139934"));

        given().accept(ContentType.JSON)
                .and().pathParam("id", 24)
                .when().get("/minions/{id}")
                .then().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().assertThat().body("id", is(24),  // imagine this part is same as  --  . assertEquals("24", response.path("id"));
                        "name", equalTo("Julio"),
                        "gender", is(equalTo("Male")),
                        "phone", is("9393139934"));
    }

    /**
     Given accept type is json
     And query param nameContains value is "e"
     And query param gender value is "Female"
     When I send get request to /minions/search
     ----------
     Then status code is 200
     And content type is Json
     And header date contains 2024
     And json response data is as expected
     totalElement is 33
     has ids: 94, 98, 91, 81
     has names: Jocelin, Georgianne, Catie, Marylee,Elita
     every gender is Female
     every name contains e
     */
    @DisplayName("GET /minions/search -- with query params")
    @Test
    public void searchTest(){
        given().accept(ContentType.JSON)
                .and().queryParam("nameContains", "e")
                .and().queryParam("gender", "Female")
                .when().get("/minions/search")
                .then().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON)
                //.and().header("Date", containsString("2024"))
                .and().header("Date", containsString(LocalDate.now().getYear() + ""))
                .and().body(
                        "totalElement", is(equalTo(33)),
                        "content.id", hasItems(94, 98, 91, 81),
                        "content.name", hasItems("Jocelin", "Georgianne", "Catie", "Marylee", "Elita"),
                        "content.gender", everyItem(is("Female")),
                        "content.name", everyItem(containsStringIgnoringCase("e"))
                        )
                .log().all();


    }
}
