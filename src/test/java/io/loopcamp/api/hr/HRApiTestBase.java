package io.loopcamp.api.hr;


import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;

import java.util.Map;

public class HRApiTestBase {

    protected static final String DATABASE_URL = ConfigurationReader.getProperty("hr.db.url");
    protected static final String DATABASE_USERNAME = ConfigurationReader.getProperty("hr.db.username");
    protected static final String DATABASE_PASSWORD = ConfigurationReader.getProperty("hr.db.password");

    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("hr.api.url");
    }

    public static Map<String, String> getAcceptHeader(){
        return Map.of("Accept", ContentType.JSON.toString());
    }
}
