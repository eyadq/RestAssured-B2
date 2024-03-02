package utilities;

import org.junit.jupiter.api.BeforeAll;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;

public class HRTestBase {

    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("hr.api.url");
    }
}
