package io.loopcamp.test.day10_b_data_driven_testing;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CsvSourceDDTTest {

    @ParameterizedTest
    @CsvSource({
            "7, 34, 102",
            "3, 56, 34",
            "4, 76, 77",
            "8, 87, 87"
    })
    public void basicAddTest(int num1, int num2, int num3){
        System.out.print("num1 = " + num1);
        System.out.print("\tnum2 = " + num2);
        System.out.println("\tnum3 = " + num3);
    }

    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    @ParameterizedTest
    @CsvSource({
            "AK, Kodiak",
            "TX, Houston",
            "MA, Boston",
            "OH, Cleveland",
            "NC, Charlotte",
            "IL, Chicago"
             })
    public void cityAndStateZipCodeAPITest(String stateCode, String cityName){
        given().accept(ContentType.JSON)
                .and().pathParam("country", "US")
                .and().pathParam("state", stateCode)
                .and().pathParam("city", cityName)
                .when().get("/{country}/{state}/{city}")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON)
                .and().body("places[0].'place name'", containsString(cityName))
                .log().all();
    }


}
