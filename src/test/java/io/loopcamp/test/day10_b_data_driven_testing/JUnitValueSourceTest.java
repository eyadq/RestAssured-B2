package io.loopcamp.test.day10_b_data_driven_testing;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class JUnitValueSourceTest {

    @ParameterizedTest
    @ValueSource(ints = {5, 23, 45, 23, 75, 34, 675, 34})
    public void numberTest(int num){
        System.out.println("Num: " + num);
        assertThat(num, is(greaterThan(10)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Emil", "Elyas", "Tom", "Jerry", "Vinnie"})
    public void stringsTest(String name){
        System.out.println("Name: " + name);
        assertThat(name, not(blankOrNullString()));
    }

    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    @ParameterizedTest
    @ValueSource(ints = {22192, 98033, 77493, 37650, 77044})
    public void zipcodeTest(int zipcode){
        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .and().pathParam("zipcode", zipcode)
                .and().get("/US/{zipcode}")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .log().all();
    }
}
