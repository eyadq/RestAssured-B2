package utilities;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public class MinionTestBase {

    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("minions.api.url");
    }
}
