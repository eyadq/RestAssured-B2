package io.loopcamp.test.day12_mocks;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

public class CoursesMocksApiTest {

    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("mock_server_url");
    }

    @DisplayName("GET /courses")
    @Test
    public void testCoursesApi () {
        given().accept(ContentType.JSON)
                .when().get("/courses")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON)
                .and().assertThat().body("courseIds", hasItems(1, 2, 3))
                .and().assertThat().body("courseNames", hasItems("Java SDET", "RPA Developer", "Salesforce Automation"))
                .log().all();
    }
}
