package io.loopcamp.test.day12_mocks;
import io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MockStudentsApiTest {

    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("mock_server_url");
    }

    @DisplayName("GET /students/1")
    @Test
    public void testStudentsApi () {
        given().accept(ContentType.JSON)
                .when().get("/students/1")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON)
                .and().assertThat().body("studentId", equalTo(1))
                .log().all();
    }
}
