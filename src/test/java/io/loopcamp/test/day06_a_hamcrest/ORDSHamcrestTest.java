package io.loopcamp.test.day06_a_hamcrest;

import utilities.HRTestBase;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ORDSHamcrestTest extends HRTestBase {

    /**
     * given accept type is json
     * when I send get request to /countries
     * Then status code is 200
     * and content type is json
     * and count should be 25
     * and country ids should contain "AR,AU,BE,BR,CA"
     * and country names should have "Argentina,Australia,Belgium,Brazil,Canada"
     *
     * items[0].country_id ==> AR
     * items[1].country_id ==> AU
     */

    @DisplayName("GET /countries")
    @Test
    public void getCountries(){
        String firstCountryId =
        given().accept(ContentType.JSON)
        .when().get("/countries")
            .then().statusCode(HttpStatus.SC_OK)
            .and().contentType(ContentType.JSON)
            .and().body(
                  "items", hasSize(25),
                   "items.country_id", hasItems("AR","AU","BE","BR","CA"),
                   "items.country_name", hasItems("Argentina","Australia","Belgium","Brazil","Canada"),
                   "items[0].country_id", is(equalTo("AR")),
                   "items[1].country_id", is(equalTo("AU"))
                    )
                .and().extract().body().path("items[0].country_id");

        System.out.println(firstCountryId);
    }


}
